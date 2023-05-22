<%--@elvariable id="isFavourite" type="boolean"--%>
<%--@elvariable id="user" type="model.User"--%>
<%--@elvariable id="statusMap" type="java.util.HashMap<String,String>"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="novel" type="model.Novel"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--@elvariable id="StringUtils" type="core.StringUtils.class"--%>
<%@ page import="core.StringUtils" %>

<%--@elvariable id="URLSlugification" type="service.URLSlugification.class"--%>
<%@ page import="service.URLSlugification" %>

<%--@elvariable id="TimeConverter" type="core.string_process.TimeConverter.class"--%>
<%@ page import="core.string_process.TimeConverter" %>

<%--@elvariable id="user" type="model.User"--%>
<%@page import="model.User" %>

<%--@elvariable id="Volume" type="model.Volume.class"--%>
<%@page import="model.Volume" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>${novel.name}</title>

    <%@ include file="layout/basic_stylesheet.jsp" %>
    <link rel="stylesheet" href="/css/novel_detail.css">
    <link rel="icon" href="/images/favicon.ico" type="image/x-icon"/>
</head>

<body style="background-color: var(--silver);">
<jsp:include page="layout/header_main.jsp"></jsp:include>
<div style="height: 30px"></div>
<div class="container">
    <div class="row">
        <div class="col-12 col-lg-9">
            <section class="basic-section pt-3">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-12 col-md-3 d-flex">
                            <div class="series-cover">
                                <div class="a6-ratio">
                                    <div class="img-wrapper border"
                                         style="background-image: url('${novel.image}');">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col">
                            <h1 class="text-center user-select-none mb-3">${novel.name}</h1>
                            <div class="d-flex mb-3">
                                <%--@elvariable id="genres" type="java.util.List<model.Genre>"--%>
                                <c:forEach items="${genres}" var="genre">
                                    <div class="genre-item">
                                        <a href="/tim-kiem-truyen?genres=${genre.id}"
                                           class="genre-link">${genre.name}</a>
                                    </div>
                                </c:forEach>
                            </div>
                            <div class="flex-column">
                                <p class="mb-1"><b>Tác giả:</b> ${novel.authorName}</p>
                                <c:set var="status" value="${novel.status}"/>
                                <p class="mb-1"><b>Tình trạng:</b> ${statusMap.get(status)}</p>
                                <p class="mb-1"><b>Lượt xem:</b> ${novel.viewCount}</p>
                                <c:if test="${user != null}">
                                    <button data-action="unfollow" data-id="${novel.id}"
                                            class="basic-btn basic-btn--red ${isFavourite ? "" : "hidden"}">
                                        <i class="fas fa-times"></i>
                                        Bỏ theo dõi
                                    </button>
                                    <button data-action="follow" data-id="${novel.id}"
                                            class="basic-btn basic-btn--green ${isFavourite ? "hidden" : ""}">
                                        <i class="fas fa-heart"></i>
                                        Theo dõi
                                    </button>
                                </c:if><p></p>
                                <a href="" class="no-decor" data-toggle="modal" data-target="#reportNovelModal"
                                   onclick="showReportNovelForm(${novel.id}, ${user.id})">
                                    <i class="far fa-flag"></i>
                                    Báo cáo
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
                <hr/>
                <div class="container-fluid">
                    <h5>Tóm tắt</h5>
                    <p>${novel.summary}</p>
                </div>
            </section>
            <section class="basic-section">
                <c:forEach items="${novel.volumes}" var="volume">
                    <c:if test="${volume.approvalStatus.equals(Volume.APPROVE_STATUS_APPROVED)}">
                        <div class="basic-section__header">
                            <h5>${volume.name}</h5>
                        </div>
                        <div class="container fluid">
                            <div class="row">
                                <div class="col-12 col-md-3 d-flex justify-content-center">
                                    <div class="a6-ratio volume-cover">
                                        <div class=" img-wrapper border"
                                             style="background-image: url('${volume.image}');">
                                        </div>
                                    </div>
                                </div>
                                <div class="col col-md-9">
                                    <ul class="chapters">
                                        <c:forEach items="${volume.chapters}" var="chapter">
                                            <li class="chapters__item">
                                                <div class="chapters__title">
                                                    <a class="chapters__link"
                                                       href="/doc-tieu-thuyet/${novel.id}-${URLSlugification.sluging(novel.name)}/${chapter.id}-${URLSlugification.sluging(chapter.name)}">${StringUtils.truncate(chapter.name, 100)}</a>
                                                </div>
                                                <span class="chapters__time">${TimeConverter.convertToddMMyyyy(chapter.updatedTime)}</span>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>
            </section>
            <%@include file="layout/comment_section.jsp" %>
        </div>
        <div class="col">
            <c:if test="${user.role.equals(User.ROLE_MODERATOR)}">
                <div class="d-flex justify-content-center">
                    <button class="basic-btn basic-btn--red" data-toggle="modal" data-target="#deleteNovelModal">
                        Xóa tiểu thuyết
                    </button>
                </div>
            </c:if>
        </div>
    </div>
