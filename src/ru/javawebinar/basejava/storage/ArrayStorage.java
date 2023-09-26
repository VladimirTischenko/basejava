package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private static final int _10000 = 10000;

    Resume[] storage = new Resume[_10000];
    private int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        String uuid = r.getUuid();
        int index = findIndex(uuid);
        if (index > -1) {
            System.out.println("Resume with uuid " + uuid + " already exists");
        } else if (size == _10000) {
            System.out.println("Storage is overflow");
        } else {
            storage[size] = r;
            size++;
        }
    }

    public void update(Resume r) {
        String uuid = r.getUuid();
        int index = findIndex(uuid);
        if (index == -1) {
            System.out.println(getResumeDoesNotExistMessage(uuid));
        } else {
            storage[index] = r;
        }
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index == -1) {
            System.out.println(getResumeDoesNotExistMessage(uuid));
            return null;
        } else {
            return storage[index];
        }
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index == -1) {
            System.out.println(getResumeDoesNotExistMessage(uuid));
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

    private String getResumeDoesNotExistMessage(String uuid) {
        return "Resume with uuid " + uuid + " not exists";
    }
}
