<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ACER
  Date: 02/04/2023
  Time: 7:47 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Quản lý thể loại</title>
    <%@include file="/WEB-INF/view/layout/basic_stylesheet.jsp" %>
    <link rel="stylesheet" href="/css/novel_detail.css">
    <style>
        .genre-item:hover{
            color: #3d9970;
        }
    </style>
</head>
<jsp:include page="/WEB-INF/view/layout/header_admin.jsp"></jsp:include>
<body style="background-color: var(--silver)">
<main class="mt-2">
    <div class="container">
        <div class="row">
            <div class="col-12">
                <header class="mb-3 d-flex justify-content-between align-items-center">
                    <span class="title title--underline title--bold">Danh sách thể loại</span>
                    <button class="basic-btn basic-btn--olive justify-content-end" data-toggle="modal"
                            data-target="#createModal">
                        Thêm thể loại
                    </button>
                </header>

                <%--                <div class="d-flex mb-3">--%>
                <%--                    <div class="genre-item">--%>
                <%--                        <a href="#" class="genre-link">Slice of life</a>--%>
                <%--                    </div>--%>
                <%--                    <div class="genre-item">--%>
                <%--                        <a href="#" class="genre-link">Comedy</a>--%>
                <%--                    </div>--%>
                <%--                    <div class="genre-item">--%>
                <%--                        <a href="#" class="genre-link">Shounen</a>--%>
                <%--                    </div>--%>
                <%--                </div>--%>
                <div class="d-flex mb-3" style="flex-wrap: wrap">
                    <c:forEach var="genre" items="${genreList}" varStatus="status">
                        <div class="genre-item" style="background-color: var(--light-gray); margin-bottom: 10px">
                            <a class="genre-link">${genre.getName()}
                                <button class="basic-btn basic-btn--olive"
                                        data-toggle="modal" data-target="#editModal"
                                        onclick="showEditForm(${genre.getId()}, '${genre.getName()}')">
                                    <i class="fas fa-edit"></i>
                                </button>
                                <button class="basic-btn basic-btn--red"
                                        data-toggle="modal" data-target="#deleteModal"
                                        onclick="showDeleteForm(${genre.getId()}, '${genre.getName()}')">
                                    <i class="fas fa-trash-alt"></i>
                                </button>
                            </a>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
    <!--Modal delete-->
    <div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title title title--bold" id="staticBackdropLabel">Xoá thể loại</h5>
                    <i class="fas fa-compress-arrows-alt top-right-btn" data-dismiss="modal" aria-label="Close"
                       style="font-size: x-large"></i>
                </div>
                <div class="modal-body">
                    Bạn có muốn xoá thể loại <span id="genreNameDelete" class="text-success"></span><span> không?</span>
                </div>
                <form action="/admin/genre-manage" method="post">
                    <div class="modal-footer justify-content-center">
                        <button type="button" class="basic-btn basic-btn--olive" data-bs-dismiss="modal">Đóng</button>
                        <button type="submit" class="basic-btn basic-btn--red">Xoá</button>
                        <input hidden name="id" type="text" id="idDelete">
                        <input hidden name="action" value="delete" type="text" id="">
                    </div>
                </form>
            </div>
        </div>
    </div>
    <!--Modal create-->
    <div class="modal fade" id="createModal" tabindex="-1" aria-labelledby="staticBackdropLabel1" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title title title--bold" id="staticBackdropLabel1">Thêm thể loại</h5>
                    <i class="fas fa-compress-arrows-alt top-right-btn" data-dismiss="modal" aria-label="Close"
                       style="font-size: x-large"></i>
                </div>

                <form action="/admin/genre-manage" method="post">
                    <div class="modal-body">
                        <label>Tên thể loại</label>
                        <input type="text" name="genreName" class="w-100" placeholder="Nhập tên thể loại">
                    </div>
                    <div class="modal-footer justify-content-center">
                        <button type="reset" class="basic-btn basic-btn--red" data-bs-dismiss="modal">Reset</button>
                        <button type="submit" class="basic-btn basic-btn--olive">Lưu</button>
                        <input hidden name="action" value="create" type="text">
                    </div>
                </form>
            </div>
        </div>
    </div>
    <!--Modal create-->
    <div class="modal fade" id="editModal" tabindex="-1" aria-labelledby="staticBackdropLabel2" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title title title--bold" id="staticBackdropLabel2">Thêm thể loại</h5>
                    <i class="fas fa-compress-arrows-alt top-right-btn" data-dismiss="modal" aria-label="Close"
                       style="font-size: x-large"></i>
                </div>

                <form action="/admin/genre-manage" method="post">
                    <div class="modal-body">
                        <label for="idUpdate"></label><input id="idUpdate" name="idUpdate" hidden>
                        <label for="genreNameUpdate">Tên thể loại</label>
                        <input type="text" id="genreNameUpdate" name="genreName" class="w-100"
                               placeholder="Nhập tên thể loại">
                    </div>
                    <div class="modal-footer justify-content-center">
                        <button type="reset" class="basic-btn basic-btn--red" data-bs-dismiss="modal">Reset</button>
                        <button type="submit" class="basic-btn basic-btn--olive">Lưu</button>
                        <input hidden name="action" value="update" type="text">
                    </div>
                </form>
            </div>
        </div>
    </div>
</main>
</body>
<%@include file="/WEB-INF/view/layout/boostrap_js.jsp" %>
<script>
    function showDeleteForm(id, name) {
        document.getElementById("idDelete").value = id;
        document.getElementById("genreNameDelete").innerText = name;
    }

    function showEditForm(id, name) {
        document.getElementById("idUpdate").value = id;
        document.getElementById("genreNameUpdate").value = name;
    }
</script>
</html>
