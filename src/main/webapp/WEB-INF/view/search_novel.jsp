<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <form class="container-fluid basic-section p-3" id="search-form"
          onsubmit="event.preventDefault(); submitForm()">
        <div class="row">
            <div class="d-flex align-items-center col-10">
                <label for="novel" class="label">Tên truyện: </label>
                <input class="input-text" style="flex-grow: 1;" type="text" name="novel" id="novel"
                       placeholder="Tên truyện" value="<%= request.getParameter("novel") != null ? request.getParameter("novel") : ""%>">
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
                           placeholder="Tên tác giả" value="<%= request.getParameter("author") != null ? request.getParameter("author") : ""%>" >
                </div>
            </div>
            <div class="col-12 col-lg-4">
                <div class="d-flex align-items-center mt-2">
                    <label for="status" class="label">Tình trạng: </label>
                    <!-- <input style="flex-grow: 1;" type="" name="author" id="status" placeholder="Tên tác giả"> -->
                    <select class="input-text" name="status" id="status" style="flex-grow: 1;" value="<%= request.getParameter("status") != null ? request.getParameter("status") : "Tất cả"%>">
                        <option value="all">Tất cả</option>
                        <option value="on going">Đang tiến hành</option>
                        <option value="finished">Hoàn thành</option>
                        <option value="paused">Tạm hoãn</option>
                    </select>
                </div>
            </div>
            <div class="col-12 col-lg-4">
                <div class="d-flex align-items-center mt-2">
                    <label for="sort" class="label">Sắp xếp theo: </label>
                    <select class="input-text" style="flex-grow: 1;" name="sort" id="sort" value="<%= request.getParameter("sort") != null ? request.getParameter("sort") : "Tên truyện"%>">
                        <option value="name">Tên truyện</option>
<%--                        <option value="author name">Tác giả</option>--%>
                        <option value="comment">Lượt bình luận</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="mt-2 d-flex">
            <label for="genre" class="label">Thể loại: </label>
            <div style="flex-grow: 1" class="genre-holder" id="genre">
                <%
                    String genresIDString = request.getParameter("genres");
                    List<Integer> genresIDList = new ArrayList<>();

                    if (!(genresIDString == null) && !genresIDString.isEmpty()) {
                        String[] arrGenresIDString = genresIDString.split(",");
                        for (int i = 0; i < arrGenresIDString.length; i++) {
                            genresIDList.add(Integer.parseInt(arrGenresIDString[i]));
                        }
                    }
                    request.setAttribute("genresIDList", genresIDList);
                %>
                <c:forEach items="${genres}" var="genre">
                    <div class="checkbox-holder">
                        <input class="checkbox1" type="checkbox" data-genre="${genre.id}"
                               id="genre${genre.id}"
                               <c:if test="${genresIDList.contains(genre.id)}">checked="checked"</c:if>>
                        <label for="genre${genre.id}">${genre.name}</label>
                    </div>
                </c:forEach>

            <%--                <div class="checkbox-holder">--%>
<%--                    <input class="checkbox1" type="checkbox" data-genre="1" id="genre1">--%>
<%--                    <label for="genre1"> Genre1</label>--%>
<%--                </div>--%>
<%--                <div class="checkbox-holder">--%>
<%--                    <input class="checkbox1" type="checkbox" data-genre="2" id="genre2">--%>
<%--                    <label for="genre2"> Genre2</label>--%>
<%--                </div>--%>
            </div>
        </div>
    </form>
    <header class="mb-3">
        <span class="title title--bold title--underline">Kết quả tìm kiếm</span>
    </header>
    <div class="row">
        <div class="col-4 col-md-2 thumb">
            <c:forEach items="${novelsSearched}" var="novelSearched">
                <a href="#" class="no-decor">
                    <div class="thumb__wrapper">
                        <div class="thumb__img-panel shadow a6-ratio">
                            <div class="img-wrapper"
                                 style="background-image: url(${novelSearched.image});">
                            </div>
                        </div>
                        <p class="thumb__caption">${novelSearched.name}</p>
                    </div>
                </a>
<%--                <div class="checkbox-holder">--%>
<%--                    <input class="checkbox1" type="checkbox" data-genre="${genre.id}"--%>
<%--                           id="genre${genre.id}">--%>
<%--                    <label for="genre${genre.id}"> ${genre.name}</label>--%>
<%--                </div>--%>
            </c:forEach>
            <a href="#" class="no-decor">
                <div class="thumb__wrapper">
                    <div class="thumb__img-panel shadow a6-ratio">
                        <div class="img-wrapper"
                             style="background-image: url('https://images.fpt.shop/unsafe/filters:quality(90)/fptshop.com.vn/uploads/images/tin-tuc/152650/Originals/Hu%20Tao.jpg');">
                        </div>
                    </div>
                    <p class="thumb__caption">Hu Tao saves my life</p>
                </div>
            </a>
        </div>
    </div>

</div>

<%--Boostrap script--%>
<%@ include file="layout/boostrap_js.jsp" %>

<script src="/js/search_novel.js"></script>
</body>
</html>
