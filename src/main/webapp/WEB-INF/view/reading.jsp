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
<div class="container pt-3">
    <h2 class="text-center">${reqChapter.belongVolume.name}</h2>
    <h4 class="text-center">${reqChapter.name}</h4>
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
        <a href="#list-novel-modal" class="basic-btn basic-btn--olive" style="min-width: 25%;" data-toggle="modal"
           data-target="#list-novel-modal">Danh sách</a>
        <c:choose>
            <c:when test="${nextChapter == null}">
                <a href="#" class="basic-btn disabled basic-btn--olive" style="min-width: 25%;">
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
    <%@include file="layout/comment_section.jsp" %>
</div>
<%--TODO:delete login modal--%>
<!--login modal-->
<div class="modal" tabindex="-1" id="list-novel-modal" aria-labelledby="...">
    <div class="modal-dialog modal-dialog-centered">
        <div class="pt-3 pb-4 pl-4 pr-4 modal-content">
            <i class="fas fa-compress-arrows-alt top-right-btn" data-dismiss="modal" aria-label="Close"
               style="font-size: x-large"></i>
            <header class="rd_sidebar-header clear">
                <a class="img" href="/truyen/${reqNovel.id}-${URLSlugification.sluging(reqNovel.name)}"
                   style="background: url('${reqNovel.image}') no-repeat"></a>
                <div class="rd_sidebar-name">
                    <h5>
                        <a href="/truyen/${reqNovel.id}-${URLSlugification.sluging(reqNovel.name)}">${StringUtils.truncate(reqNovel.name, 50)}</a>
                    </h5>
                    <small><i class="fas fa-pen"></i>${reqNovel.authorName}</small>
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
                                    <c:forEach items="${volume.chapters}" var="reqChapter">
                                        <c:if test="${reqChapter.approvalStatus.equals(Chapter.APPROVE_STATUS_APPROVED)}">
                                            <c:choose>
                                                <c:when test="${reqChapter.id == reqChapter.id}">
                                                    <li class="current">
                                                        <a href="/doc-tieu-thuyet/${reqNovel.id}-${URLSlugification.sluging(reqNovel.name)}/${reqChapter.id}-${URLSlugification.sluging(reqChapter.name)}">
                                                                ${StringUtils.truncate(reqChapter.name, 120)}
                                                        </a>
                                                    </li>
                                                </c:when>
                                                <c:otherwise>
                                                    <li class="">
                                                        <a href="/doc-tieu-thuyet/${reqNovel.id}-${URLSlugification.sluging(reqNovel.name)}/${reqChapter.id}-${URLSlugification.sluging(reqChapter.name)}">
                                                                ${StringUtils.truncate(reqChapter.name, 120)}
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
                                        href="/doc-tieu-thuyet/${reqNovel.id}-${URLSlugification.sluging(reqNovel.name)}/${volume.chapters[0].id}-${URLSlugification.sluging(volume.chapters[0].name)}">${StringUtils.truncate(volume.name, 120)}</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </c:forEach>
            </ul>
        </div>
    </div>
</div>

<%--Boostrap script--%>
<%@ include file="layout/boostrap_js.jsp" %>
</body>
</html>