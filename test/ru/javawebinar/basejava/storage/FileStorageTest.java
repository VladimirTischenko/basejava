package ru.javawebinar.basejava.storage;

import java.io.File;

class FileStorageTest extends AbstractStorageTest {
    private static final File DIRECTORY = new File("./fileStorage");

    protected FileStorageTest() {
        super(new FileStorage(DIRECTORY));
    }
}