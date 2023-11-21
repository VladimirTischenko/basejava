package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.Storage;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    final Storage storage = Config.get().getStorage();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
//        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        resp.getWriter().write(getHtml(req.getParameter("uuid")));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

    }

    private String getHtml(String uuid) {
        if (uuid == null) {
            List<Resume> resumes = storage.getAllSorted();
            StringBuilder builder = new StringBuilder();
            builder.append("<table><tr><th>Uuid</th><th>Full name</th></tr>");
            if (resumes.size() > 0) {
                resumes.forEach(resume -> builder.append("<tr><td>").append(resume.getUuid())
                        .append("</td><td>").append(resume.getFullName()).append("</td></tr>"));
            }
            builder.append("</table>");
            return builder.toString();
        } else {
            return storage.get(uuid).toString();
        }
    }
}
