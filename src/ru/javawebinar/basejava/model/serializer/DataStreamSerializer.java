package ru.javawebinar.basejava.model.serializer;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.util.LocalDateAdapter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements Serializer {
    private final LocalDateAdapter adapter = new LocalDateAdapter();

    @Override
    public void serialize(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            Map<SectionType, Section> sections = resume.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                Section section = entry.getValue();
                String sectionClassSimpleName = section.getClass().getSimpleName();
                dos.writeUTF(sectionClassSimpleName);
                switch (sectionClassSimpleName) {
                    case "TextSection":
                        dos.writeUTF(((TextSection) section).getText());
                        break;
                    case "ListSection":
                        List<String> list = ((ListSection) section).getList();
                        dos.writeInt(list.size());
                        for (String s : list) {
                            dos.writeUTF(s);
                        }
                        break;
                    case "OrganizationSection":
                        List<Organization> organizations = ((OrganizationSection) section).getOrganizations();
                        dos.writeInt(organizations.size());
                        for (Organization organization : organizations) {
                            List<Organization.Position> positions = organization.getPositions();
                            dos.writeInt(positions.size());
                            for (Organization.Position position : positions) {
                                String description = position.getDescription();
                                dos.writeUTF(description == null ? "null" : description);
                                dos.writeUTF(adapter.marshal(position.getStartDate()));
                                dos.writeUTF(adapter.marshal(position.getEndDate()));
                                dos.writeUTF(position.getTitle());
                            }
                            Link homePage = organization.getHomePage();
                            dos.writeUTF(homePage.getName());
                            dos.writeUTF(homePage.getUrl());
                        }
                        break;
                }
            }
        }
    }

    @Override
    public Resume deserialize(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);

            int contactsSize = dis.readInt();
            for (int i = 0; i < contactsSize; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            int sectionsSize = dis.readInt();
            for (int i = 0; i < sectionsSize; i++) {
                String sectionType = dis.readUTF();
                switch (dis.readUTF()) {
                    case "TextSection":
                        resume.addSection(getSectionType(sectionType), new TextSection(dis.readUTF()));
                        break;
                    case "ListSection":
                        int listSize = dis.readInt();
                        List<String> list = new ArrayList<>(listSize);
                        for (int j = 0; j < listSize; j++) {
                            list.add(dis.readUTF());
                        }
                        resume.addSection(getSectionType(sectionType), new ListSection(list));
                        break;
                    case "OrganizationSection":
                        int organizationsSize = dis.readInt();
                        List<Organization> organizations = new ArrayList<>(organizationsSize);
                        for (int j = 0; j < organizationsSize; j++) {
                            int positionsSize = dis.readInt();
                            List<Organization.Position> positions = new ArrayList<>(positionsSize);
                            for (int k = 0; k < positionsSize; k++) {
                                String description = dis.readUTF();
                                description = description.equals("null") ? null : description;
                                positions.add(new Organization.Position(adapter.unmarshal(dis.readUTF()), adapter.unmarshal(dis.readUTF()),
                                        dis.readUTF(), description));
                            }
                            Organization organization = new Organization(new Link(dis.readUTF(), dis.readUTF()), positions);
                            organizations.add(organization);
                        }
                        resume.addSection(getSectionType(sectionType), new OrganizationSection(organizations));
                        break;
                }
            }

            return resume;
        }
    }

    private SectionType getSectionType(String s) {
        return SectionType.valueOf(s);
    }
}
