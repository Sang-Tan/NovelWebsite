<%--@elvariable id="user" type="model.User"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 3/26/2023
  Time: 7:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thông tin cá nhân</title>
    <%@include file="/WEB-INF/view/layout/basic_stylesheet.jsp" %>
</head>
<body>
<%@include file="layout/header.jsp" %>
<div class="container mt-5">
    <div class="row">
        <div class="col">
            <div class="basic-section">
                <div class="basic-section__header">
                    <h4 class="basic-section__title">Thông tin cá nhân</h4>
                </div>
                <form method="post" class="containter-fluid ml-5 mr-5" enctype="multipart/form-data">
                    <div class="d-flex flex-column align-items-center mb-3">
                        <div class="square-ratio img-cover mb-2">
                            <div class="img-wrapper border" id="image-preview"
                                 style="background-image: url('${user.avatar}');">
                            </div>
                        </div>
                        <div class="upload-btn-wrapper">
                            <button type="button" class="basic-btn basic-btn--maroon">
                                Chọn ảnh
                            </button>
                            <input type="file" name="avatar" title="Ảnh đại diện" id="image-input"
                                   accept="image/png, .jpg">
                        </div>
                    </div>
                    <div class=" d-flex align-items-center mb-2">
                        <label for="display-name" class="basic-label required">Tên </label>
                        <input class="input-text" style="flex-grow: 1" type="text" name="display_name" id="display-name"
                               placeholder="Tên của bạn UwU" value="${user.displayName}" required>
                    </div>
                    <div class="d-flex justify-content-center">
                        <button type="submit" class="basic-btn basic-btn--olive">Xác nhận</button>
                    </div>
                </form>

            </div>
        </div>
    </div>
</div>
<script charset="UTF-8" src="/js/form.js"></script>
<script>
    const image_preview = document.getElementById("image-preview");
    const image_input = document.getElementById("image-input");

    bindImagePreview(image_input, image_preview);
</script>
<%--@elvariable id="errors" type="java.util.HashMap<java.lang.String,java.lang.String>"--%>
<c:if test="${errors != null}">
    <script>
        let errors = "";
        <c:forEach items="${errors}" var="error">
        errors += "${error.value} \n";
        </c:forEach>
        alert(errors);
    </script>
</c:if>
</body>
</html>
