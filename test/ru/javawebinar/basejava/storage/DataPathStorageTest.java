package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.serializer.DataStreamSerializer;

class DataPathStorageTest extends AbstractStorageTest {
    protected DataPathStorageTest() {
        super(new PathStorage(DIRECTORY, new DataStreamSerializer()));
    }
}