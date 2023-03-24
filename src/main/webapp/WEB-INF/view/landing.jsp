<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <title>Landing page ss</title>
            <%@ include file="layout/basic_stylesheet.jsp" %>
        </head>

        <body>

            <jsp:include page="layout/header_main.jsp"></jsp:include>

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
                                    <a href="/testui/novel_detail" class="no-decor">
                                        <div class="thumb__wrapper">
                                            <div class="thumb__img-panel shadow a6-ratio">
                                                <div class="img-wrapper img-wrapper--shadow"
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
                                            <a href="#" class="theme-link w-500">Chuyển sinh đến dị giới, mọi người gọi
                                                tôi là
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
            <%@ include file="layout/boostrap_js.jsp" %>

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