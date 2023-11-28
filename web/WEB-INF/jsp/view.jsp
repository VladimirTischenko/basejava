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
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry" type="java.util.Map.Entry<ru.javawebinar.basejava.model.ContactType, java.lang.String>"/>
            <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br>
        </c:forEach>
    </p>
    <hr>
    <p>
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry" type="java.util.Map.Entry<ru.javawebinar.basejava.model.SectionType, ru.javawebinar.basejava.model.Section>"/>
            <c:set var="type" value="${sectionEntry.key}"/>
            <c:set var="section" value="${sectionEntry.value}"/>
            <strong>${type.title}</strong><br>
            <c:choose>
                <c:when test="${type == \"OBJECTIVE\" || type == \"PERSONAL\"}">
                    <br>${section}<br><br>
                </c:when>
                <c:when test="${type == \"ACHIEVEMENT\" || type == \"QUALIFICATIONS\"}">
                    <c:forEach var="string" items="${section.list}">
                        <li>${string}</li>
                    </c:forEach>
                </c:when>
                <c:when test="${type == \"EXPERIENCE\" || type == \"EDUCATION\"}">
                    <c:forEach var="organization" items="${section.organizations}">
                        <jsp:useBean id="organization" type="ru.javawebinar.basejava.model.Organization"/>
                        <br><%=organization.getHomePage().toHtml()%><br>
                        <c:forEach var="position" items="${organization.positions}">
                            <jsp:useBean id="position" type="ru.javawebinar.basejava.model.Organization.Position"/>
                            ${position.startDate}&nbsp-&nbsp${position.endDate}&nbsp<strong>${position.title}</strong><br>
                            <c:set var="description" value="${position.description}"/>
                            <c:if test="${description != null}">
                                ${description}<br>
                            </c:if>
                        </c:forEach>
                    </c:forEach><br>
                </c:when>
            </c:choose>
        </c:forEach>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
