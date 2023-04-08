<%--@elvariable id="userInWall" type="model.User"--%>
<%--@elvariable id="userNovels" type="java.util.Collection<model.Novel>"--%>
<%--@elvariable id="User" type="model.User.class"--%>
<%@page import="model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${userInWall.displayName}</title>
    <%@include file="layout/basic_stylesheet.jsp" %>
    <link href="/css/user_wall.css" rel="stylesheet">
</head>
<body style="background-color: var(--silver)">
<%@include file="layout/header_main.jsp" %>
<div class="container mt-4">
    <div class="row">
        <div class="col-12 col-lg-3">
            <div class="basic-section mt-2" style="background-color: var(--dark-silver);">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-3 col-lg-5">
                            <div class="mt-2 mb-2">
                                <div class="square-ratio border-radius-50">
                                    <div class="img-wrapper"
                                         style="background-image: url('${userInWall.avatar}')"></div>
                                </div>
                            </div>
                        </div>
                        <div class="col-7 pl-0">
                            <div class="d-flex flex-column justify-content-center h-100">
                                <div class="text-center">
                                    <h6 title="${userInWall.displayName}"
                                        class="overflow-elipsis overflow-elipsis--wrap w-700">${userInWall.displayName}</h6>
                                </div>
                                <div class="pr-3 pl-3">
                                    <c:choose>
                                        <c:when test="${userInWall.role == User.ROLE_MEMBER}">
                                            <div class="role-box role-box--member">
                                                Member
                                            </div>
                                        </c:when>
                                        <c:when test="${userInWall.role == User.ROLE_ADMIN}">
                                            <div class="role-box role-box--admin">
                                                Admin
                                            </div>
                                        </c:when>
                                        <c:when test="${userInWall.role == User.ROLE_MODERATOR}">
                                            <div class="role-box role-box--moderator">
                                                Moderator
                                            </div>
                                        </c:when>
                                    </c:choose>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
        <div class="col ml-4">
            <header class="mb-3">
                <span class="title title--bold">Truyện đã đăng</span>
            </header>
            <div class="row pl-1">
                <c:forEach items="${userNovels}" var="userNovel">
                    <div class="col-3 col-xl-2 thumb">
                        <a href="/testui/novel_detail" class="no-decor">
                            <div class="thumb__wrapper">
                                <div class="thumb__img-panel shadow a6-ratio">
                                    <div class="img-wrapper img-wrapper--shadow"
                                         style="background-image: url('${userNovel.image}');">
                                    </div>
                                </div>
                                <p class="thumb__caption">${userNovel.name}</p>
                            </div>
                        </a>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
    <%@include file="layout/boostrap_js.jsp" %>
</body>
</html>
