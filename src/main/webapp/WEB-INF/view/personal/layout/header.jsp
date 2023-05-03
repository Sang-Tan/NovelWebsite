<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 3/17/2023
  Time: 2:46 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar">
    <div class="container">
        <ul class="navbar__list">
            <li class="navbar__list-item">
                <a href="/" class="navbar__link navbar__brand">
                    <i class="fas fa-home"></i>
                </a>
            </li>
            <li class="navbar__list-item">
                <a href="/ca-nhan/them-truyen" class="navbar__link navbar__list-text">Thêm truyện</a>
            </li>
            <li class="navbar__list-item">
                <a href="/ca-nhan/truyen-da-dang" class="navbar__link navbar__list-text">Truyện đã đăng</a>
            </li>
            <li class="navbar__list-item">
                <a href="/ca-nhan/thong-tin" class="navbar__link navbar__list-text">Sửa thông tin cá nhân</a>
            </li>
            <li class="navbar__list-item">
                <a href="${pageContext.request.contextPath}/ca-nhan/doi-mat-khau"
                   class="navbar__link navbar__list-text">Đổi mật khẩu</a>
            </li>
        </ul>
    </div>
</nav>

