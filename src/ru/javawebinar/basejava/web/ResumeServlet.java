package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.Storage;
import ru.javawebinar.basejava.util.DateUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

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
            case "view":
            case "edit":
                resume = storage.get(uuid);
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
        Resume r = storage.get(uuid);
        r.setFullName(fullName);
        for (ContactType type : ContactType.values()) {
            String value = req.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.addContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            switch (type) {
                case OBJECTIVE:
                case PERSONAL:
                    String value = req.getParameter(type.name());
                    if (value != null && value.trim().length() != 0) {
                        r.addSection(type, new TextSection(value));
                    } else {
                        r.getSections().remove(type);
                    }
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    String[] values = req.getParameterValues(type.name());
                    if (values.length > 0) {
                        r.addSection(type, new ListSection(values));
                    } else {
                        r.getSections().remove(type);
                    }
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    String[] organizationNames = req.getParameterValues(type + "organizationName");
                    String[] urls = req.getParameterValues(type + "url");
                    String[] startDates = req.getParameterValues(type + "startDate");
                    String[] endDates = req.getParameterValues(type + "endDate");
                    String[] titles = req.getParameterValues(type + "title");
                    String[] descriptions = req.getParameterValues(type + "description");
                    String[] size = req.getParameterValues(type + "size");
                    if (organizationNames.length > 0 || urls.length > 0 || startDates.length > 0 || endDates.length > 0 || titles.length > 0) {
                        List<Organization> organizations = new ArrayList<>();
                        int positionsSize;
                        int count = 0;
                        for (int i = 0; i < titles.length; i++) {
                            positionsSize = Integer.parseInt(size[i]);
                            List<Organization.Position> positions = new ArrayList<>();
                            for (int j = 0; j < positionsSize; j++, count++) {
                                String description = type == SectionType.EDUCATION ? null : descriptions[count];
                                Organization.Position position = new Organization.Position(
                                        DateUtil.toLocalDate(startDates[count]), DateUtil.toLocalDate(endDates[count]), titles[count], description);
                                positions.add(position);
                            }
                            organizations.add(new Organization(new Link(organizationNames[i], urls[i]), positions));
                        }
                        r.addSection(type, new OrganizationSection(organizations));
                    } else {
                        r.getSections().remove(type);
                    }
            }
        }
        storage.update(r);
        resp.sendRedirect("resume");
    }
}
