package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.util.DateUtil;

import java.time.Month;

public class ResumeTestData {
    public static Resume createResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);

        resume.addContact(ContactType.PHONE, "+7(921) 855-0482");
        resume.addContact(ContactType.SKYPE, "skype:grigory.kislin");
        resume.addContact(ContactType.LINKEDIN, "Профиль LinkedIn");

        TextSection objectiveSection = new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        resume.addSection(SectionType.OBJECTIVE, objectiveSection);

        TextSection personalSection = new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
        resume.addSection(SectionType.PERSONAL, personalSection);

        ListSection achievementSection = new ListSection("Организация команды и успешная реализация Java проектов для сторонних заказчиков: приложения ...",
                "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность...");
        resume.addSection(SectionType.ACHIEVEMENT, achievementSection);

        ListSection qualificationsSection = new ListSection("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
                "Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        resume.addSection(SectionType.QUALIFICATIONS, qualificationsSection);


        Organization.Position position111 = new Organization.Position(DateUtil.of(2013, Month.OCTOBER), DateUtil.NOW, "Автор проекта.",
                "Создание, организация и проведение Java онлайн проектов и стажировок.");
        Organization organization11 = new Organization("Java Online Projects", "https://javaops.ru/", position111);

        Organization.Position position112 = new Organization.Position(DateUtil.of(2014, Month.OCTOBER), DateUtil.of(2016, Month.JANUARY),
                "Старший разработчик (backend)",
                "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin...");
        Organization organization12 = new Organization("Wrike", "https://www.wrike.com/", position112);

        OrganizationSection experienceSection = new OrganizationSection(organization11, organization12);
        resume.addSection(SectionType.EXPERIENCE, experienceSection);


        Organization.Position position211 = new Organization.Position(DateUtil.of(2013, Month.MARCH), DateUtil.of(2013, Month.MAY),
                "'Functional Programming Principles in Scala' by Martin Odersky", null);
        Organization organization21 = new Organization("Coursera", "https://www.coursera.org/learn/progfun1", position211);

        Organization.Position position221 = new Organization.Position(DateUtil.of(2011, Month.MARCH), DateUtil.of(2011, Month.APRIL),
                "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'", null);
        Organization organization22 = new Organization("Luxoft", "https://prmotion.me/?ID=22366", position221);

        OrganizationSection educationSection = new OrganizationSection(organization21, organization22);
        resume.addSection(SectionType.EDUCATION, educationSection);

        return resume;
    }
}
