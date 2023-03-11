<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Landing page</title>
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
    <link rel="stylesheet" href="/template/css/core.css">
    <link rel="stylesheet" href="/template/css/main.css">

</head>

<body>
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

<!--main-->
<main class="mt-2">
    <div class="container">
        <div class="row">
            <div class="col-12 col-lg-9">
                <header class="mb-3">
                    <span class="title title--underline title--bold">Mới update</span>
                </header>
                <div class="row">
                    <div class="col-4 col-xl-3 thumb">
                        <a href="#" class="no-decor">
                            <div class="thumb__wrapper">
                                <div class="thumb__img-panel img-wrapper--shadow a6-ratio">
                                    <div class="img-wrapper"
                                         style="background-image: url('https://images.fpt.shop/unsafe/filters:quality(90)/fptshop.com.vn/uploads/images/tin-tuc/152650/Originals/Hu%20Tao.jpg');">
                                    </div>
                                </div>
                                <p class="thumb__caption">Hu Tao saves my life</p>
                            </div>
                        </a>
                    </div>
                </div>
                <nav class="mt-1">
                    <ul class="d-flex justify-content-end">
                        <li class="page-list__item">
                            <a href="#" class="page-list__link">Đầu</a>
                        </li>
                        <li class="page-list__item">
                            <a href="#" class="page-list__link">1</a>
                        </li>
                        <li class="page-list__item active">
                            <a href="#" class="page-list__link">2</a>
                        </li>
                        <li class="page-list__item">
                            <a href="#" class="page-list__link">3</a>
                        </li>
                        <li class="page-list__item">
                            <a href="#" class="page-list__link">4</a>
                        </li>
                        <li class="page-list__item">
                            <a href="#" class="page-list__link">5</a>
                        </li>
                        <li class="page-list__item">
                            <a href="#" class="page-list__link">Cuối</a>
                        </li>
                    </ul>
                </nav>
            </div>
            <div class="col col-lg-3">
                <header class="title-section mb-2">
                    <span class="title title--underline title--bold">Nổi bật</span>
                </header>
                <div class="tab-section">
                    <ul class="tab-list">
                        <li class="tab active">Top all</li>
                        <li class="tab ">Top tháng</li>
                        <li class="tab">Top tuần</li>
                    </ul>
                    <div class="tab-panels">
                        <ul class="p-0 tab-panel active">
                            <li class="ranking__item">
                                <span class="ranking__bullet">1</span>
                                <a href="#" class="theme-link w-500">Chuyển sinh đến dị giới, mọi người gọi tôi là
                                    CMMB nhưng tôi nhặt được thánh kiếm mạnh nhất </a>
                            </li>
                            <li class="ranking__item">
                                <span class="ranking__bullet">2</span>
                                <a href="#" class="theme-link w-500">Hình như thê tử của ta muốn được nuông
                                    chiều</a>
                            </li>
                            <li class="ranking__item">
                                <span class="ranking__bullet">3</span>
                                <a href="#" class="theme-link w-500">Ta phải chiến đấu chống lại quỷ tộc</a>
                            </li>
                            <li class="ranking__item">
                                <span class="ranking__bullet">4</span>
                                <a href="#" class="theme-link w-500">Thần Võ Đại Sư</a>
                            </li>
                        </ul>
                        <ul class="p-0 tab-panel">
                            <li class="ranking__item">
                                <span class="ranking__bullet">1</span>
                                <a href="#" class="theme-link w-500">Người vợ đoản mệnh của anh</a>
                            </li>
                            <li class="ranking__item">
                                <span class="ranking__bullet">2</span>
                                <a href="#" class="theme-link w-500">Chuyển sinh thành chó</a>
                            </li>
                            <li class="ranking__item">
                                <span class="ranking__bullet">3</span>
                                <a href="#" class="theme-link w-500">Chuyển sinh thành bồn cầu</a>
                            </li>
                            <li class="ranking__item">
                                <span class="ranking__bullet">4</span>
                                <a href="#" class="theme-link w-500">Chuyển sinh thành CMMB</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
<!-- Bootstrap -->
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
        integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js"
        integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js"
        integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
        crossorigin="anonymous"></script>
<script>
    const tabs = document.querySelectorAll('.tab');

    tabs.forEach((tab) => {
        tab.addEventListener('click', (e) => {
            const target = e.target;
            const targetIndex = Array.from(target.parentElement.children).indexOf(target);

            //select panel
            const targetSection = target.closest('.tab-section');
            if (!targetSection) {
                throw new Error(`${target} does not in class tab-section`);
            }
            const targetPanels = targetSection.querySelector('.tab-panels');
            const targetPanel = targetPanels.children[targetIndex];

            //deactive tabs
            const parent = target.closest('.tab-list');
            if (!parent) {
                throw new Error(`Parent of ${target} does not have class tab-list`);
            }

            const activeTabs = parent.querySelectorAll('.tab.active');
            activeTabs.forEach((tab, index) => {
                tab.classList.remove('active');
            });

            //deactive panels
            const activePanels = targetPanels.querySelectorAll('.tab-panel.active');
            activePanels.forEach((panel, index) => {
                panel.classList.remove('active');
            });
            target.classList.add('active');
            targetPanels.children[targetIndex].classList.add('active');
        });
    });
</script>
</body>


</html>