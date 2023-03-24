<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 3/20/2023
  Time: 8:14 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form method="post" class="containter-fluid ml-5 mr-5" enctype="multipart/form-data">
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
        <label for="volume_name" class="basic-label required">Tiêu đề </label>
        <input class="input-text" style="flex-grow: 1" type="text" name="volume_name" id="volume_name"
               placeholder="Tiêu đề" required>
    </div>
    <div class="d-flex justify-content-center">
        <button type="submit" class="basic-btn basic-btn--olive">Xác nhận</button>
    </div>
</form>

<script src="/js/form.js"></script>
<script>
    const imgPreview = document.getElementById('image-preview');
    const imgInput = document.getElementById('image-input');
    bindImagePreview(imgInput, imgPreview);
</script>