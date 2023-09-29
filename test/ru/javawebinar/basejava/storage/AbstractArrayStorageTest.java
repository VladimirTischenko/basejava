package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

abstract class AbstractArrayStorageTest {
    private final Storage storage;

    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final Resume RESUME_1 = new Resume(UUID_1);
    private static final Resume RESUME_2 = new Resume(UUID_2);
    private static final Resume RESUME_3 = new Resume(UUID_3);
    private static final Resume RESUME_4 = new Resume(UUID_4);

    @BeforeEach
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    void size() {
        assertSize(3);
    }

    @Test
    void save() {
        storage.save(RESUME_4);
        assertSize(4);
        Resume[] resumes = {RESUME_1, RESUME_2, RESUME_3, RESUME_4};
        Assertions.assertArrayEquals(resumes, storage.getAll());
    }

    @Test
    void update() {
        storage.update(RESUME_2);
        Resume[] resumes = {RESUME_1, RESUME_2, RESUME_3};
        Assertions.assertArrayEquals(resumes, storage.getAll());
    }

    @Test
    void get() {
        Resume resume = storage.get(UUID_2);
        Assertions.assertEquals(RESUME_2, resume);
    }

    @Test
    void delete() {
        storage.delete(UUID_2);
        assertSize(2);
        Resume[] resumes = {RESUME_1, RESUME_3};
        Assertions.assertArrayEquals(resumes, storage.getAll());
    }

    @Test
    void getAll() {
        assertSize(3);
        Resume[] resumes = {RESUME_1, RESUME_2, RESUME_3};
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
            for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT - size; i++) {
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
        Assertions.assertThrows(NotExistStorageException.class, () ->
                storage.update(RESUME_4)
        );
    }

    @Test
    public void deleteNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () ->
                storage.delete("dummy")
        );
    }

    private void assertSize(int size) {
        Assertions.assertEquals(size, storage.size());
    }
}