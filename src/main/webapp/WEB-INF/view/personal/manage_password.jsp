<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="errorMessage" type="java.lang.String"--%>
<%--@elvariable id="successMessage" type="java.lang.String"--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Đổi mật khẩu</title>
    <link rel="icon" href="/images/favicon.ico" type="image/x-icon" />
    <%@include file="/WEB-INF/view/layout/basic_stylesheet.jsp" %>
</head>
<body style="background-color: var(--silver)">
<%@include file="layout/header.jsp" %>
<div class="container mt-5">
    <div class="basic-section">
        <div class="basic-section__header">
            <h3 class="basic-section__title">Đổi mật khẩu</h3>
        </div>
        <form method="post" class="containter-fluid ml-5 mr-5" enctype="application/x-www-form-urlencoded">
            <div class=" d-flex align-items-center mb-2">
                <label for="old-password" class="basic-label required">Mật khẩu cũ</label>
                <input class="input-text" style="flex-grow: 1" type="password" name="old-password" id="old-password"
                       required>
            </div>
            <div class=" d-flex align-items-center mb-2">
                <label for="new-password" class="basic-label required">Mật khẩu mới</label>
                <input class="input-text" style="flex-grow: 1" type="password" name="new-password" id="new-password"
                       required>
            </div>
            <div class="d-flex justify-content-center">
                <button type="submit" class="basic-btn basic-btn--olive">Xác nhận</button>
            </div>
        </form>
    </div>
</div>
<%@include file="/WEB-INF/view/layout/basic_js.jsp" %>
<c:choose>
    <c:when test="${not empty errorMessage}">
        <script>
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: '${errorMessage}',
            })
        </script>
    </c:when>
    <c:when test="${not empty successMessage}">
        <script>
            Swal.fire({
                icon: 'success',
                title: 'Thành công',
                text: '${successMessage}',
            })
        </script>
    </c:when>
</c:choose>
</body>
</html>
