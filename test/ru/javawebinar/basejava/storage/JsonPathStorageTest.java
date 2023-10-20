package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.serializer.JsonStreamSerializer;

class JsonPathStorageTest extends AbstractStorageTest {
    protected JsonPathStorageTest() {
        super(new PathStorage(DIRECTORY, new JsonStreamSerializer()));
    }
}