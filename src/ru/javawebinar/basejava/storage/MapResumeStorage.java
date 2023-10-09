package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {
    protected final Map<String, Resume> map = new HashMap<>();

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public void doUpdate(Resume r, Resume resume) {
        map.put(r.getUuid(), r);
    }

    @Override
    public void doSave(Resume r, Resume resume) {
        map.put(r.getUuid(), r);
    }

    @Override
    public Resume doGet(Resume resume) {
        return resume;
    }

    @Override
    public void doDelete(Resume resume) {
        map.remove(resume.getUuid());
    }

    @Override
    public List<Resume> getCopyAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    protected boolean isExist(Resume resume) {
        return resume != null;
    }

    @Override
    protected Resume findSearchKey(String uuid) {
        return map.get(uuid);
    }
}
