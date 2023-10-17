package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.serializer.ObjectStreamResumeSerializer;

class PathStorageTest extends AbstractStorageTest {
    protected PathStorageTest() {
        super(new PathStorage(DIRECTORY, new ObjectStreamResumeSerializer()));
    }
}