<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="model.Novel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="core.StringUtils" %>
<%--@elvariable id="StringUtils" type="core.StringUtils.class"--%>
<%@ page import="service.URLSlugification" %>
<%--@elvariable id="URLSlugification" type="service.URLSlugification.class"--%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Tìm kiếm</title>
    <%@ include file="layout/basic_stylesheet.jsp" %>
    <link rel="stylesheet" href="/css/search_novel.css">

</head>
<body style="background-color: var(--silver);">
<jsp:include page="layout/header_main.jsp"></jsp:include>
<div class="container mt-4">
    <form action="/tim-kiem-truyen" class="container-fluid basic-section p-3" id="search-form"
          onsubmit="event.preventDefault(); submitForm()">
        <div class="row">
            <div class="d-flex align-items-center col-10">
                <label for="novel" class="label">Tên truyện: </label>
                <input class="input-text" style="flex-grow: 1;" type="text" name="novel" id="novel"
                       placeholder="Tên truyện"
                       value="<%= request.getParameter("novel") != null ? request.getParameter("novel") : ""%>">
            </div>
            <div class="col d-flex">
                <button class="basic-btn basic-btn--olive" style="width: 100%;" type="submit">Tìm kiếm</button>
            </div>
        </div>
        <div class="row">
            <div class="col-12 col-lg-4">
                <div class="d-flex align-items-center mt-2">
                    <label for="author" class="label">Tác giả: </label>
                    <input class="input-text" style="flex-grow: 1" type="text" name="author" id="author"
                           placeholder="Tên tác giả"
                           value="<%= request.getParameter("author") != null ? request.getParameter("author") : ""%>">
                </div>
            </div>
            <div class="col-12 col-lg-4">
                <div class="d-flex align-items-center mt-2">
                    <label for="status" class="label">Tình trạng: </label>
                    <select class="input-text" name="status" id="status" style="flex-grow: 1;">
                        <option value="all" <% if ("all".equals(request.getParameter("status"))) { %> selected <% } %>>
                            Tất cả
                        </option>
                        <option value="on going" <% if ("on going".equals(request.getParameter("status"))) { %>
                                selected <% } %>>Đang tiến hành
                        </option>
                        <option value="finished" <% if ("finished".equals(request.getParameter("status"))) { %>
                                selected <% } %>>Hoàn thành
                        </option>
                        <option value="paused" <% if ("paused".equals(request.getParameter("status"))) { %>
                                selected <% } %>>Tạm hoãn
                        </option>
                    </select>
                </div>
            </div>
            <div class="col-12 col-lg-4">
                <div class="d-flex align-items-center mt-2">
                    <label for="sort" class="label">Sắp xếp theo: </label>
                    <select class="input-text" style="flex-grow: 1;" name="sort" id="sort"
                            value="<%= request.getParameter("sort") != null ? request.getParameter("sort") : "Tên truyện"%>">
                        <option value="name" <% if ("name".equals(request.getParameter("sort"))) { %> selected <% } %>>
                            Tên truyện
                        </option>
                    </select>
                </div>
            </div>
        </div>
        <div class="mt-2 d-flex">
            <label for="genre" class="label">Thể loại: </label>
            <div style="flex-grow: 1" class="genre-holder" id="genre">
                <%--@elvariable id="genres" type="java.util.HashSet<model.Genre>"--%>
                <c:forEach items="${genres}" var="genre">
                    <div class="checkbox-holder">
                        <input class="checkbox1" type="checkbox" data-genre="${genre.id}"
                               id="genre${genre.id}"
                               <c:if test="${genresIDList.contains(genre.id)}">checked="checked"</c:if>>
                        <label for="genre${genre.id}">${genre.name}</label>
                    </div>
                </c:forEach>
            </div>
        </div>
    </form>
    <header class="mb-3">
        <span class="title title--bold title--underline">Kết quả tìm kiếm</span>
    </header>
    <main class="sect-body container-fluid basic-section p-3">

        <%--@elvariable id="novelsSearched" type="java.util.List<model.Novel>"--%>
        <c:choose>
            <c:when test="${novelsSearched!=null}">
                <div class="row">
                    <c:forEach items="${novelsSearched}" var="novelSearched">
                        <div class="col-4 col-md-2 thumb">
                            <a href="/truyen/${novelSearched.id}-${URLSlugification.sluging(novelSearched.name)}" class="no-decor">
                                <div class="thumb__wrapper">
                                    <div class="thumb__img-panel shadow a6-ratio">
                                        <div class="img-wrapper"
                                             style="background-image: url(${novelSearched.image});">
                                        </div>
                                    </div>
                                    <p class="thumb__caption">${StringUtils.truncate(novelSearched.name,100)}</p>
                                </div>
                            </a>
                        </div>
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <div class="row">
                    <div class="col-12">
                        <p class="text-center">Không có kết quả nào</p>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>

        <%@include file="layout/pagination_footer.jsp" %>
    </main>
</div>

<%--Boostrap script--%>
<%@ include file="layout/boostrap_js.jsp" %>
<script src="/js/search_novel.js"></script>
</body>
</html>
