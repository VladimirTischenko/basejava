package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;

    protected PathStorage(String dir) {
        this.directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    @Override
    protected boolean isExist(Path path) {
        return path.exists();
    }

    @Override
    protected Path findSearchKey(String uuid) {
        return new Path(directory, uuid + ".txt");
    }

    @Override
    protected List<Resume> getCopyAll() {
        Path[] paths = directory.listPaths();
        if (paths == null) {
            throw new StorageException("Directory read error", null);
        }
        List<Resume> resumes = new ArrayList<>(paths.length);
        for (Path path : paths) {
            Resume resume = doGet(path);
            resumes.add(resume);
        }
        return resumes;
    }

    @Override
    protected void doUpdate(Resume r, Path path) {
        doSave(r, path);
    }

    @Override
    protected void doSave(Resume r, Path path) {
        try (PathOutputStream pathOutputStream = new PathOutputStream(path);
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(pathOutputStream);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream)) {
            objectOutputStream.writeObject(r);
        } catch (IOException e) {
            throw new StorageException("Path write error", r.getUuid(), e);
        }
    }

    @Override
    protected Resume doGet(Path path) {
        try (PathInputStream pathInputStream = new PathInputStream(path);
             BufferedInputStream bufferedInputStream = new BufferedInputStream(pathInputStream);
             ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream)) {
            return (Resume) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new StorageException("Path read error", path.getName(), e);
        }
    }

    @Override
    protected void doDelete(Path path) {
        if (!path.delete()) {
            throw new StorageException("Path delete error", path.getName());
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
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
