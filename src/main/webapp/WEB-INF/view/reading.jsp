<%--@elvariable id="reqNovel" type="model.Novel"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="reqChapter" type="model.Chapter"--%>
<%--@elvariable id="previousChapter" type="model.Chapter"--%>
<%--@elvariable id="nextChapter" type="model.Chapter"--%>
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
    <title>Chapter name</title>
    <%@ include file="layout/basic_stylesheet.jsp" %>
    <link rel="stylesheet" type="text/css" href="/css/reading.css">
</head>

<body style="background-color: var(--silver);">

<jsp:include page="layout/header_main.jsp"></jsp:include>
<section id="side_icon" class="d-flex flex-column align-items-center none force-block-l">
    <c:choose>
        <c:when test="${previousChapter == null}">
            <a class="button_item disabled" href="#">
                <i class="fas fa-backward"></i>
            </a>
        </c:when>
        <c:otherwise>
            <a class="button_item"
               href="/doc-tieu-thuyet/${reqNovel.id}-${URLSlugification.sluging(reqNovel.name)}/${previousChapter.id}-${URLSlugification.sluging(previousChapter.name)}">
                <i class="fas fa-backward"></i>
            </a>
        </c:otherwise>
    </c:choose>

    <a class="button_item" href="/truyen/${reqNovel.id}-${URLSlugification.sluging(reqNovel.name)}">
        <i class="fas fa-home"></i>
    </a>
    <%--@elvariable id="user" type="model.User"--%>

    <c:if test="${user != null}">
        <a id="add-bookmark" onclick="addBookMark()" data-chapter-id="${reqChapter.id}"
           class="button_item" ${isBookMarkYet ? "hidden" : ""}>
            <i class="fas fa-bookmark"></i>
        </a>
        <a id="delete-bookmark" onclick="deleteBookmark()" data-chapter-id="${reqChapter.id}"
           class="button_item" ${isBookMarkYet ? "" : "hidden"}>
            <i class="far fa-bookmark"></i>
        </a>
    </c:if>
    <a class="button_item" onclick="switchChaptersMode()"><i class="fas fa-info"></i></a>
    <c:choose>
        <c:when test="${nextChapter == null}">
            <a class="disabled button_item">
                <i class="fas fa-forward"></i>
            </a>
        </c:when>
        <c:otherwise>
            <a class="button_item"
               href="/doc-tieu-thuyet/${reqNovel.id}-${URLSlugification.sluging(reqNovel.name)}/${nextChapter.id}-${URLSlugification.sluging(nextChapter.name)}">
                <i class="fas fa-forward"></i>
            </a>
        </c:otherwise>
    </c:choose>
</section>
<section class="sidebar rdtoggle" id="chapters" aria-labelledby="...">
    <main class="rdtoggle_body">
        <header class="rd_sidebar-header clear">
            <a class="img" href="/truyen/${reqNovel.id}-${URLSlugification.sluging(reqNovel.name)}"
               style="background: url('${reqNovel.image}') no-repeat"></a>
            <div class="rd_sidebar-name">
                <h5>
                    <a href="/truyen/${reqNovel.id}-${URLSlugification.sluging(reqNovel.name)}">${StringUtils.truncate(reqNovel.name, 35)}</a>
                </h5>
                <small><i class="fas fa-pen"></i>${StringUtils.truncate(reqNovel.authorName, 15)}</small>
            </div>
        </header>
        <ul id="chap_list" class="unstyled">
            <c:forEach items="${reqNovel.volumes}" var="volume">
                <c:if test="${VolumeService.getFirstApprovedChapter(volume.id) != null}">
                    <c:choose>
                        <c:when test="${volume.id == reqChapter.volumeId}">
                            <li class="current"><a
                                    href="/doc-tieu-thuyet/${reqNovel.id}-${URLSlugification.sluging(reqNovel.name)}/${VolumeService.getFirstApprovedChapter(volume.id).id}-${URLSlugification.sluging(VolumeService.getFirstApprovedChapter(volume.id).name)}">${StringUtils.truncate(volume.name, 120)}</a>
                            </li>
                            <ul class="sub-chap_list unstyled">
                                <c:forEach items="${volume.chapters}" var="chapter">
                                    <c:if test="${chapter.approvalStatus.equals(Chapter.APPROVE_STATUS_APPROVED)}">
                                        <c:choose>
                                            <c:when test="${chapter.id == reqChapter.id}">
                                                <li class="current">
                                                    <a href="/doc-tieu-thuyet/${reqNovel.id}-${URLSlugification.sluging(reqNovel.name)}/${chapter.id}-${URLSlugification.sluging(chapter.name)}">
                                                            ${StringUtils.truncate(chapter.name, 50)}
                                                    </a>
                                                </li>
                                            </c:when>
                                            <c:otherwise>
                                                <li class="">
                                                    <a href="/doc-tieu-thuyet/${reqNovel.id}-${URLSlugification.sluging(reqNovel.name)}/${chapter.id}-${URLSlugification.sluging(chapter.name)}">
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
                            <li class=""><a
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
        <main class="chapter-content">
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
               onclick="switchChaptersMode()">Danh
                sách</a>
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
        function switchChaptersMode() {
            var chapters = document.querySelector("#chapters");
            if (chapters.classList.contains("on")) {
                chapters.classList.remove("on");
            } else {
                chapters.classList.add("on");
            }
        }
    </script>

    <%@ include file="layout/basic_js.jsp" %>
    <script src="/js/bookmark_manage.js"></script>

</body>


</html>