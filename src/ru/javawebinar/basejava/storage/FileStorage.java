package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.serializer.ResumeSerializer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private final File directory;
    private final ResumeSerializer resumeSerializer;

    protected FileStorage(File directory, ResumeSerializer resumeSerializer) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
        this.resumeSerializer = resumeSerializer;
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected File findSearchKey(String uuid) {
        return new File(directory, uuid + ".txt");
    }

    @Override
    protected List<Resume> getCopyAll() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Directory read error", null);
        }
        List<Resume> resumes = new ArrayList<>(files.length);
        for (File file : files) {
            Resume resume = doGet(file);
            resumes.add(resume);
        }
        return resumes;
    }

    @Override
    protected void doUpdate(Resume r, File file) {
        doSave(r, file);
    }

    @Override
    protected void doSave(Resume r, File file) {
        try (FileOutputStream fos = new FileOutputStream(file);
             BufferedOutputStream bos = new BufferedOutputStream(fos)) {
            resumeSerializer.serialize(r, bos);
        } catch (IOException e) {
            throw new StorageException("File write error", r.getUuid(), e);
        }
    }

    @Override
    protected Resume doGet(File file) {
        try (FileInputStream fis = new FileInputStream(file);
             BufferedInputStream bis = new BufferedInputStream(fis)) {
            return resumeSerializer.deserialize(bis);
        } catch (IOException e) {
            throw new StorageException("File read error", file.getName(), e);
        }
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("File delete error", file.getName());
        }
    }

    @Override
    public void clear() {
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            doDelete(file);
        }
    }

    @Override
    public int size() {
        String[] strings = directory.list();
        if (strings == null) {
            throw new StorageException("Directory read error", null);
        }
        return strings.length;
    }
}
