package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.util.DateUtil;

import java.time.Month;
import java.util.UUID;

public class TestData {
    public static final String UUID_1 = UUID.randomUUID().toString();
    public static final String UUID_2 = UUID.randomUUID().toString();
    public static final String UUID_3 = UUID.randomUUID().toString();
    public static final String UUID_4 = UUID.randomUUID().toString();
    public static final Resume RESUME_1;
    public static final Resume RESUME_2;
    public static final Resume RESUME_3;
    public static final Resume RESUME_4;

    static {
        RESUME_1 = TestData.createResume(UUID_1,"Second");
        RESUME_2 = TestData.createResume(UUID_2, "First");
        RESUME_3 = TestData.createResume(UUID_3, "First");
        RESUME_4 = TestData.createResume(UUID_4, "Fourth");
    }

    public static Resume createResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);

        resume.setContact(ContactType.PHONE, "+7(921) 855-0482");
        resume.setContact(ContactType.SKYPE, "skype:grigory.kislin");
        resume.setContact(ContactType.LINKEDIN, "Профиль LinkedIn");

        TextSection objectiveSection = new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        resume.setSection(SectionType.OBJECTIVE, objectiveSection);

        TextSection personalSection = new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
        resume.setSection(SectionType.PERSONAL, personalSection);

        ListSection achievementSection = new ListSection("Организация команды и успешная реализация Java проектов для сторонних заказчиков: приложения ...",
                "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность...");
        resume.setSection(SectionType.ACHIEVEMENT, achievementSection);

        ListSection qualificationsSection = new ListSection("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
                "Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        resume.setSection(SectionType.QUALIFICATIONS, qualificationsSection);

        Organization.Position position111 = new Organization.Position(DateUtil.of(2013, Month.OCTOBER), DateUtil.NOW, "Автор проекта.",
                "Создание, организация и проведение Java онлайн проектов и стажировок.");
        Organization organization11 = new Organization("Java Online Projects", "https://javaops.ru/", position111);

        Organization.Position position112 = new Organization.Position(DateUtil.of(2014, Month.OCTOBER), DateUtil.of(2016, Month.JANUARY),
                "Старший разработчик (backend)",
                "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin...");
        Organization organization12 = new Organization("Wrike", "https://www.wrike.com/", position112);

        OrganizationSection experienceSection = new OrganizationSection(organization11, organization12);
        resume.setSection(SectionType.EXPERIENCE, experienceSection);

        Organization.Position position211 = new Organization.Position(DateUtil.of(2013, Month.MARCH), DateUtil.of(2013, Month.MAY),
                "'Functional Programming Principles in Scala' by Martin Odersky", null);
        Organization organization21 = new Organization("Coursera", "https://www.coursera.org/learn/progfun1", position211);

        Organization.Position position221 = new Organization.Position(DateUtil.of(2011, Month.MARCH), DateUtil.of(2011, Month.APRIL),
                "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'", null);
        Organization organization22 = new Organization("Luxoft", "https://prmotion.me/?ID=22366", position221);

        OrganizationSection educationSection = new OrganizationSection(organization21, organization22);
        resume.setSection(SectionType.EDUCATION, educationSection);

        return resume;
    }
}
