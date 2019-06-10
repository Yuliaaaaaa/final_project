<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page='mainPage.jsp'/>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="${bundle}"/>

<html>
<head>
    <meta charset="UTF-8">
    <title>UserPage</title>
</head>
<body>
<div class="box">
    <h3><fmt:message key="menu.myPatients"/></h3>
    <form method="post">
        <div class="box"
             style="display: block; width: auto; position: absolute; z-index: 999; font-size: 20px">
            <fmt:message key="label.patient"/>:
            <select name="patientId" style="float: left; width: 80px">
                <c:forEach items="${allPatients}" var="patient">
                    <option value="${patient.getId()}">${patient.getSurname()} ${patient.getName()}</option>
                </c:forEach>
            </select>
            <br>
            <fmt:message key="label.diagnosis"/>: <input name="diagnosis" type="text"/>
            <br>
            <c:if test="${fail}"><fmt:message key="error.fieldsNotFilled"/>!</c:if>
            <input name="method" value="add_diagnosis" type="hidden"/>
            <button onclick="this.form.submit()"><fmt:message key="registration.label.patient"/></button>
        </div>
    </form>
    <table>
        <tr>
            <th><fmt:message key="user.surname"/></th>
            <th><fmt:message key="user.name"/></th>
            <th><fmt:message key="user.sex"/></th>
            <th><fmt:message key="user.birthday"/></th>
            <th><fmt:message key="patient.diagnoses"/></th>
        </tr>
        <form method="get" action="/diagnoses">
            <c:forEach items="${doctorsPatients}" var="patient">
            <tr>
                <th><c:out value="${patient.getSurname()}"/></th>
                <th><c:out value="${patient.getName()}"/></th>
                <th><c:out value="${patient.getSex()}"/></th>
                <th><c:out value="${patient.getBirthday_date()}"/></th>
                <th>
                    <button name="patientId" value="${patient.getId()}" scope="request"><fmt:message
                            key="patient.show.diagnosis"/>
                    </button>
                </th>
            </tr>
            </c:forEach>
    </table>
</div>
</body>
</html>
