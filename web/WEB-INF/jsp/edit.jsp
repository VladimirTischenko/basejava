<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
<%@ page import="ru.javawebinar.basejava.model.ListSection" %>
<%@ page import="ru.javawebinar.basejava.model.OrganizationSection" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <hr>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:set var="typeName" value="${type.name()}"/>
            <c:set var="section" value="${resume.getSection(type)}"/>
            <jsp:useBean id="section" type="ru.javawebinar.basejava.model.Section"/>
            <h3><a>${type.title}</a></h3>
            <c:choose>
                <c:when test="${type == \"OBJECTIVE\" || type == \"PERSONAL\"}">
                    <input type="text" name="${typeName}" size=120 value="${section}">
                </c:when>
                <c:when test="${type == \"ACHIEVEMENT\" || type == \"QUALIFICATIONS\"}">
                    <c:forEach var="string" items="<%=((ListSection) section).getList()%>">
                        <input type="text" name="${typeName}" size=120 value="${string}">
                    </c:forEach>
                </c:when>
                <c:when test="${type == \"EXPERIENCE\" || type == \"EDUCATION\"}">
                    <c:forEach var="organization" items="<%=((OrganizationSection) section).getOrganizations()%>" varStatus="counter">
                        <dl>
                            <dt>Название учереждения:</dt>
                            <dd><input type="text" name="${type}" size=20 value="${organization.homePage.name}"></dd>
                        </dl>
                        <dl>
                            <dt>Сайт учереждения:</dt>
                            <dd><input type="text" name="${type}url" size=30 value="${organization.homePage.url}"></dd>
                        </dl>
                        <c:forEach var="position" items="${organization.positions}">
                            <br>
                            <dl>
                                <dt>Начальная дата:</dt>
                                <dd><input type="text" name="${type}${counter.index}startDate" size=10 value="${position.startDate}"></dd>
                            </dl>
                            <dl>
                                <dt>Конечная дата:</dt>
                                <dd><input type="text" name="${type}${counter.index}endDate" size=10 value="${position.endDate}"></dd>
                            </dl>
                            <dl>
                                <dt>Должность:</dt>
                                <dd><input type="text" name="${type}${counter.index}title" size=120 value="${position.title}"></dd>
                            </dl>
                            <c:if test="${type == \"EXPERIENCE\"}">
                                <dl>
                                    <dt>Описание:</dt>
                                    <dd><input type="text" name="${type}${counter.index}description" size=120 value="${position.description}"></dd>
                                </dl>
                            </c:if>
                        </c:forEach>
                        <br>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button type="button" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>

