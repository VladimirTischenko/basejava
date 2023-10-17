package ru.javawebinar.basejava;

import java.io.File;
import java.util.Objects;

public class MainFile {
    public static void main(String[] args) {
        File dir = new File(".");
        printFileNameByRecursion(dir, "");
    }

    static void printFileNameByRecursion(File dir, String indent) {
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.isFile()) {
                System.out.println(indent + "File: " + file.getAbsolutePath());
            } else {
                System.out.println(indent + "Directory: " + file.getAbsolutePath());
                printFileNameByRecursion(file, indent + "    ");
            }
        }
    }
}
