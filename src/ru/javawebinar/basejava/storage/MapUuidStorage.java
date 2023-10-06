package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage {
    protected final Map<String, Resume> map = new HashMap<>();

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public void doUpdate(Resume r, Object uuid) {
        map.put(r.getUuid(), r);
    }

    @Override
    public void doSave(Resume r, Object uuid) {
        map.put(r.getUuid(), r);
    }

    @Override
    public Resume doGet(Object uuid) {
        return map.get((String) uuid);
    }

    @Override
    public void doDelete(Object uuid) {
        map.remove((String) uuid);
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
    protected boolean isExist(Object uuid) {
        return uuid != null;
    }

    @Override
    protected Object findSearchKey(String uuid) {
        return map.containsKey(uuid) ? uuid : null;
    }
}
