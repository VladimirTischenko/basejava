package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage implements Storage {
    static final Comparator<Resume> FULLNAME_RESUME_COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = getResumesForSorting();
        resumes.sort(FULLNAME_RESUME_COMPARATOR);
        return resumes;
    }

    protected abstract List<Resume> getResumesForSorting();
}
