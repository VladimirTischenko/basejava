package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.serializer.XmlStreamSerializer;

class XmlPathStorageTest extends AbstractStorageTest {
    protected XmlPathStorageTest() {
        super(new PathStorage(DIRECTORY, new XmlStreamSerializer()));
    }
}