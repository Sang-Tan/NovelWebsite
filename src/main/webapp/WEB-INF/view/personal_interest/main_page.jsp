<%--@elvariable id="interest" type="core.metadata.PersonalInterest"--%>
<%--@elvariable id="PersonalInterest" type="core.metadata.PersonalInterest.class"--%>
<%--@elvariable id="user" type="model.User"--%>

<%@page import="core.metadata.PersonalInterest" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>
        <c:choose>
            <c:when test="${interest.equals(PersonalInterest.READING_LIST)}">
                Truyện theo dõi
            </c:when>
            <c:when test="${interest.equals(PersonalInterest.BOOKMARK)}">
                Đánh dấu
            </c:when>
            <c:when test="${interest.equals(PersonalInterest.NOTIFICATION)}">
                Thông báo
            </c:when>
        </c:choose>
    </title>
    <%@include file="/WEB-INF/view/layout/basic_stylesheet.jsp" %>
</head>
<body style="background-color: var(--silver)">
<%@include file="/WEB-INF/view/layout/header_main.jsp" %>
<div class="container mt-3">
    <div class="row">
        <div class="col-3">
            <div class="basic-section">
                <div class="basic-section__header flex-column align-items-start
            justify-content-center mb-0">
                    <h5>Tài khoản</h5>
                    <p class="mb-0">${user.username}</p>
                </div>
                <table class="table table-hover mb-0">
                    <tbody>
                    <tr>
                        <td class="p-0">
                            <a class="theme-link link-fill p-3 ${interest.equals(PersonalInterest.READING_LIST) ? "left-highlight": ""}"
                               href="/theo-doi">Truyện
                                theo dõi</a>
                        </td>
                    </tr>
                    <tr>
                        <td class="p-0">
                            <a class="theme-link link-fill p-3 ${interest.equals(PersonalInterest.BOOKMARK) ? "left-highlight": ""}"
                               href="/danh-dau">Bookmark</a>
                        </td>
                    </tr>
                    <tr>
                        <td class="p-0">
                            <a class="theme-link link-fill p-3 ${interest.equals(PersonalInterest.NOTIFICATION) ? "left-highlight": ""}"
                               href="/thong-bao">Thông báo</a>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="col">
            <c:choose>
                <c:when test="${interest.equals(PersonalInterest.READING_LIST)}">
                    <%@include file="layout/reading_list.jsp" %>
                </c:when>
                <c:when test="${interest.equals(PersonalInterest.BOOKMARK)}">
                    <%@include file="layout/bookmark_list.jsp" %>
                </c:when>
                <c:when test="${interest.equals(PersonalInterest.NOTIFICATION)}">
                    <%@include file="layout/notification_list.jsp" %>
                </c:when>
            </c:choose>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/view/layout/boostrap_js.jsp" %>
</body>
</html>