</div>

<%--Delete novel modal--%>
<c:if test="${user.role.equals(User.ROLE_MODERATOR)}">
    <div class="modal fade" id="deleteNovelModal" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-dialog-scrollable">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title title title--bold">Xóa tiểu thuyết</h5>
                    <i class="fas fa-compress-arrows-alt top-right-btn" data-dismiss="modal" aria-label="Close"
                       style="font-size: x-large"></i>
                </div>
                <form action="/mod/xoa-tieu-thuyet" enctype="application/x-www-form-urlencoded"
                      method="post" id="deleteNovelForm" onsubmit="modDeleteNovel(this,event)">
                    <div class="modal-body">
                        <label for="deleteNovelReasonTxt">Lý do xóa</label>
                        <input type="text" id="deleteNovelReasonTxt" name="reason" class="col-12"
                               style="padding: 5px"/>
                    </div>

                    <div class="modal-footer justify-content-center">
                        <button type="button" class="basic-btn basic-btn--gray" data-dismiss="modal">
                            Đóng
                        </button>
                        <button type="submit" class="basic-btn basic-btn--olive">
                            OK
                        </button>
                        <input name="novel-id" type="hidden" value="${novel.id}">
                    </div>
                </form>
            </div>
        </div>
    </div>
</c:if>

<!--Novel report modal-->
<div class="modal fade" id="reportNovelModal" tabindex="-1" aria-labelledby="staticBackdropLabel2" aria-hidden="true">
    <div class="modal-dialog modal-dialog-scrollable">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title title title--bold"
                    id="staticBackdropLabel2">Báo cáo truyện</h5>
                <i class="fas fa-compress-arrows-alt top-right-btn" data-dismiss="modal" aria-label="Close"
                   style="font-size: x-large"></i>
            </div>
            <form action="/bao-cao-truyen?action=report_novel"
                  method="post" id="reportNovelForm">
                <div class="modal-body">
                    <b>Lý do</b>
                    <textarea id="reason1" name="reason" class="col-12" rows="5" style="padding: 5px"></textarea>
                </div>

                <div class="modal-footer justify-content-center">
                    <button type="button" class="basic-btn basic-btn--gray" data-dismiss="modal">
                        Đóng
                    </button>
                    <button type="button" class="basic-btn basic-btn--olive" data-toggle="modal"
                            data-target="#confirmModal1">
                        OK
                    </button>
                    <input name="novelId" hidden
                           id="novelId" type="text">
                    <input name="userId" hidden id="userId1"
                           type="text">
                </div>
            </form>
        </div>
    </div>
</div>
<!--Novel report modal 2-->
<div class="modal fade" id="confirmModal1" tabindex="-1" aria-labelledby="staticBackdropLabel2" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title title title--bold">
                    Bạn chắc chứ?
                </h5>
                <i class="fas fa-compress-arrows-alt top-right-btn" data-dismiss="modal" aria-label="Close"
                   style="font-size: x-large"></i>
            </div>
            <div class="modal-body text-center">
                <p>Bạn có muốn báo cáo truyện này không?</p>
            </div>

            <div class="modal-footer justify-content-center">
                <button type="button" class="basic-btn basic-btn--gray" data-dismiss="modal">
                    Đóng
                </button>
                <button type="button" class="basic-btn basic-btn--olive" id="send" onclick="submitNovelReport()"
                        data-dismiss="modal">
                    OK
                </button>
            </div>
        </div>
    </div>
</div>
<!-- Bootstrap -->
<%@ include file="layout/basic_js.jsp" %>

<script src="/js/personal_interest.js"></script>
<script src="/js/novel_report.js"></script>
<script src="/js/form.js"></script>
<script src="/js/novel_mod_deletion.js"></script>
</body>
</html>