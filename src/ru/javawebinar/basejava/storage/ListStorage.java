package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ListStorage extends AbstractStorage {
    protected final List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void update(Resume r) {
        Resume resume = get(r.getUuid());
        int index = storage.indexOf(resume);
        storage.set(index, r);
    }

    @Override
    public void save(Resume r) {
        String uuid = r.getUuid();
        Optional<Resume> optionalResume = getOptionalResumeByUuid(uuid);
        if (optionalResume.isPresent()) {
            throw new ExistStorageException(uuid);
        } else {
            storage.add(r);
        }
    }

    @Override
    public Resume get(String uuid) {
        Optional<Resume> optionalResume = getOptionalResumeByUuid(uuid);
        return optionalResume.orElseThrow(() -> new NotExistStorageException(uuid));
    }

    @Override
    public void delete(String uuid) {
        boolean b = storage.removeIf(resume -> Objects.equals(resume.getUuid(), uuid));
        if (!b) {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(Resume[]::new);
    }

    @Override
    public int size() {
        return storage.size();
    }

    private Optional<Resume> getOptionalResumeByUuid(String uuid) {
        return storage.stream().filter(resume -> Objects.equals(resume.getUuid(), uuid))
                .findFirst();
    }
}
