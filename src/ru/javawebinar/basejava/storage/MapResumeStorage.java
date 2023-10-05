package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage {
    protected final Map<Resume, String> storage = new HashMap<>();

    @Override
    protected List<Resume> getResumesForSorting() {
        return new ArrayList<>(storage.keySet());
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void update(Resume r) {
        String uuid = r.getUuid();
        if (storage.containsKey(r)) {
            storage.put(r, uuid);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public void save(Resume r) {
        String uuid = r.getUuid();
        if (storage.containsKey(r)) {
            throw new ExistStorageException(uuid);
        } else {
            storage.put(r, uuid);
        }
    }

    @Override
    public Resume get(String uuid) {
        Optional<Resume> optionalResume = storage.entrySet()
                .stream()
                .filter(entry -> uuid.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findFirst();
        return optionalResume.orElseThrow(() -> new NotExistStorageException(uuid));
    }

    @Override
    public void delete(String uuid) {
        Resume resume = get(uuid);
        storage.remove(resume);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
