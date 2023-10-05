package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage {
    protected final Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void update(Resume r) {
        String uuid = r.getUuid();
        if (storage.containsKey(uuid)) {
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
            storage.put(uuid, r);
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
    public List<Resume> getResumesForSorting() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public int size() {
        return storage.size();
    }
}
