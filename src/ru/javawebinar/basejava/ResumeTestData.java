package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;

import java.util.List;
import java.util.Map;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("Test Resume");

        Map<String, String> contacts = resume.getContacts();
        contacts.put("Тел.", "+7(921) 855-0482");
        contacts.put("Skype", "skype:grigory.kislin");
        contacts.put("Профиль LinkedIn", null);

        Map<Integer, Section> sectionTypes = resume.getSectionTypes();

        SectionType objective = SectionType.OBJECTIVE;
        TextSection objectiveTS = new TextSection(objective.getTitle());
        objectiveTS.setText("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        sectionTypes.put(objective.ordinal(), objectiveTS);

        SectionType personal = SectionType.PERSONAL;
        TextSection personalTS = new TextSection(personal.getTitle());
        personalTS.setText("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
        sectionTypes.put(personal.ordinal(), personalTS);

        SectionType achievement = SectionType.ACHIEVEMENT;
        ListSection achievementTS = new ListSection(achievement.getTitle());
        List<String> achievementTSList = achievementTS.getList();
        achievementTSList.add("Организация команды и успешная реализация Java проектов для сторонних заказчиков: приложения автопарк на стеке Spring " +
                "Cloud/микросервисы, система мониторинга показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный " +
                "Spring Boot + Vaadin проект для комплексных DIY смет");
        achievementTSList.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. " +
                "XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. " +
                "Более 3500 выпускников.");
        sectionTypes.put(achievement.ordinal(), achievementTS);

        SectionType qualifications = SectionType.QUALIFICATIONS;
        ListSection qualificationsTS = new ListSection(qualifications.getTitle());
        List<String> qualificationsTSList = qualificationsTS.getList();
        qualificationsTSList.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualificationsTSList.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        sectionTypes.put(qualifications.ordinal(), qualificationsTS);

        SectionType experience = SectionType.EXPERIENCE;
        OrganizationSection experienceTS = new OrganizationSection(experience.getTitle());
        List<OrganizationSection.Node> experienceTSList = experienceTS.getList();
        OrganizationSection.Node node31 = experienceTS.new Node();
        node31.setName("Java Online Projects");
        node31.setPeriod("10/2013 - Сейчас");
        node31.setPosition("Автор проекта.");
        node31.setDescription("Создание, организация и проведение Java онлайн проектов и стажировок.");
        experienceTSList.add(node31);
        OrganizationSection.Node node32 = experienceTS.new Node();
        node32.setName("Wrike");
        node32.setPeriod("10/2014 - 01/2016");
        node32.setPosition("Старший разработчик (backend)");
        node32.setDescription("Проектирование и разработка онлайн платформы управления проектами Wrike " +
                "(Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
        experienceTSList.add(node32);
        sectionTypes.put(experience.ordinal(), experienceTS);

        SectionType education = SectionType.EDUCATION;
        OrganizationSection educationTS = new OrganizationSection(education.getTitle());
        List<OrganizationSection.Node> educationTSList = educationTS.getList();
        OrganizationSection.Node node41 = educationTS.new Node();
        node41.setName("Coursera");
        node41.setPeriod("03/2013 - 05/2013");
        node41.setPosition("'Functional Programming Principles in Scala' by Martin Odersky");
        educationTSList.add(node41);
        OrganizationSection.Node node42 = educationTS.new Node();
        node42.setName("Luxoft");
        node42.setPeriod("03/2011 - 04/2011");
        node42.setPosition("Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'");
        educationTSList.add(node42);
        sectionTypes.put(education.ordinal(), educationTS);

        resume.print();
    }
}
