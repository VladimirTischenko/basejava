package ru.javawebinar.basejava.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public record Link(String name, String url) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public Link {
        Objects.requireNonNull(name, "name must not be null");
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Link link = (Link) o;

        if (!name.equals(link.name)) return false;
        return Objects.equals(url, link.url);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Link{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}