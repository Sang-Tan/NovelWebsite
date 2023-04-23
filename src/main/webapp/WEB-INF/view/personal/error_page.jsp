<%--@elvariable id="errorMessage" type="java.lang.String"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Lỗi</title>
    <%@include file="/WEB-INF/view/layout/basic_stylesheet.jsp" %>
</head>
<body>
<%@include file="layout/header.jsp" %>
<div class="d-flex align-items-center justify-content-center">
    <h1>${errorMessage}</h1>
</div>
</body>
</html>
