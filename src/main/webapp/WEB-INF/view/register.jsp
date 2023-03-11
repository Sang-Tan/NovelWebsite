<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Register</title>
    <!-- normalize css -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/8.0.1/normalize.min.css"
          integrity="sha512-NhSC1YmyruXifcj/KFRWoC561YpHpc5Jtzgvbuzx5VozKpWvQ+4nXhPdFgmx8xqexRcpAglTj9sIBWINXa8x5w=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>

    <!-- bootstrap css -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

    <!-- Google Font -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;500;600;700&display=swap" rel="stylesheet">

    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css"
          integrity="sha512-iBBXm8fW90+nuLcSKlbmrPcLa0OT92xO1BIsZ+ywDWZCvqsWgccV3gFoRBv0z+8dLJgyAHIhR35VZc2oM/gI1w=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>

    <!-- Custom StyleSheet -->
    <!-- <link rel="stylesheet" href="./css/core.css"> -->
    <link rel="stylesheet" href="./css/main.css">
    <link rel="stylesheet" href="./css/form.css">

</head>

<body>
<form action="" class="pt-3 pb-4 pl-4 pr-4 popup-form center-screen">
    <h3 class="text-center w-700">Đăng ký</h3>
    <i class="fas fa-compress-arrows-alt top-right-btn" style="font-size:x-large;"></i>
    <p class="text-center mb-4">Đã có tài khoản? <a href="#">Đăng nhập</a></p>
    <div class="container mb-4">
        <div class="row mb-2">
            <label class="col-12 pl-0 mb-1" for="username">Tên đăng nhập</label>
            <input class="col input-text" type="text" name="username" id="username"
                   placeholder="Nhập tên đăng nhập">
        </div>
        <div class="row mb-2">
            <label class="col-12 pl-0 mb-1" for="password">Mật khẩu</label>
            <input class="col input-text" type="password" name="password" id="password" placeholder="Nhập mật khẩu">
        </div>
        <div class="row mb-2">
            <label class="col-12 pl-0 mb-1" for="confirm_password">Nhập lại mật khẩu</label>
            <input class="col input-text" type="password" name="confirm_password" id="confirm_password"
                   placeholder="Nhập lại mật khẩu">
        </div>
    </div>
    <div class="d-flex justify-content-center mb-2">
        <button type="submit" class="pl-3 pr-3 basic-btn basic-btn--olive">Đăng nhập</button>
    </div>
    <div class="d-flex justify-content-center">
        <a href="#" class="text-center w-600">Quên mật khẩu?</a>
    </div>
</form>
</body>

</html>