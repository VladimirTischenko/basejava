package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.serializer.ResumeSerializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private final ResumeSerializer resumeSerializer;

    protected PathStorage(String dir, ResumeSerializer resumeSerializer) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
        this.resumeSerializer = resumeSerializer;
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    protected Path findSearchKey(String uuid) {
        return Paths.get(directory.toString() + '/' + uuid + ".txt");
    }

    @Override
    protected List<Resume> getCopyAll() {
        return getPathStream().map(this::doGet).collect(Collectors.toList());
    }

    @Override
    protected void doUpdate(Resume r, Path path) {
        doSave(r, path);
    }

    @Override
    protected void doSave(Resume r, Path path) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            resumeSerializer.serialize(r, baos);
            byte[] bytes = baos.toByteArray();
            Files.write(path, bytes);
        } catch (IOException e) {
            throw new StorageException("Path write error", r.getUuid(), e);
        }
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            byte[] bytes = Files.readAllBytes(path);
            try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes)) {
                return resumeSerializer.deserialize(bais);
            }
        } catch (IOException e) {
            throw new StorageException("Path read error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Path delete error", path.getFileName().toString());
        }
    }

    @Override
    public void clear() {
        getPathStream().forEach(this::doDelete);
    }

    @Override
    public int size() {
        return (int) getPathStream().count();
    }

    private Stream<Path> getPathStream() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Directory read error", null);
        }
    }
}
