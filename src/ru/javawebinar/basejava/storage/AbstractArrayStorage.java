package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public int size() {
        return size;
    }

    public void save(Resume r) {
        String uuid = r.getUuid();
        int index = findIndex(uuid);
        if (index > -1) {
            System.out.println("Resume with uuid " + uuid + " already exists");
        } else if (size == STORAGE_LIMIT) {
            System.out.println("Storage is overflow");
        } else {
            insertResume(r);
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
            removeResume(index);
            size--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    protected abstract int findIndex(String uuid);
    protected abstract void insertResume(Resume r);
    protected abstract void removeResume(int index);

    protected String getResumeDoesNotExistMessage(String uuid) {
        return "Resume with uuid " + uuid + " not exists";
    }
}
