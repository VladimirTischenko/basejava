package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import static org.junit.jupiter.api.Assertions.*;

abstract class AbstractStorageTest {
    final Storage storage;

    protected AbstractStorageTest(Storage storage) {
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
        assertSize(3);
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
        assertGet(RESUME_4);
    }

    @Test
    void update() {
        Resume resume = new Resume(UUID_2);
        storage.update(resume);
        assertEquals(resume, storage.get(UUID_2));
    }

    @Test
    void get() {
        Resume resume = storage.get(UUID_2);
        assertEquals(RESUME_2, resume);
    }

    @Test
    void delete() {
        storage.delete(UUID_2);
        assertSize(2);
        assertGet(RESUME_1);
        assertGet(RESUME_3);

    }

    @Test
    void getAll() {
        assertSize(3);
        Resume[] resumes = storage.getAll();
        assertGet(resumes[0]);
        assertGet(resumes[1]);
        assertGet(resumes[2]);
    }

    @Test
    public void getNotExist() {
        assertThrows(NotExistStorageException.class, () ->
                storage.get("dummy")
        );
    }

    @Test
    public void saveAlreadyExist() {
        Resume resume = new Resume(UUID_3);
        assertThrows(ExistStorageException.class, () ->
                storage.save(resume)
        );
    }

    @Test
    public void updateNotExist() {
        assertThrows(NotExistStorageException.class, () ->
                storage.update(RESUME_4)
        );
    }

    @Test
    public void deleteNotExist() {
        assertThrows(NotExistStorageException.class, () ->
                storage.delete("dummy")
        );
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }

    private void assertGet(Resume r) {
        assertEquals(r, storage.get(r.getUuid()));
    }
}