<%--@elvariable id="user" type="model.User"--%>
<%--@elvariable id="uploadedNovelCount" type="java.lang.Integer"--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cá nhân</title>
    <%@include file="/WEB-INF/view/layout/basic_stylesheet.jsp" %>
</head>

<body>
<%@include file="layout/header.jsp" %>
<div class="d-flex justify-content-center align-items-center pt-5">
    <!--<div class="basic-section p-5">
        <div class="square-ratio" style="width: 140px;">
            <div class="img-wrapper border" style="background-image: url(${user.avatar})"></div>
        </div>
        <p><span style="font-weight: bold">Username:</span> ${user.username}</p>
        <p><span style="font-weight: bold">Tên hiển thị :</span> ${user.displayName}</p>
    </div>-->
    <div class="basic-section" style="width: 25rem;">
        <div class="basic-section__header">
            <div class="square-ratio" style="width: 80px;">
                <div class="img-wrapper border" style="background-image: url(${user.avatar})"></div>
            </div>
        </div>
        <div class="pl-3">
            <p><span style="font-weight: bold">Username:</span> ${user.username}</p>
            <p><span style="font-weight: bold">Tên hiển thị:</span> ${user.displayName}</p>
            <p><span style="font-weight: bold">Số truyện đã đăng:</span> ${uploadedNovelCount}</p>
        </div>
    </div>
</div>
</body>
</html>
