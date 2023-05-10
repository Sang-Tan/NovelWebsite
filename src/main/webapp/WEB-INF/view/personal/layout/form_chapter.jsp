<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="reqChapter" type="model.Chapter"--%>
<%--@elvariable id="submitAllowed" type="java.lang.Boolean"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:if test="${submitAllowed == null}">
    <c:set var="submitAllowed" value="true"/>
</c:if>

<form method="post" class="containter-fluid ml-5 mr-5" enctype="application/x-www-form-urlencoded">
    <div class="d-flex align-items-center mb-2">
        <label for="chapter_name" class="basic-label text-left required">Tiêu đề </label>
        <input class="input-text " style="flex-grow: 1" type="text" name="chapter_name" id="chapter_name"
               placeholder="Tiêu đề" value="${reqChapter.name}" ${submitAllowed? "" : "disabled"} required>
    </div>
    <div class="d-flex flex-column mb-3">
        <div class="mb-2">
            <label for="chapter_content" class="basic-label d-block text-left required">Nội dung</label>
        </div>
        <textarea rows="10" class="input-text" style="flex-grow: 1" name="chapter_content" id="chapter_content"
                  placeholder="Nội dung" ${submitAllowed? "" : "disabled"} required>${reqChapter.content}</textarea>
    </div>
    <c:if test="${submitAllowed}">
        <div class="d-flex justify-content-center">
            <button type="submit" class="basic-btn basic-btn--olive">Xác nhận</button>
        </div>
    </c:if>
</form>
