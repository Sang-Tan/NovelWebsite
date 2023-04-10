<%--@elvariable id="statusMap" type="java.util.HashMap<String,String>"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="novel" type="model.Novel"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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
                                                       href="/doc-tieu-thuyet/${volume.id}-${volume.name.replace(" ", "-")}/${chapter.id}-${chapter.name.replace(" ","-")}">${chapter.name}</a>
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
            <%@include file="layout/comment_section.jsp" %>
        </div>
    </div>
</div>

<!-- Bootstrap -->
<%@ include file="layout/boostrap_js.jsp" %>
</body>
</html>