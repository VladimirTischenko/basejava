package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

abstract class AbstractArrayStorageTest extends AbstractStorageTest{
    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
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

}