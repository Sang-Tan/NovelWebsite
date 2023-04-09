<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="chapter" type="model.Chapter"--%>
<%--@elvariable id="previousChapter" type="model.Chapter"--%>
<%--@elvariable id="nextChapter" type="model.Chapter"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<<<<<<< Updated upstream--%>

<%--=======--%>
<%-->>>>>>> Stashed changes--%>
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
    <h2 class="text-center">${chapter.belongVolume.name}</h2>
    <h4 class="text-center">${chapter.name}</h4>
    <main class="reading-part">
        <p>
            ${chapter.content}
        </p>
    </main>
    <div class="d-flex justify-content-between mb-3">

        <c:choose>
            <c:when test="${previousChapter == null}">
                <a href="#" class="basic-btn disabled basic-btn--olive" style="min-width: 25%;">
                    Chương trước
                </a>
            </c:when>
            <c:otherwise>
                <a
                        href="/doc-tieu-thuyet/${novelUrl}/${previousChapter.id}-${previousChapter.name.replace(" ", "-")}"
                        class="basic-btn basic-btn--olive"
                        style="min-width: 25%;">
                    Chương trước
                </a>
            </c:otherwise>
        </c:choose>


<%--        <a--%>
<%--                href="/doc-tieu-thuyet/${novelUrl}/--%>
<%--            ${previousChapter.id}-${previousChapter.name.replace(" ", "-")}}" r class="basic-btn disabled basic-btn--olive"--%>
<%--                style="min-width: 25%;">--%>
<%--            Chương trước--%>
<%--        </a>--%>
<%--        <a class="basic-btn basic-btn--olive" style="min-width: 25%;" data-affect="#rd_sidebar.chapters">--%>
<%--            Danh sách--%>
<%--        </a>--%>
        <a href="#list-novel-modal" class="basic-btn basic-btn--olive" style="min-width: 25%;" data-toggle="modal"
           data-target="#list-novel-modal">Danh sách</a>
<%--        <a id="rd-info_icon" data-affect="#rd_sidebar.chapters" class="rd_sd-button_item"><i class="fas fa-info"></i></a>--%>
        <c:choose>
            <c:when test="${nextChapter == null}">
                <a href="#" class="basic-btn disabled basic-btn--olive" style="min-width: 25%;">
                    Chương Sau
                </a>

            </c:when>
            <c:otherwise>
                <a
                        href="/doc-tieu-thuyet/${novelUrl}/${nextChapter.id}-${nextChapter.name.replace(" ", "-")}"
                        class="basic-btn basic-btn--olive"
                        style="min-width: 25%;">
                    Chương trước
                </a>
            </c:otherwise>
        </c:choose>
    </div>
</div>


<%--Boostrap script--%>
<%@ include file="layout/boostrap_js.jsp" %>
</body>
</html>