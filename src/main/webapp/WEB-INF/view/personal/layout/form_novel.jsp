<%--@elvariable id="reqNovel" type="model.Novel"--%>
<%--@elvariable id="genres" type="java.util.List<model.Genre>"--%>
<%--@elvariable id="novelGenreIds" type="java.util.Collection<java.lang.Integer>"--%>
<%--@elvariable id="Novel" type="model.Novel.class"--%>
<%--@elvariable id="submitAllowed" type="java.lang.Boolean"--%>
<%@page import="model.Novel" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form method="post" class="containter-fluid ml-5 mr-5" enctype="multipart/form-data"
      onsubmit="event.preventDefault(); submitForm(event)">
    <div class="d-flex flex-column align-items-center mb-3">
        <div class="a6-ratio img-cover mb-2">
            <div class="img-wrapper border" id="image-preview"
                 style="background-image: url(${reqNovel.image});">
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
               placeholder="Tên truyện" value="${reqNovel.name}" required>
    </div>
    <div class="d-flex align-items-center mb-2">
        <label for="genre" class="basic-label required" title="Chọn ít nhất 1 thể loại">Thể loại: </label>
        <div style="flex-grow: 1" class="checkbox-list" id="genre">
            <c:forEach items="${genres}" var="genre">
                <div class="checkbox-holder">
                    <input class="checkbox1" type="checkbox" data-genre="${genre.id}"
                           id="genre${genre.id}" <c:if test="${novelGenreIds.contains(genre.id)}">checked</c:if>>
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
                  required>${reqNovel.summary}</textarea>
    </div>
    <div class="d-flex align-items-start mb-4">
        <label for="status" class="basic-label">Tình trạng: </label>
        <!-- <input style="flex-grow: 1;" type="" name="author" id="status" placeholder="Tên tác giả"> -->
        <select class="input-text" name="status" id="status" style="flex-grow: 1;">
            <option value="${Novel.STATUS_ON_GOING}"
                    <c:if test="${reqNovel.status.equals(Novel.STATUS_ON_GOING)}">selected</c:if>>
                Đang tiến hành
            </option>
            <option value="${Novel.STATUS_FINISHED}"
                    <c:if test="${reqNovel.status.equals(Novel.STATUS_FINISHED)}">selected</c:if>>
                Hoàn thành
            </option>
            <option value="${Novel.STATUS_PAUSED}"
                    <c:if test="${reqNovel.status.equals(Novel.STATUS_PAUSED)}">selected</c:if>>
                Tạm ngưng
            </option>
        </select>
    </div>

    <c:if test="${submitAllowed == null || submitAllowed == true}">
        <div class="d-flex justify-content-center">
            <button type="submit" class="basic-btn basic-btn--olive">Xác nhận</button>
        </div>
    </c:if>
</form>

<script src="/js/form.js"></script>
<script src="/js/novel_manage.js"></script>
