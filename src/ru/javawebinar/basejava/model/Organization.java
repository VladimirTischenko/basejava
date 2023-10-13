package ru.javawebinar.basejava.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Organization implements Serializable {
    private final Link homePage;

    private final List<OrganizationParticipation> times;

    public Organization(String name, String url, List<OrganizationParticipation> times) {
        Objects.requireNonNull(times, "times must not be null");
        this.homePage = new Link(name, url);
        this.times = times;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!homePage.equals(that.homePage)) return false;
        return times.equals(that.times);
    }

    @Override
    public int hashCode() {
        int result = homePage.hashCode();
        result = 31 * result + times.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "homePage=" + homePage +
                ", times=" + times +
                '}';
    }
}
