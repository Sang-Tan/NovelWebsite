<%--@elvariable id="novels" type="java.util.Collection<model.Novel>"--%>
<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 3/17/2023
  Time: 2:49 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cá nhân</title>
    <%@include file="/WEB-INF/view/layout/basic_stylesheet.jsp" %>
</head>
<body>
<%@include file="layout/header.jsp" %>

<div class="container mt-3">
    <div class="basic-section">
        <div class="basic-section__header ">
            <h4>Truyện đã đăng (${novels.size()})</h4>
        </div>
        <div class="containter-fluid ml-3 mr-3">
            <div class="row">
                <c:forEach items="${novels}" var="novel">
                    <div class="col-3 col-md-2 thumb">
                        <a href="/ca-nhan/tieu-thuyet/${novel.id}" class="no-decor">
                            <div class="thumb__wrapper">
                                <div class="thumb__img-panel a6-ratio">
                                    <div class="img-wrapper border "
                                         style="background-image: url('${novel.image}');">
                                    </div>
                                </div>
                                <p class="thumb__caption">${novel.name}</p>
                            </div>
                        </a>
                    </div>
                </c:forEach>

            </div>
        </div>

    </div>
</div>

<%-- boostrap script --%>
<%@include file="/WEB-INF/view/layout/basic_js.jsp" %>
</body>
</html>
