<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="reqVolume" type="model.Volume"--%>
<%--@elvariable id="submitAllowed" type="java.lang.Boolean"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form method="post" class="containter-fluid ml-5 mr-5" enctype="multipart/form-data"
      onkeydown="return event.key != 'Enter';">
    <div class="d-flex flex-column align-items-center mb-3">
        <div class="a6-ratio img-cover mb-2">
            <div class="img-wrapper border" id="image-preview"
                 style="background-image: url('${reqVolume.image}');">
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
        <label for="volume_name" class="basic-label required">Tiêu đề </label>
        <input class="input-text" style="flex-grow: 1" type="text" name="volume_name" id="volume_name"
               placeholder="Tiêu đề" value="${reqVolume.name}" required>
    </div>
    <c:if test="${submitAllowed == null || submitAllowed == true}">
        <div class="d-flex justify-content-center">
            <button type="submit" class="basic-btn basic-btn--olive">Xác nhận</button>
        </div>
    </c:if>
</form>

<script src="/js/form.js"></script>
<script>
    const imgPreview = document.getElementById('image-preview');
    const imgInput = document.getElementById('image-input');
    bindImagePreview(imgInput, imgPreview);
</script>