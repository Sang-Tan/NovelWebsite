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
            <li class="navbar__list-item">
                <a href="#login-modal" class="navbar__link navbar__list-text" data-toggle="modal"
                   data-target="#login-modal">Đăng
                    nhập</a>
            </li>
            <!--
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
            -->
        </ul>
    </div>
</nav>

<!--login modal-->
<div class="modal " tabindex="-1" role="dialog" id="login-modal" aria-labelledby="...">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <form action="" class="pt-3 pb-4 pl-4 pr-4 modal-content rounded-form">
            <h3 class="text-center w-700">Đăng nhập</h3>
            <i class="fas fa-compress-arrows-alt top-right-btn" data-dismiss="modal" aria-label="Close"
               style="font-size: x-large"></i>
            <p class="text-center mb-4">Bạn chưa có tài khoản?
                <a href="#register-modal" data-toggle="modal" data-dismiss="modal" data-target="#register-modal">
                    Đăng ký
                </a>
            </p>
            <div class="container mb-4">
                <div class="row mb-3">
                    <label class="col-12 pl-0 mb-1" for="login-username">Tên đăng nhập</label>
                    <input class="col input-text" type="text" name="username" id="login-username"
                           placeholder="Nhập tên đăng nhập">
                </div>
                <div class="row mb-2">
                    <label class="col-12 pl-0 mb-1" for="login-password">Mật khẩu</label>
                    <input class="col input-text" type="password" name="password" id="login-password"
                           placeholder="Nhập mật khẩu">
                </div>
                <div class="d-flex justify-content-center align-items-center">
                    <input type="checkbox" id="remember" name="remember">
                    <label class="w-500 mb-0 ml-3" for="remember">Ghi nhớ đăng nhập</label>
                </div>
            </div>
            <div class="d-flex justify-content-center mb-2">
                <button type="submit" class="pl-3 pr-3 basic-btn basic-btn--olive">Đăng nhập</button>
            </div>
            <div class="d-flex justify-content-center">
                <a href="#" class="text-center w-600">Quên mật khẩu?</a>
            </div>
        </form>
    </div>
</div>

<!-- register modal -->
<div class="modal" tabindex="-1" role="dialog" id="register-modal" aria-labelledby="...">
    <div class="modal-dialog modal-dialog-centered" role="document">

        <form action="" class="pt-3 pb-4 pl-4 pr-4 modal-content rounded-form">
            <h3 class="text-center w-700">Đăng ký</h3>
            <i class="fas fa-compress-arrows-alt top-right-btn" data-dismiss="modal" style="font-size:x-large;"></i>
            <p class="text-center mb-4">Đã có tài khoản?
                <a href="#login-modal" data-toggle="modal" data-dismiss="modal" data-target="#login-modal">
                    Đăng nhập
                </a>
            </p>
            <div class="container mb-4">
                <div class="row mb-2">
                    <label class="col-12 pl-0 mb-1" for="regis-username">Tên đăng nhập</label>
                    <input class="col input-text" type="text" name="username" id="regis-username"
                           placeholder="Nhập tên đăng nhập">
                </div>
                <div class="row mb-2">
                    <label class="col-12 pl-0 mb-1" for="regis-password">Mật khẩu</label>
                    <input class="col input-text" type="password" name="password" id="regis-password"
                           placeholder="Nhập mật khẩu">
                </div>
                <div class="row mb-2">
                    <label class="col-12 pl-0 mb-1" for="confirm_password">Nhập lại mật khẩu</label>
                    <input class="col input-text" type="password" name="confirm_password" id="confirm_password"
                           placeholder="Nhập lại mật khẩu">
                </div>
            </div>
            <div class="d-flex justify-content-center mb-2">
                <button type="submit" class="pl-3 pr-3 basic-btn basic-btn--olive">Đăng ký</button>
            </div>
        </form>
    </div>
</div>
