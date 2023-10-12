package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

public class OrganizationSection implements Section {
    private final String title;
    private List<Node> list = new ArrayList<>();

    public OrganizationSection(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public List<Node> getList() {
        return list;
    }

    public void setList(List<Node> list) {
        this.list = list;
    }

    public class Node {
        private String name;
        private String period;
        private String position;
        private String description;

        public void setName(String name) {
            this.name = name;
        }

        public void setPeriod(String period) {
            this.period = period;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    @Override
    public void print() {
        StringBuilder result = new StringBuilder(title + '\n');
        for (Node node : list) {
            if (node.name != null) {
                result.append('\n');
                result.append(node.name).append('\n');
            }
            if (node.period != null) {
                result.append(node.period).append('\n');
            }
            if (node.position != null) {
                result.append(node.position).append('\n');
            }
            if (node.description != null) {
                result.append(node.description).append('\n');
            }
        }
        System.out.println(result);
    }
}
