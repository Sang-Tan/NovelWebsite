<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="latestUpdateNovels" type="java.util.List<model.Novel>"--%>
<%--@elvariable id="topViewRecentWeekNovels" type="java.util.List<model.Novel>"--%>
<%--@elvariable id="topViewRecentMonthNovels" type="java.util.List<model.Novel>"--%>
<%--@elvariable id="topViewRecentDayNovels" type="java.util.List<model.Novel>"--%>
<%--@elvariable id="latestUpdateChapters" type="java.util.Map<java.lang.Integer,model.Chapter>"--%>
<%@page import="service.URLSlugification" %>
<%--@elvariable id="URLSlugification" type="service.URLSlugification"--%>
<%@page import="core.StringUtils" %>
<%--@elvariable id="StringUtils" type="core.StringUtils"--%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Trang chủ</title>
    <%@ include file="layout/basic_stylesheet.jsp" %>
    <link rel="icon" href="/images/favicon.ico" type="image/x-icon"/>
</head>

<body>

<jsp:include page="layout/header_main.jsp"></jsp:include>

<!--main-->
<main class="mt-2">
    <div class="container">
        <div class="row">
            <div class="col-12 col-lg-9">
                <header class="mb-3">
                    <span class="title title--underline title--bold">Mới cập nhật</span>
                </header>
                <div class="row">

                    <c:forEach items="${latestUpdateNovels}" var="novel">
                        <div class="col-4 col-xl-3 thumb">
                            <div>
                                <div class="thumb__wrapper">
                                    <div class="thumb__img-panel shadow a6-ratio">
                                        <div class="img-wrapper img-wrapper--shadow"
                                             style="background-image: url('${novel.image}');">
                                        </div>
                                        <div class="thumb__detail">
                                            <div class="overflow-elipsis" style="width: 100%;">
                                                <c:set var="latestChapter"
                                                       value="${latestUpdateChapters.get(novel.id)}"/>
                                                    <%-- elvariable id="latestChapter" type="model.Chapter"--%>
                                                <a href="/doc-tieu-thuyet/${novel.id}/${latestChapter.id}"
                                                   class="thumb__detail-link">${latestChapter.name}</a>
                                            </div>
                                        </div>
                                    </div>
                                    <a href="/truyen/${novel.id}-${URLSlugification.sluging(novel.name)}"
                                       class="no-decor">
                                        <p class="thumb__caption">${StringUtils.truncate(novel.name, 30)}</p>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <div class="col col-lg-3">
                <header class="title-section mb-2">
                    <span class="title title--underline title--bold">Nổi bật</span>
                </header>
                <div class="tab-section">
                    <ul class="tab-list">
                        <li class="tab active">Top ngày</li>
                        <li class="tab">Top tuần</li>
                        <li class="tab ">Top tháng</li>

                    </ul>
                    <div class="tab-panels">
                        <ul class="p-0 tab-panel active">
                            <c:set var="index" value="1"/>
                            <c:forEach items="${topViewRecentDayNovels}" var="novel">
                                <li class="ranking__item">
                                    <span class="ranking__bullet">${index}</span>
                                    <a href="/truyen/${novel.id}-${URLSlugification.sluging(novel.name)}"
                                       class="theme-link w-500">${novel.name}</a>
                                </li>
                                <c:set var="index" value="${index + 1}"/>
                            </c:forEach>
                        </ul>
                        <ul class="p-0 tab-panel">
                            <c:set var="index" value="1"/>
                            <c:forEach items="${topViewRecentWeekNovels}" var="novel">
                                <li class="ranking__item">
                                    <span class="ranking__bullet">${index}</span>
                                    <a href="/truyen/${novel.id}-${URLSlugification.sluging(novel.name)}"
                                       class="theme-link w-500">${novel.name}</a>
                                </li>
                                <c:set var="index" value="${index + 1}"/>
                            </c:forEach>
                        </ul>
                        <ul class="p-0 tab-panel">
                            <c:set var="index" value="1"/>
                            <c:forEach items="${topViewRecentMonthNovels}" var="novel">
                                <li class="ranking__item">
                                    <span class="ranking__bullet">${index}</span>
                                    <a href="/truyen/${novel.id}-${URLSlugification.sluging(novel.name)}"
                                       class="theme-link w-500">${novel.name}</a>
                                </li>
                                <c:set var="index" value="${index + 1}"/>
                            </c:forEach>
                        </ul>

                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
<!-- Bootstrap -->
<%@ include file="layout/basic_js.jsp" %>

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