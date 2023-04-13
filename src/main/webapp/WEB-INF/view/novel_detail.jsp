<%--@elvariable id="statusMap" type="java.util.HashMap<String,String>"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="novel" type="model.Novel"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="core.StringCoverter" %>
<%--@elvariable id="StringCoverter" type="core.StringCoverter.class"--%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Novel</title>

    <%@ include file="layout/basic_stylesheet.jsp" %>
    <link rel="stylesheet" href="/css/novel_detail.css">
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
                                        <a href="${searchNovelUri}?genres=${genre.id}"
                                           class="genre-link">${genre.name}</a>
                                    </div>
                                </c:forEach>
                            </div>
                            <div class="flex-column">
                                <p class="mb-1"><b>Tác giả:</b> ${novel.authorName}</p>
                                <c:set var="status" value="${novel.status}"/>
                                <p class="mb-1"><b>Tình trạng:</b> ${statusMap.get(status)}</p>
                                <p class="mb-1"><b>Lượt xem:</b> 100.000</p>
                                <button class="basic-btn basic-btn--green">Theo dõi</button>
                                <button class="basic-btn basic-btn--red">Bỏ theo dõi</button>
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
                                        <c:if test="${chapter.approvalStatus.equals('approved')}">
                                            <li class="chapters__item">
                                                <div class="chapters__title">
                                                    <a class="chapters__link"
                                                       href="/doc-tieu-thuyet/${novel.id}-${StringCoverter.removeAccent(novel.name.replace(" ", "-"))}/${chapter.id}-${StringCoverter.removeAccent(chapter.name.replace(" ","-"))}">${chapter.name}</a>
                                                        <%--                                            <a class="chapters__link" href="">Chap 1--%>
                                                        <%--                                                overflow test overflow test overflow test overflow test</a>--%>
                                                </div>
                                                <span class="chapters__time">${chapter.modifyTime}</span>
                                            </li>
                                        </c:if>
                                    </c:forEach>
                                </ul>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </section>
            <section class="basic-section">
                <header class="basic-section__header">
                    <h5>Bình luận</h5>
                </header>
                <div class="container-fluid">
                    <c:choose>
                        <c:when test="${user == null}">
                            <p>Bạn phải <a href="#login-modal" class="navbar__link navbar__list-text"
                                           data-toggle="modal"
                                           data-target="#login-modal">Đăng nhập</a> hoặc <a href="#register-modal"
                                                                                            class="navbar__link navbar__list-text"
                                                                                            data-toggle="modal"
                                                                                            data-target="#register-modal">Tạo
                                tài khoản</a> để có thể bình
                                luận</p>

                        </c:when>

                        <c:otherwise>
                            <form action="novel_detail.jsp" method="post" class="mb-3">
                                <textarea name="cmtText" title="cmt" id="" rows="10" class="cmt-box"
                                          style="min-height: 100px;resize: none;scroll-behavior: auto"></textarea>
                                <div class="d-flex justify-content-end">
                                    <button type="submit" class="basic-btn basic-btn--green"
                                            style="width: 100px">Gửi
                                    </button>
                                </div>
                            </form>
                        </c:otherwise>
                    </c:choose>
                    <div class="cmt-group">
                        <%--@elvariable id="comments" type="java.util.List<model.Comment>"--%>
                        <div class="cmt-group__item">
                            <div class="cmt-group__avatar"
                                 style="background-image: url(https://a.ppy.sh/10969666_1614400439.jpeg);">
                            </div>
                            <div class="cmt-detail">
                                <a href="" class="cmt-detail__name">Hu Tao</a>
                                <p>Tin chuẩn chưa a</p>
                                <div class="cmt-toolkit">
                                    <div class="cmt-toolkit__item">
                                        <a href="" class="cmt-toolkit__link">Trả lời</a>
                                    </div>
                                    <div class="cmt-toolkit__item">
                                        <a href="" class="cmt-toolkit__link">Báo cáo</a>
                                    </div>
                                    <div class="cmt-toolkit__item">
                                        <a href="" class="cmt-toolkit__link">
                                            <i class="fas fa-thumbs-up"></i>
                                            <span>Thích</span>
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="cmt-group__item">
                            <div class="cmt-group__avatar"
                                 style="background-image: url('https://statics.voz.tech/data/avatars/m/1824/1824025.jpg?1659768273');">
                            </div>
                            <div class="cmt-detail">
                                <a href="" class="cmt-detail__name">BLV Trương Anh Ngọc</a>
                                <p>Chuẩn e nhé</p>
                                <div class="cmt-toolkit">
                                    <div class="cmt-toolkit__item">
                                        <a href="" class="cmt-toolkit__link">Trả lời</a>
                                    </div>
                                    <div class="cmt-toolkit__item">
                                        <a href="" class="cmt-toolkit__link">Báo cáo</a>
                                    </div>
                                    <div class="cmt-toolkit__item">
                                        <a href="" class="cmt-toolkit__link">
                                            <i class="fas fa-thumbs-up"></i>
                                            <span>Thích</span>
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="cmt-group__item">
                            <div class="cmt-group__avatar"
                                 style="background-image: url(https://cdn.shopify.com/s/files/1/0508/7775/9669/products/O1CN01zM7mRH1v7IcxM1vso__2208460416125.jpg?v=1639325196);">
                            </div>
                            <div class="cmt-detail">
                                <a href="" class="cmt-detail__name">Boo Tao</a>
                                <p>:))))))))</p>
                                <p>:))))))))</p>
                                <p>:))))))))</p>
                                <p>:))))))))</p>
                                <p>:))))))))</p>
                                <div class="cmt-toolkit">
                                    <div class="cmt-toolkit__item">
                                        <a href="" class="cmt-toolkit__link">Trả lời</a>
                                    </div>
                                    <div class="cmt-toolkit__item">
                                        <a href="" class="cmt-toolkit__link">Báo cáo</a>
                                    </div>
                                    <div class="cmt-toolkit__item">
                                        <a href="" class="cmt-toolkit__link">
                                            <i class="fas fa-thumbs-up"></i>
                                            <span>Thích</span>
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    </div>
</div>

<!-- Bootstrap -->
<%@ include file="layout/boostrap_js.jsp" %>
</body>
</html>