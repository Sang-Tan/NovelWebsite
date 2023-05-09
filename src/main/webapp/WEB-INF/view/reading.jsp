<%--@elvariable id="reqNovel" type="model.Novel"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="reqChapter" type="model.Chapter"--%>
<%--@elvariable id="previousChapter" type="model.Chapter"--%>
<%--@elvariable id="nextChapter" type="model.Chapter"--%>
<%--@elvariable id="isBookMarkYet" type="boolean"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="core.StringUtils" %>
<%--@elvariable id="StringUtils" type="core.StringUtils.class"--%>
<%@ page import="service.URLSlugification" %>
<%--@elvariable id="URLSlugification" type="service.URLSlugification.class"--%>
<%@ page import="model.Chapter" %>
<%--@elvariable id="Chapter" type="model.Chapter.class"--%>
<%@ page import="service.VolumeService" %>
<%--@elvariable id="VolumeService" type="service.VolumeService.class"--%>
<%@page import="core.string_process.HTMLParser" %>
<%--@elvariable id="HTMLParser" type="core.string_process.HTMLParser.class"--%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${reqChapter.name}</title>
    <link rel="icon" href="/images/favicon.ico" type="image/x-icon" />
    <%@ include file="layout/basic_stylesheet.jsp" %>
    <link rel="stylesheet" type="text/css" href="/css/reading.css">
</head>

<body style="background-color: var(--silver);">

<jsp:include page="layout/header_main.jsp"></jsp:include>
<section class="toolbar" id="toolbar">
    <div class="toolbar__slide-btn" onclick="toggleToolbar()">
        <div class="toolbar__slide-icon"><i class="fas fa-angle-left"></i></div>
    </div>
    <div class="d-flex flex-column justify-content-center align-items-center">
        <div class="toolbar__item-box">
            <c:choose>
                <c:when test="${previousChapter == null}">
                    <span class="toolbar__item disabled" >
                        <i class="fas fa-backward"></i>
                    </span>
                </c:when>
                <c:otherwise>
                    <a class="toolbar__item"
                       href="/doc-tieu-thuyet/${reqNovel.id}-${URLSlugification.sluging(reqNovel.name)}/${previousChapter.id}-${URLSlugification.sluging(previousChapter.name)}">
                        <i class="fas fa-backward"></i>
                    </a>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="toolbar__item-box">
            <a class="toolbar__item" href="/truyen/${reqNovel.id}-${URLSlugification.sluging(reqNovel.name)}">
                <i class="fas fa-home"></i>
            </a>
        </div>
        <%--@elvariable id="user" type="model.User"--%>

        <c:if test="${user != null}">
            <div id="add-bookmark" data-chapter-id="${reqChapter.id}"
                 class="toolbar__item-box" ${isBookMarkYet ? "hidden" : ""}>
                <a href="#" onclick="addBookMark()"
                   class="toolbar__item" }>
                    <i class="fas fa-bookmark"></i>
                </a>
            </div>
            <div id="delete-bookmark" data-chapter-id="${reqChapter.id}"
                 class="toolbar__item-box" ${isBookMarkYet ? "" : "hidden"}>
                <a href="#" onclick="deleteBookmark()"
                   class="toolbar__item">
                    <i class="far fa-bookmark"></i>
                </a>
            </div>
        </c:if>
        <div class="toolbar__item-box">
            <a class="toolbar__item" href="#" onclick="toggleNavSidebar()">
                <i class="fas fa-info"></i>
            </a>
        </div>
        <div class="toolbar__item-box">
            <c:choose>
                <c:when test="${nextChapter == null}">
                    <span class="disabled toolbar__item">
                        <i class="fas fa-forward"></i>
                    </span>
                </c:when>
                <c:otherwise>
                    <a class="toolbar__item"
                       href="/doc-tieu-thuyet/${reqNovel.id}-${URLSlugification.sluging(reqNovel.name)}/${nextChapter.id}-${URLSlugification.sluging(nextChapter.name)}">
                        <i class="fas fa-forward"></i>
                    </a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</section>
