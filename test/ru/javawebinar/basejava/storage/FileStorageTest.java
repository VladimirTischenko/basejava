package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.serializer.ObjectStreamResumeSerializer;

import java.io.File;

class FileStorageTest extends AbstractStorageTest {
    protected FileStorageTest() {
        super(new FileStorage(new File(DIRECTORY), new ObjectStreamResumeSerializer()));
    }
}