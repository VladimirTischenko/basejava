<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
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
        <h3>Секции:</h3>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:set var="typeName" value="${type.name()}"/>
            <c:set var="section" value="${resume.getSection(type)}"/>
            <c:choose>
                <c:when test="${type == \"OBJECTIVE\" || type == \"PERSONAL\"}">
                    <dl>
                        <dt>${type.title}</dt>
                        <dd><input type="text" name="${typeName}" size=120 value="${section}"></dd>
                    </dl>
                </c:when>
                <c:when test="${type == \"ACHIEVEMENT\" || type == \"QUALIFICATIONS\"}">
                    <dl>
                        <dt>${type.title}</dt>
                        <c:forEach var="string" items="${section.getList()}">
                            <dd><input type="text" name="${typeName}" size=120 value="${string}"></dd>
                        </c:forEach>
                    </dl>
                </c:when>
                <c:when test="${type == \"EXPERIENCE\" || type == \"EDUCATION\"}">
                    <dl>
                        <dt>${type.title}</dt>
                        <c:forEach var="organization" items="${section.getOrganizations()}">
                            <br><br>
                            <dd><input type="text" name="${type}organizationName" size=20 value="${organization.homePage.name}"></dd>
                            <dd><input type="text" name="${type}url" size=30 value="${organization.homePage.url}"></dd>
                            <c:set var="positions" value="${organization.getPositions()}"/>
                            <c:forEach var="position" items="${positions}">
                                <br>
                                <dd><input type="text" name="${type}startDate" size=10 value="${position.startDate}"></dd>
                                <dd><input type="text" name="${type}endDate" size=10 value="${position.endDate}"></dd>
                                <dd><input type="text" name="${type}title" size=100 value="${position.title}"></dd>
                                <c:if test="${type == \"EXPERIENCE\"}">
                                    <dd><input type="text" name="${type}description" size=120 value="${position.description}"></dd>
                                </c:if>
                                <input type="hidden" name="${type}size" value="${positions.size()}">
                            </c:forEach>
                        </c:forEach>
                    </dl>
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

