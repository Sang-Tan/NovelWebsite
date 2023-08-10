<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="pageItems" type="java.util.List<core.pagination.PageItem>"--%>
<%--@elvariable id="selectedUsers" type="java.util.List<model.User>"--%>

<%--@elvariable id="User" type="model.User.class"--%>
<%@ page import="model.User" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@ include file="/WEB-INF/view/layout/basic_stylesheet.jsp" %>
    <link rel="stylesheet" href="/css/search_user.css">
    <link rel="icon" href="/images/favicon.ico" type="image/x-icon"/>
</head>
<body style="background-color: var(--silver)">
<%@ include file="/WEB-INF/view/layout/header_admin.jsp" %>
<div class="container mt-4">
    <form method="get" action="/admin/tim-thanh-vien" class="container-fluid basic-section p-3" id="search-form">
        <header class="mb-3">
            <h1 class="text-center">Tìm kiếm thành viên</h1>
        </header>
        <div class="row mb-3">
            <div class="d-flex align-items-center col-6">
                <label for="search-text" class="label">Username: &nbsp;</label>
                <input class="input-text" style="flex-grow: 1;" type="text" name="search-text" id="search-text"
                       placeholder="username" value="${param.get("search-text")}">
            </div>
            <div class="d-flex align-items-center col-6">
                <label for="search-text" class="label">Role: &nbsp;</label>
                <select class="input-text" style="flex-grow: 1;" name="role" id="role">
                    <option value="all" ${param.get("role").equals("all")?"selected":""}>All</option>
                    <option value="admin" ${param.get("role").equals("admin")?"selected":""}>Admin</option>
                    <option value="member" ${param.get("role").equals("member")?"selected":""}>Member</option>
                    <option value="mod" ${param.get("role").equals("mod")?"selected":""}>Moderator</option>
                </select>
            </div>
        </div>
        <div class="d-flex justify-content-end">
            <button class="basic-btn basic-btn--olive" type="submit">Tìm kiếm</button>
        </div>
    </form>
    <table class="table table-bordered table-light mt-3 mb-3">
        <thead>
        <tr>
            <th class="table-col-header">Thành viên</th>
            <th class="table-col-header">Username</th>
            <th class="table-col-header">Role</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="selectedUser" items="${selectedUsers}">
            <tr>
                <td>
                    <a href="/thanh-vien/${selectedUser.id}" class="d-flex align-items-center">
                        <div class="square-ratio" style="width: 2rem">
                            <div class="img-wrapper border haft-rounded"
                                 style="background-image: url('${selectedUser.avatar}');">
                            </div>
                        </div>
                        <span class="ml-2">${selectedUser.displayName}</span>
                    </a>
                </td>
                <td>${selectedUser.username}</td>
                <td>
                    <c:choose>
                        <c:when test="${selectedUser.role.equals(User.ROLE_MEMBER)}">
                            <div class="role-box role-box--member">Member</div>
                        </c:when>
                        <c:when test="${selectedUser.role.equals(User.ROLE_MODERATOR)}">
                            <div class="role-box role-box--moderator">Moderator</div>
                        </c:when>
                        <c:when test="${selectedUser.role.equals(User.ROLE_ADMIN)}">
                            <div class="role-box role-box--admin">Admin</div>
                        </c:when>
                    </c:choose>
                </td>
            </tr>

        </c:forEach>
        </tbody>
    </table>
    <%@include file="/WEB-INF/view/layout/basic_js.jsp" %>
    <%@include file="/WEB-INF/view/layout/pagination_footer.jsp" %>
</div>
</body>
</html>
