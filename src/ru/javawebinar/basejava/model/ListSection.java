package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

public class ListSection implements Section {
    private final String title;
    private List<String> list = new ArrayList<>();

    public ListSection(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @Override
    public void print() {
        StringBuilder result = new StringBuilder(title + '\n');
        for (String s : list) {
            result.append(s).append('\n');
        }
        System.out.println(result);
    }
}
