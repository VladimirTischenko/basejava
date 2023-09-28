package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import static ru.javawebinar.basejava.storage.AbstractArrayStorage.STORAGE_LIMIT;

abstract class AbstractArrayStorageTest {
    private final Storage storage;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final Resume RESUME1 = new Resume(UUID_1);
    private static final Resume RESUME2 = new Resume(UUID_2);
    private static final Resume RESUME3 = new Resume(UUID_3);

    @BeforeEach
    public void setUp() {
        storage.clear();
        storage.save(RESUME1);
        storage.save(RESUME2);
        storage.save(RESUME3);
    }

    @Test
    void clear() {
        storage.clear();
        Assertions.assertEquals(0, storage.size());
    }

    @Test
    void size() {
        Assertions.assertEquals(3, storage.size());
    }

    @Test
    void save() {
        Resume resume = new Resume(UUID_4);
        storage.save(resume);
        Resume[] resumes = {RESUME1, RESUME2, RESUME3, resume};
        Assertions.assertArrayEquals(resumes, storage.getAll());
    }

    @Test
    void update() {
        storage.update(RESUME2);
        Resume[] resumes = {RESUME1, RESUME2, RESUME3};
        Assertions.assertArrayEquals(resumes, storage.getAll());
    }

    @Test
    void get() {
        Resume resume = storage.get(UUID_2);
        Assertions.assertEquals(RESUME2, resume);
    }

    @Test
    void delete() {
        storage.delete(UUID_2);
        Resume[] resumes = {RESUME1, RESUME3};
        Assertions.assertArrayEquals(resumes, storage.getAll());
    }

    @Test
    void getAll() {
        Resume[] resumes = {RESUME1, RESUME2, RESUME3};
        Assertions.assertArrayEquals(resumes, storage.getAll());
    }

    @Test
    public void getNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () ->
                storage.get("dummy")
        );
    }

    @Test
    public void saveAlreadyExist() {
        Resume resume = new Resume(UUID_3);
        Assertions.assertThrows(ExistStorageException.class, () ->
                storage.save(resume)
        );
    }

    @Test
    public void saveStorageIsOverflow() {
        try {
            int size = storage.size();
            for (int i = 0; i < STORAGE_LIMIT - size; i++) {
                storage.save(new Resume());
            }
        } catch (ExistStorageException e) {
            Assertions.fail("Resume with uuid " + e.getUuid() + " already exists");
        } catch (StorageException e) {
            Assertions.fail("Overflow occurred ahead of time");
        }
        Assertions.assertThrows(StorageException.class, () ->
                storage.save(new Resume())
        );
    }

    @Test
    public void updateNotExist() {
        Resume resume = new Resume(UUID_4);
        Assertions.assertThrows(NotExistStorageException.class, () ->
                storage.update(resume)
        );
    }

    @Test
    public void deleteNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () ->
                storage.delete("dummy")
        );
    }
}