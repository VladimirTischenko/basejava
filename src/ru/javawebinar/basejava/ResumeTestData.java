package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.util.DateUtil;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResumeTestData {
    public static Resume createResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);

        Map<ContactType, String> contacts = resume.getContacts();
        contacts.put(ContactType.PHONE, "+7(921) 855-0482");
        contacts.put(ContactType.SKYPE, "skype:grigory.kislin");
        contacts.put(ContactType.LINKEDIN, "Профиль LinkedIn");

        Map<SectionType, Section> sections = resume.getSections();

        TextSection objectiveSection = new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        sections.put(SectionType.OBJECTIVE, objectiveSection);

        TextSection personalSection = new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
        sections.put(SectionType.PERSONAL, personalSection);

        List<String> achievementList = new ArrayList<>();
        achievementList.add("Организация команды и успешная реализация Java проектов для сторонних заказчиков: приложения автопарк на стеке Spring " +
                "Cloud/микросервисы, система мониторинга показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный " +
                "Spring Boot + Vaadin проект для комплексных DIY смет");
        achievementList.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. " +
                "XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. " +
                "Более 3500 выпускников.");
        ListSection achievementSection = new ListSection(achievementList);
        sections.put(SectionType.ACHIEVEMENT, achievementSection);

        List<String> qualificationsList = new ArrayList<>();
        qualificationsList.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualificationsList.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        ListSection qualificationsSection = new ListSection(qualificationsList);
        sections.put(SectionType.QUALIFICATIONS, qualificationsSection);


        OrganizationParticipation organizationParticipation111 = new OrganizationParticipation(DateUtil.of(2013, Month.OCTOBER),
                LocalDate.now(), "Автор проекта.",
                "Создание, организация и проведение Java онлайн проектов и стажировок.");
        ArrayList<OrganizationParticipation> organizationParticipations11 = new ArrayList<>();
        organizationParticipations11.add(organizationParticipation111);
        Organization organization11 = new Organization("Java Online Projects", "https://javaops.ru/", organizationParticipations11);

        OrganizationParticipation organizationParticipation112 = new OrganizationParticipation(DateUtil.of(2014, Month.OCTOBER),
                DateUtil.of(2016, Month.JANUARY), "Старший разработчик (backend)",
                "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, " +
                        "Redis). Двухфакторная аутентификация, авторизация  по OAuth1, OAuth2, JWT SSO.");
        ArrayList<OrganizationParticipation> organizationParticipations12 = new ArrayList<>();
        organizationParticipations12.add(organizationParticipation112);
        Organization organization12 = new Organization("Wrike", "https://www.wrike.com/", organizationParticipations12);

        ArrayList<Organization> experienceList = new ArrayList<>();
        experienceList.add(organization11);
        experienceList.add(organization12);
        OrganizationSection experienceSection = new OrganizationSection(experienceList);
        sections.put(SectionType.EXPERIENCE, experienceSection);


        OrganizationParticipation organizationParticipation211 = new OrganizationParticipation(DateUtil.of(2013, Month.MARCH),
                DateUtil.of(2013, Month.MAY), "'Functional Programming Principles in Scala' by Martin Odersky", null);
        ArrayList<OrganizationParticipation> organizationParticipations21 = new ArrayList<>();
        organizationParticipations21.add(organizationParticipation211);
        Organization organization21 = new Organization("Coursera", "https://www.coursera.org/learn/progfun1", organizationParticipations21);

        OrganizationParticipation organizationParticipation221 = new OrganizationParticipation(DateUtil.of(2011, Month.MARCH),
                DateUtil.of(2011, Month.APRIL), "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'", null);
        ArrayList<OrganizationParticipation> organizationParticipations22 = new ArrayList<>();
        organizationParticipations22.add(organizationParticipation221);
        Organization organization22 = new Organization("Luxoft", "https://prmotion.me/?ID=22366", organizationParticipations22);

        ArrayList<Organization> educationList = new ArrayList<>();
        educationList.add(organization21);
        educationList.add(organization22);
        OrganizationSection educationSection = new OrganizationSection(educationList);
        sections.put(SectionType.EDUCATION, educationSection);

        return resume;
    }
}
