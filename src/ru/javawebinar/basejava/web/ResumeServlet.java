package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.Storage;
import ru.javawebinar.basejava.util.DateUtil;
import ru.javawebinar.basejava.util.HtmlUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    final Storage storage = Config.get().getStorage();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuid = req.getParameter("uuid");
        String action = req.getParameter("action");
        if (action == null) {
            req.setAttribute("resumes", storage.getAllSorted());
            req.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(req, resp);
            return;
        }
        Resume resume;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                resp.sendRedirect("resume");
                return;
            case "add":
                resume = new Resume();
                break;
            case "view":
                resume = storage.get(uuid);
                break;
            case "edit":
                resume = storage.get(uuid);
                for (SectionType type : new SectionType[]{SectionType.EXPERIENCE, SectionType.EDUCATION}) {
                    OrganizationSection section = (OrganizationSection) resume.getSection(type);
                    List<Organization> emptyFirstOrganizations = new ArrayList<>();
                    emptyFirstOrganizations.add(Organization.EMPTY);
                    if (section != null) {
                        for (Organization org : section.getOrganizations()) {
                            List<Organization.Position> emptyFirstPositions = new ArrayList<>();
                            emptyFirstPositions.add(Organization.Position.EMPTY);
                            emptyFirstPositions.addAll(org.getPositions());
                            emptyFirstOrganizations.add(new Organization(org.getHomePage(), emptyFirstPositions));
                        }
                    }
                    resume.setSection(type, new OrganizationSection(emptyFirstOrganizations));
                }
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        req.setAttribute("resume", resume);
        req.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        String uuid = req.getParameter("uuid");
        String fullName = req.getParameter("fullName");
        boolean isNew = uuid.equals("");
        Resume r = isNew ? new Resume(fullName) : storage.get(uuid);
        if (!isNew) {
            r.setFullName(fullName);
        }
        for (ContactType type : ContactType.values()) {
            String value = req.getParameter(type.name());
            if (HtmlUtil.isEmpty(value)) {
                r.getContacts().remove(type);
            } else {
                r.setContact(type, value);
            }
        }
        for (SectionType type : SectionType.values()) {
            String value = req.getParameter(type.name());
            String[] values = req.getParameterValues(type.name());
            if (HtmlUtil.isEmpty(value) && values.length < 2) {
                r.getSections().remove(type);
            }
            switch (type) {
                case OBJECTIVE:
                case PERSONAL:
                    r.setSection(type, new TextSection(value));
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    r.setSection(type, new ListSection(values));
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    String[] urls = req.getParameterValues(type + "url");
                    List<Organization> organizations = new ArrayList<>();
                    for (int i = 0; i < values.length; i++) {
                        String name = values[i];
                        if (!HtmlUtil.isEmpty(name)) {
                            List<Organization.Position> positions = new ArrayList<>();
                            String pfx = type.name() + i;

                            String[] startDates = req.getParameterValues(pfx + "startDate");
                            String[] endDates = req.getParameterValues(pfx + "endDate");
                            String[] titles = req.getParameterValues(pfx + "title");
                            String[] descriptions = req.getParameterValues(pfx + "description");

                            for (int j = 0; j < titles.length; j++) {
                                if (!HtmlUtil.isEmpty(titles[j])) {
                                    String description = type == SectionType.EDUCATION ? null : descriptions[j];
                                    Organization.Position position = new Organization.Position(
                                            DateUtil.toLocalDate(startDates[j]), DateUtil.toLocalDate(endDates[j]), titles[j], description);
                                    positions.add(position);
                                }
                            }
                            organizations.add(new Organization(new Link(values[i], urls[i]), positions));
                        }
                    }
                    r.setSection(type, new OrganizationSection(organizations));
            }
        }
        if (isNew) {
            storage.save(r);
        } else {
            storage.update(r);
        }
        resp.sendRedirect("resume");
    }
}