<section class="nav-sidebar" id="navigation-sidebar" aria-labelledby="...">
    <main>
        <header class="nav-sidebar__header fluid-container">
            <div class="row">
                <div class="col-4">
                    <div class="a6-ratio">
                        <a href="/truyen/${reqNovel.id}-${URLSlugification.sluging(reqNovel.name)}">
                            <div class="img-wrapper" style="background-image: url('${reqNovel.image}')"></div>
                        </a>
                    </div>
                </div>
                <div class="col pl-0">
                    <h5>
                        <a class="nav-sidebar__novel-name"
                           href="/truyen/${reqNovel.id}-${URLSlugification.sluging(reqNovel.name)}">${StringUtils.truncate(reqNovel.name, 35)}</a>
                    </h5>
                </div>
            </div>
        </header>
        <ul id="chap_list" class="nav-sidebar__list pl-0">
            <c:forEach items="${reqNovel.volumes}" var="volume">
                <c:set var="firstChapterId" value="${VolumeService.getFirstApprovedChapter(volume.id).id}"/>
                <c:if test="${firstChapterId != null}">
                    <c:choose>
                        <c:when test="${volume.id == reqChapter.volumeId}">
                            <li class="nav-sidebar__item current"><a
                                    data-chapter-id="${firstChapterId}"
                                    href="/doc-tieu-thuyet/${reqNovel.id}-${URLSlugification.sluging(reqNovel.name)}/${firstChapterId}-${URLSlugification.sluging(VolumeService.getFirstApprovedChapter(volume.id).name)}">${StringUtils.truncate(volume.name, 120)}</a>
                            </li>
                            <ul class="nav-sidebar__list">
                                <c:forEach items="${volume.chapters}" var="chapter">
                                    <c:if test="${chapter.approvalStatus.equals(Chapter.APPROVE_STATUS_APPROVED)}">
                                        <c:choose>
                                            <c:when test="${chapter.id == reqChapter.id}">
                                                <li class="nav-sidebar__item current">
                                                    <a data-chapter-id="${chapter.id}"
                                                            href="/doc-tieu-thuyet/${reqNovel.id}-${URLSlugification.sluging(reqNovel.name)}/${chapter.id}-${URLSlugification.sluging(chapter.name)}">
                                                            ${StringUtils.truncate(chapter.name, 50)}
                                                    </a>
                                                </li>
                                            </c:when>
                                            <c:otherwise>
                                                <li class="nav-sidebar__item">
                                                    <a data-chapter-id="${chapter.id}"
                                                            href="/doc-tieu-thuyet/${reqNovel.id}-${URLSlugification.sluging(reqNovel.name)}/${chapter.id}-${URLSlugification.sluging(chapter.name)}">
                                                            ${StringUtils.truncate(chapter.name, 50)}
                                                    </a>
                                                </li>
                                            </c:otherwise>

                                        </c:choose>
                                    </c:if>
                                </c:forEach>
                            </ul>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-sidebar__item"><a
                                    data-chapter-id="${firstChapterId}"
                                    href="/doc-tieu-thuyet/${reqNovel.id}-${URLSlugification.sluging(reqNovel.name)}/${volume.chapters[0].id}-${URLSlugification.sluging(volume.chapters[0].name)}">${StringUtils.truncate(volume.name, 50)}</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:if>
            </c:forEach>
        </ul>
    </main>
</section>
<div class="container pt-3">
    <div class="reading-content">
        <div class="title-top" style="padding-top: 20px">
            <h2 class="text-center">${reqChapter.belongVolume.name}</h2>
            <h4 class="text-center">${reqChapter.name}</h4>
        </div>
        <main class="reading-part">
            ${HTMLParser.wrapEachLineWithTag(reqChapter.content,"p")}
        </main>
        <div class="d-flex justify-content-between mb-3">
            <c:choose>
                <c:when test="${previousChapter == null}">
                    <a href="#" class="basic-btn disabled basic-btn--olive" style="min-width: 25%;">
                        Chương trước
                    </a>
                </c:when>
                <c:otherwise>
                    <a href="/doc-tieu-thuyet/${reqNovel.id}-${URLSlugification.sluging(reqNovel.name)}/${previousChapter.id}-${URLSlugification.sluging(previousChapter.name)}"
                       class="basic-btn basic-btn--olive"
                       style="min-width: 25%;">
                        Chương trước
                    </a>
                </c:otherwise>
            </c:choose>
            <a class="basic-btn basic-btn--olive" style="min-width: 25%; color: white"
               onclick="toggleNavSidebar()">Danh sách</a>
            <c:choose>
                <c:when test="${nextChapter == null}">
                    <a class="basic-btn disabled basic-btn--olive" style="min-width: 25%;">
                        Chương sau
                    </a>
                </c:when>
                <c:otherwise>
                    <a href="/doc-tieu-thuyet/${reqNovel.id}-${URLSlugification.sluging(reqNovel.name)}/${nextChapter.id}-${URLSlugification.sluging(nextChapter.name)}"
                       class="basic-btn basic-btn--olive"
                       style="min-width: 25%;">
                        Chương sau
                    </a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <script>
        function toggleNavSidebar() {
            const navSidebar = document.querySelector("#navigation-sidebar");
            navSidebar.classList.toggle("show");
        }

        function toggleToolbar() {
            const toolbar = document.getElementById("toolbar");
            toolbar.classList.toggle("show");
        }
    </script>
    <%@include file="layout/comment_section.jsp" %>
    <%@ include file="layout/basic_js.jsp" %>
    <script src="/js/bookmark_manage.js"></script>

</body>


</html>