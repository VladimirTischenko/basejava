package ru.javawebinar.basejava.util;

import java.time.LocalDate;
import java.time.Month;

public class DateUtil {
    public static final LocalDate NOW = LocalDate.of(2030, 1, 1);

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static LocalDate toLocalDate(String s) {
        String[] strings = s.split("-");
        return of(Integer.parseInt(strings[0]), Month.of(Integer.parseInt(strings[1])));
    }
}
