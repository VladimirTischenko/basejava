package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.TestData;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.SectionType;
import ru.javawebinar.basejava.model.TextSection;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.javawebinar.basejava.TestData.*;
import static ru.javawebinar.basejava.storage.AbstractStorage.FULLNAME_UUID_RESUME_COMPARATOR;

abstract class AbstractStorageTest {
    static final String DIRECTORY = Config.get().getStorageDir();

    final Storage storage;

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_3);
        storage.save(RESUME_2);
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
        Resume resume = TestData.createResume(UUID_2, "New Name");
        resume.setContact(ContactType.SKYPE, "skype:new.skype");
        resume.setSection(SectionType.OBJECTIVE, new TextSection("New position"));
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
    void getAllSorted() {
        assertSize(3);
        List<Resume> expectedResumes = Arrays.asList(RESUME_2, RESUME_3, RESUME_1);
        expectedResumes.sort(FULLNAME_UUID_RESUME_COMPARATOR);
        List<Resume> actualResumes = storage.getAllSorted();
        assertIterableEquals(expectedResumes, actualResumes);
    }

    @Test
    public void getNotExist() {
        assertThrows(NotExistStorageException.class, () ->
                storage.get("dummy")
        );
    }

    @Test
    public void saveAlreadyExist() {
        assertThrows(ExistStorageException.class, () ->
                storage.save(RESUME_3)
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