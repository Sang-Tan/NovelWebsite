<%@ page import="model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!--navigation bar-->
<nav class="navbar">
    <div class="container">
        <ul class="navbar__list">
            <li class="navbar__list-item">
                <a href="/home" class="navbar__link navbar__brand">
                    <div class="brand-image-wrapper" style="background-image: url('/images/logo.png')">

                    </div>
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
                <form action="/testui/search_novel" class="navbar__search m-auto">
                    <input type="text" placeholder="Tìm truyện" class="navbar__search-textbox">
                    <button class="navbar__search-btn">
                        <i class="fas fa-search"></i>
                    </button>
                </form>
            </li>
            <c:choose>
                <%--@elvariable id="user" type="model.User"--%>
                <c:when test="${user == null}">
                    <li class="navbar__list-item">
                        <a href="#login-modal" class="navbar__link navbar__list-text" data-toggle="modal"
                           data-target="#login-modal">Đăng
                            nhập</a>
                    </li>
                    <li class="navbar__list-item">
                        <a href="#register-modal" class="navbar__link navbar__list-text" data-toggle="modal"
                           data-target="#register-modal">Đăng
                            ký</a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="navbar__list-item dropdown">
                        <a href="#" class="navbar__link navbar__list-text ml-1 d-flex justify-content-center"
                           data-toggle="dropdown">
                            <img src="${user.avatar}"
                                 alt="avatar" class="navbar__avatar">
                            <p class="d-inline-block ml-2 mt-auto mb-auto">${user.displayName}</p>
                        </a>
                        <div class="dropdown-menu">
                            <a href="/theo-doi" class="dropdown-item">
                                <i class="fas fa-heart mr-1"></i><span>Truyện yêu thích</span>
                            </a>
                            <a href="/danh-dau" class="dropdown-item">
                                <i class="fas fa-bookmark mr-1"></i>Đánh dấu
                            </a>
                            <a href="/ca-nhan" class="dropdown-item">Cá nhân</a>
                            <a href="/logout" class="dropdown-item">Đăng xuất</a>
                        </div>
                    </li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</nav>

<!--login modal-->
<div class="modal" tabindex="-1" role="dialog" id="login-modal" aria-labelledby="...">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <form method="post" action="/login" onsubmit="event.preventDefault(); loginSubmit();"
              enctype="application/x-www-form-urlencoded" class="pt-3 pb-4 pl-4 pr-4 modal-content rounded-form">
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
                    <span class="error-text col-12 pl-0 hidden"></span>
                </div>

                <div class="row mb-2">
                    <label class="col-12 pl-0 mb-1" for="login-password">Mật khẩu</label>
                    <input class="col input-text" type="password" name="password" id="login-password"
                           placeholder="Nhập mật khẩu">
                    <span class="error-text col-12 pl-0 hidden"></span>
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

        <form action="/register" method="post" onsubmit="event.preventDefault(); registerSubmit();"
              class="pt-3 pb-4 pl-4 pr-4 modal-content rounded-form" enctype="application/x-www-form-urlencoded">
            <h3 class="text-center w-700">Đăng ký</h3>
            <i class="fas fa-compress-arrows-alt top-right-btn" data-dismiss="modal" style="font-size:x-large;"></i>
            <p class="text-center mb-4">Đã có tài khoản?
                <a href="#login-modal" data-toggle="modal" data-dismiss="modal" data-target="#login-modal">
                    Đăng nhập
                </a>
            </p>
            <div class="container mb-4">
                <div class="row mb-2 basic-tooltip">
                    <label class="col-12 pl-0 mb-1" for="regis-username">Tên đăng nhập</label>
                    <input class="col input-text" type="text" name="username" id="regis-username"
                           placeholder="Nhập tên đăng nhập">
                    <span class="error-text col-12 pl-0 hidden"></span>
                    <span class="tooltip-text">Tên đăng nhập gồm ít nhất nhất 6 kí tự là số, chữ hoa hoặc chữ thường</span>
                </div>
                <div class="row mb-2 basic-tooltip">
                    <label class="col-12 pl-0 mb-1" for="regis-password">Mật khẩu</label>
                    <input class="col input-text" type="password" name="password" id="regis-password"
                           placeholder="Nhập mật khẩu">
                    <span class="error-text col-12 pl-0 hidden"></span>
                    <span class="tooltip-text">Mật khẩu có ít nhất 6 kí tự bao gồm số, chữ hoa, chữ thường</span>
                </div>
                <div class="row mb-2">
                    <label class="col-12 pl-0 mb-1" for="confirm_password">Nhập lại mật khẩu</label>
                    <input class="col input-text" type="password" name="confirm_password" id="confirm_password"
                           placeholder="Nhập lại mật khẩu">
                    <span class="error-text col-12 pl-0 hidden"></span>
                </div>
            </div>
            <div class="d-flex justify-content-center mb-2">
                <button type="submit" class="pl-3 pr-3 basic-btn basic-btn--olive">Đăng ký</button>
            </div>
        </form>
    </div>
</div>

<script>
    function registerSubmit() {
        const form = event.target;
        const request = new XMLHttpRequest();
        request.open("POST", "/register");

        request.onload = () => {
            if (request.status === 404) {
                alert("Đăng ký thất bại");
                return;
            }

            const data = JSON.parse(request.responseText);

            if (data.status === "success") {
                alert("Đăng ký thành công");
                window.location.reload();
            } else {
                showFormError(form, data);
            }
        }
        const formData = new FormData(form);
        request.send(formData);
    }

    function loginSubmit() {
        const form = event.target;
        const request = new XMLHttpRequest();
        request.open("POST", "/login");

        request.onload = () => {
            if (request.status === 404) {
                alert("Đăng nhập thất bại");
                return;
            }

            const data = JSON.parse(request.responseText);
            if (data.status === "success") {
                alert("Đăng nhập thành công");
                window.location.reload();
            } else {
                showFormError(form, data);
            }
        }

        const formData = new FormData(form);
        request.send(formData);
    }

    function showFormError(form, errorData) {
        const errors = errorData.errors;
        Object.entries(errors).forEach(([key, value]) => {
            const invalidInput = form.elements[key];
            const errorText = getErrorTextElement(invalidInput);

            invalidInput.classList.add("error");
            showErrorText(errorText, value);

//add event listener to remove error class
            invalidInput.addEventListener("focus", () => {
                invalidInput.classList.remove("error");
                hideErrorText(errorText);
            });
        });
    }

    function getErrorTextElement(inputElement) {
        return inputElement.parentNode.querySelector(".error-text");
    }

    function showErrorText(errorTextElement, text) {
        if (errorTextElement) {
            errorTextElement.classList.remove("hidden");
            errorTextElement.innerText = text;
        }
    }

    function hideErrorText(errorTextElement) {
        if (errorTextElement) {
            errorTextElement.classList.add("hidden");
        }
    }
</script>