package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.serializer.Serializer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private final Serializer serializer;

    protected PathStorage(String dir, Serializer serializer) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
        this.serializer = serializer;
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    protected Path findSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected List<Resume> getCopyAll() {
        return getListStream().map(this::doGet).collect(Collectors.toList());
    }

    @Override
    protected void doUpdate(Resume r, Path path) {
        doSave(r, path);
    }

    @Override
    protected void doSave(Resume r, Path path) {
        try (OutputStream outputStream = Files.newOutputStream(path);
             BufferedOutputStream bos = new BufferedOutputStream(outputStream)) {
            serializer.serialize(r, bos);
        } catch (IOException e) {
            throw new StorageException("Path write error", r.getUuid(), e);
        }
    }

    @Override
    protected Resume doGet(Path path) {
        try (InputStream inputStream = Files.newInputStream(path);
             BufferedInputStream bis = new BufferedInputStream(inputStream)) {
            return serializer.deserialize(bis);
        } catch (IOException e) {
            throw new StorageException("Path read error", getFileName(path), e);
        }
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Path delete error", getFileName(path), e);
        }
    }

    @Override
    public void clear() {
        getListStream().forEach(this::doDelete);
    }

    @Override
    public int size() {
        return (int) getListStream().count();
    }

    private Stream<Path> getListStream() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Directory read error", e);
        }
    }

    private String getFileName(Path path) {
        return path.getFileName().toString();
    }
}
