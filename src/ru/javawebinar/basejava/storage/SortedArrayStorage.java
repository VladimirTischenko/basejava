package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    public void insertResume(Resume r) {
        int i;
        for (i = size - 1; (i >= 0 && storage[i].compareTo(r) > 0); i--) {
            storage[i + 1] = storage[i];
        }
        storage[i + 1] = r;
    }

    @Override
    public void removeResume(int index) {
        if (size - index >= 0) {
            System.arraycopy(storage, index + 1, storage, index, size - index);
        }
    }

    @Override
    protected int findIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
