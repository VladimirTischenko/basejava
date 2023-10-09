package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage<String> {
    protected final Map<String, Resume> map = new HashMap<>();

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public void doUpdate(Resume r, String uuid) {
        map.put(r.getUuid(), r);
    }

    @Override
    public void doSave(Resume r, String uuid) {
        map.put(r.getUuid(), r);
    }

    @Override
    public Resume doGet(String uuid) {
        return map.get(uuid);
    }

    @Override
    public void doDelete(String uuid) {
        map.remove(uuid);
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
    protected boolean isExist(String uuid) {
        return uuid != null;
    }

    @Override
    protected String findSearchKey(String uuid) {
        return map.containsKey(uuid) ? uuid : null;
    }
}
