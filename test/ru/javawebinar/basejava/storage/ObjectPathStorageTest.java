package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.serializer.ObjectStreamSerializer;

class ObjectPathStorageTest extends AbstractStorageTest {
    protected ObjectPathStorageTest() {
        super(new PathStorage(DIRECTORY, new ObjectStreamSerializer()));
    }
}