package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.serializer.ObjectStreamSerializer;

class PathStorageTest extends AbstractStorageTest {
    protected PathStorageTest() {
        super(new PathStorage(DIRECTORY, new ObjectStreamSerializer()));
    }
}