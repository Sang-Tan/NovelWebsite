<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!--navigation bar-->
<nav class="navbar">
    <div class="container">
        <ul class="navbar__list">
            <li class="navbar__list-item">
                <a href="#" class="navbar__link navbar__brand">
                    NOVEL
                </a>
            </li>
            <li class="navbar__list-item">
                <a href="#" class="navbar__link navbar__list-text">Danh sách</a>
            </li>
            <li class="navbar__list-item">
                <a href="#" class="navbar__link navbar__list-text">Siuuuu</a>
            </li>
            <li class="navbar__list-item">
                <div style="display: inline-block">
                    <a href="#" class="navbar__link navbar__list-text">Giới thiệu</a>
                </div>

            </li>
        </ul>
        <ul class="navbar__list">
            <li class="navbar__list-item">
                <form action="/index.html" class="navbar__search">
                    <input type="text" placeholder="Tìm truyện" class="navbar__search-textbox">
                    <button class="navbar__search-btn">
                        <i class="fas fa-search"></i>
                    </button>
                </form>
            </li>
            <!-- <li class="navbar__list-item">
                <a href="#" class="navbar__link navbar__list-text">Đăng nhập</a>
            </li> -->
            <li class="navbar__list-item dropdown">
                <a href="#" class="navbar__link navbar__list-text ml-1 d-flex justify-content-center"
                   data-toggle="dropdown">
                    <img src="https://styles.redditmedia.com/t5_4dg0pd/styles/profileIcon_p8ofrlir7ik71.jpg?width=256&height=256&frame=1&auto=webp&crop=256:256,smart&s=d4d7dfe5bf6e1e9d88412ed2ca61df3270cba1c4"
                         alt="" class="navbar__avatar">
                    <p class="d-inline-block ml-2 mt-auto mb-auto">Username</p>
                </a>
                <div class="dropdown-menu">
                    <a href="#" class="dropdown-item">Thông tin cá nhân</a>
                    <a href="#" class="dropdown-item">Đăng xuất</a>

                </div>
            </li>
        </ul>
    </div>
</nav>