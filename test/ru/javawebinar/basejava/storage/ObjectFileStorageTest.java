package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.serializer.ObjectStreamSerializer;

import java.io.File;

class ObjectFileStorageTest extends AbstractStorageTest {
    protected ObjectFileStorageTest() {
        super(new FileStorage(new File(DIRECTORY), new ObjectStreamSerializer()));
    }
}