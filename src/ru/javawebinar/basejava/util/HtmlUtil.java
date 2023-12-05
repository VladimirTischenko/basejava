package ru.javawebinar.basejava.util;

public class HtmlUtil {
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }
}
