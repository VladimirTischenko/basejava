package ru.javawebinar.basejava.util;

import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.Section;
import ru.javawebinar.basejava.model.TextSection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.javawebinar.basejava.TestData.RESUME_1;

class JsonParserTest {

    @Test
    void testResume() {
        String s = JsonParser.write(RESUME_1, Resume.class);
        Resume resume = JsonParser.read(s, Resume.class);
        assertEquals(RESUME_1, resume);
    }

    @Test
    void write() {
        TextSection section1 = new TextSection("TextSection1");
        String s = JsonParser.write(section1, Section.class);
        System.out.println(s);
        Section section2 = JsonParser.read(s, Section.class);
        assertEquals(section1, section2);
    }
}