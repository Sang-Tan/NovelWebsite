<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 3/18/2023
  Time: 4:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chỉnh sửa</title>
    <%@include file="/WEB-INF/view/layout/basic_stylesheet.jsp" %>
</head>
<body style="background-color: var(--silver)">
<%@include file="layout/header.jsp" %>
<div class="container mt-5">
    <div class="row">
        <div class="col-12 col-md-3">
            <div class="basic-section">
                <div class="basic-section__header">
                    <h4 class="basic-section__title">Mục lục</h4>
                </div>
                <jsp:include page="layout/novel_menu.jsp"/>

            </div>
        </div>
        <div class="col">
            <div class="basic-section">
                <div class="basic-section__header">
                    <h4 class="basic-section__title">Chỉnh sửa novel</h4>
                </div>
                <jsp:include page="layout/form_novel.jsp"/>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/view/layout/boostrap_js.jsp" %>
<script src="/js/init.js"></script>
</body>
</html>
