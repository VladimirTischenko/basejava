package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    protected final Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void update(Resume r) {
        String uuid = r.getUuid();
        boolean b = storage.containsKey(uuid);
        if (b) {
            storage.put(uuid, r);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public void save(Resume r) {
        String uuid = r.getUuid();
        if (storage.containsKey(uuid)) {
            throw new ExistStorageException(uuid);
        } else {
            storage.put(r.getUuid(), r);
        }
    }

    @Override
    public Resume get(String uuid) {
        Resume resume = storage.get(uuid);
        if (resume == null) {
            throw new NotExistStorageException(uuid);
        } else {
            return resume;
        }
    }

    @Override
    public void delete(String uuid) {
        Resume resume = storage.remove(uuid);
        if (resume == null) {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public Resume[] getAll() {
        Collection<Resume> resumes = storage.values();
        return resumes.toArray(new Resume[storage.size()]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
