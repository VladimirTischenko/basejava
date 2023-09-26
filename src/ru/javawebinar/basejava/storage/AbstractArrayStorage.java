package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    public int size() {
        return size;
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

    protected abstract int findIndex(String uuid);

    protected String getResumeDoesNotExistMessage(String uuid) {
        return "Resume with uuid " + uuid + " not exists";
    }
}
