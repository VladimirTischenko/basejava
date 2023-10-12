package ru.javawebinar.basejava.model;

import java.util.*;

/**
 * Initial resume class
 */
public class Resume {

    // Unique identifier
    private final String uuid;
    private final String fullName;

    private final Map<String, String> contacts = new LinkedHashMap<>();

    private final Map<Integer, Section> sectionTypes = new TreeMap<>();

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public Map<String, String> getContacts() {
        return contacts;
    }

    public Map<Integer, Section> getSectionTypes() {
        return sectionTypes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (!uuid.equals(resume.uuid)) return false;
        return fullName.equals(resume.fullName);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + fullName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Resume{" +
                "uuid='" + uuid + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }

    public void print() {
        StringBuilder result = new StringBuilder(this.getFullName() + '\n');
        result.append('\n');

        for (Map.Entry<String, String> entry : contacts.entrySet()) {
            result.append(entry.getKey());
            String value = entry.getValue();
            if (value != null) {
                result.append(" : ").append(value);
            }
            result.append('\n');
        }

        System.out.println(result);

        for (Map.Entry<Integer, Section> entry : sectionTypes.entrySet()) {
            entry.getValue().print();
        }
    }
}
