package ru.javawebinar.basejava.model;

public class TextSection implements Section {
    private final String title;
    private String text;

    public TextSection(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void print() {
        System.out.println(title + '\n' +
                text + '\n');
    }
}
