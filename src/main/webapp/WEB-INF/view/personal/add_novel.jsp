<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 3/17/2023
  Time: 2:49 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thêm truyện</title>
    <%@include file="/WEB-INF/view/layout/basic_stylesheet.jsp" %>
</head>
<body>
<%@include file="layout/header.jsp" %>

<div class="container mt-3">
    <div class="basic-section">
        <div class="basic-section__header ">
            <h4>Thêm truyện</h4>
        </div>
        <form method="post" class="containter-fluid ml-5 mr-5" enctype="multipart/form-data"
              onsubmit="event.preventDefault(); submitForm(event)">
            <div class="d-flex flex-column align-items-center mb-3">
                <div class="a6-ratio img-cover mb-2">
                    <div class="img-wrapper border" id="image-preview"
                         style="background-image: url('https://images.fpt.shop/unsafe/filters:quality(90)/fptshop.com.vn/uploads/images/tin-tuc/152650/Originals/Hu%20Tao.jpg');">
                    </div>
                </div>
                <div class="upload-btn-wrapper">
                    <button type="button" class="basic-btn basic-btn--maroon">
                        Chọn ảnh
                    </button>
                    <input type="file" name="image" title="Ảnh bìa" id="image-input"
                           accept="image/png, .jpg">
                </div>
            </div>
            <div class=" d-flex align-items-center mb-2">
                <label for="novel_name" class="basic-label required">Tên truyện </label>
                <input class="input-text" style="flex-grow: 1" type="text" name="novel_name" id="novel_name"
                       placeholder="Tên truyện" required>
            </div>
            <div class="d-flex align-items-center mb-2">
                <label for="genre" class="basic-label required" title="Chọn ít nhất 1 thể loại">Thể loại: </label>
                <div style="flex-grow: 1" class="checkbox-list" id="genre">
                    <%--@elvariable id="genres" type="java.util.List<model.Genre>"--%>
                    <c:forEach items="${genres}" var="genre">
                        <div class="checkbox-holder">
                            <input class="checkbox1" type="checkbox" data-genre="${genre.id}"
                                   id="genre${genre.id}">
                            <label for="genre${genre.id}"> ${genre.name}</label>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <div class="d-flex align-items-start mb-2">
                <div>
                    <label for="summary" class="basic-label required">Tóm tắt : </label>
                </div>
                <textarea name="summary" title="Tóm tắt" id="summary" rows="5" class="basic-textarea"
                          required></textarea>
            </div>
            <div class="d-flex align-items-start mb-4">
                <label for="status" class="basic-label">Tình trạng: </label>
                <!-- <input style="flex-grow: 1;" type="" name="author" id="status" placeholder="Tên tác giả"> -->
                <select class="input-text" name="status" id="status" style="flex-grow: 1;">
                    <option value="on going">Đang tiến hành</option>
                    <option value="finished">Hoàn thành</option>
                    <option value="paused">Tạm hoãn</option>
                </select>
            </div>
            <div class="d-flex justify-content-center">
                <button type="submit" class="basic-btn basic-btn--olive">Đăng truyện</button>
            </div>
        </form>

    </div>
</div>

<%-- boostrap script --%>
<%@include file="/WEB-INF/view/layout/boostrap_js.jsp" %>

<script charset="UTF-8" src="/js/form.js"></script>
<script type="text/javascript" charset="UTF-8" src="/js/novel_manage.js"></script>
</body>
</html>
