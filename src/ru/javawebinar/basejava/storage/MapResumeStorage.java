package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage {
    protected final Map<String, Resume> map = new HashMap<>();

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public void doUpdate(Resume r, Object resume) {
        map.put(r.getUuid(), r);
    }

    @Override
    public void doSave(Resume r, Object resume) {
        map.put(r.getUuid(), r);
    }

    @Override
    public Resume doGet(Object resume) {
        return (Resume) resume;
    }

    @Override
    public void doDelete(Object resume) {
        map.remove(((Resume) resume).getUuid());
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
    protected boolean isExist(Object resume) {
        return resume != null;
    }

    @Override
    protected Object findSearchKey(String uuid) {
        return map.get(uuid);
    }
}
