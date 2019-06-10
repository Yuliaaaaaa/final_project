<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="${bundle}"/>

<jsp:include page='../pages/mainPage.jsp'/>

<html>
<head>
    <meta charset="UTF-8">
    <title>Error</title>
</head>
<body>
<h1><fmt:message key="error.notReached"/>!</h1>
<a href="/main"><fmt:message key="button.return.toMain"/></a>
</body>
</html>
