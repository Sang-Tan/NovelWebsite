<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<form action="" class="pt-3 pb-4 pl-4 pr-4 popup-form center-screen">
    <h3 class="text-center w-700">Đăng nhập</h3>
    <i class="fas fa-compress-arrows-alt top-right-btn" style="font-size: x-large"></i>
    <p class="text-center mb-4">Bạn chưa có tài khoản? <a href="#">Đăng ký</a></p>
    <div class="container mb-4">
        <div class="row mb-3">
            <label class="col-12 pl-0 mb-1" for="username">Tên đăng nhập</label>
            <input class="col input-text" type="text" name="username" id="username"
                   placeholder="Nhập tên đăng nhập">
        </div>
        <div class="row mb-2">
            <label class="col-12 pl-0 mb-1" for="password">Mật khẩu</label>
            <input class="col input-text" type="password" name="password" id="password" placeholder="Nhập mật khẩu">
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