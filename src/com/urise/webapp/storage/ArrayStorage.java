package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private static final String ERROR_RESUME_WITH_UUID = "ERROR: resume with uuid ";
    private static final String ERROR_STORAGE_IS_FULL = "ERROR: storage is full";
    private static final String NOT_EXIST = " not exists";
    private static final String ALREADY_EXIST = " already exists";
    private static final int _10000 = 10000;

    Resume[] storage = new Resume[_10000];
    private int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        if (size == _10000) {
            System.out.println(ERROR_STORAGE_IS_FULL);
            return;
        }
        String uuid = r.getUuid();
        int index = findIndex(uuid);
        if (index == -1) {
            storage[size] = r;
            size++;
        } else {
            System.out.println(ERROR_RESUME_WITH_UUID + uuid + ALREADY_EXIST);
        }
    }

    public void update(Resume r) {
        String uuid = r.getUuid();
        int index = findIndex(uuid);
        if (index == -1) {
            System.out.println(getErrorResumeDoesNotExistMessage(uuid));
        } else {
            storage[index] = r;
        }
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index == -1) {
            System.out.println(getErrorResumeDoesNotExistMessage(uuid));
            return null;
        } else {
            return storage[index];
        }
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index == -1) {
            System.out.println(getErrorResumeDoesNotExistMessage(uuid));
        } else {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    private int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }

    private String getErrorResumeDoesNotExistMessage(String uuid) {
        return ERROR_RESUME_WITH_UUID + uuid + NOT_EXIST;
    }
}
