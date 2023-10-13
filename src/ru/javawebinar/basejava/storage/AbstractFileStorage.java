package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private static final File DIR = new File("./fileStorage");

    @Override
    protected boolean isExist(File searchKey) {
        return searchKey.exists();
    }

    @Override
    protected File findSearchKey(String uuid) {
        return new File(DIR + "/" + uuid + ".txt");
    }

    @Override
    protected List<Resume> getCopyAll() {
        List<Resume> resumes = new ArrayList<>();
        for (File file : Objects.requireNonNull(DIR.listFiles())) {
            Resume resume = doGet(file);
            resumes.add(resume);
        }
        return resumes;
    }

    @Override
    protected void doUpdate(Resume r, File searchKey) {
        doSave(r, searchKey);
    }

    @Override
    protected void doSave(Resume r, File searchKey) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(searchKey);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(r);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected Resume doGet(File searchKey) {
        Object object;
        try (FileInputStream fileInputStream = new FileInputStream(searchKey);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            object = objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return (Resume) object;
    }

    @Override
    protected void doDelete(File searchKey) {
        boolean delete = searchKey.delete();
    }

    @Override
    public void clear() {
        for (File file : Objects.requireNonNull(DIR.listFiles())) {
            boolean delete = file.delete();
        }
    }

    @Override
    public int size() {
        int i = 0;
        for (File file : Objects.requireNonNull(DIR.listFiles())) {
            i++;
        }
        return i;
    }
}
