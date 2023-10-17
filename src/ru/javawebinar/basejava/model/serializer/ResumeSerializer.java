package ru.javawebinar.basejava.model.serializer;

import ru.javawebinar.basejava.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ResumeSerializer {
    void serialize(Resume resume, OutputStream outputStream) throws IOException;
    Resume deserialize(InputStream inputStream) throws IOException;
}
