<%--@elvariable id="managingAction" type="core.metadata.ManageNovelAction"--%>
<%--@elvariable id="ManageNovelAction" type="core.metadata.ManageNovelAction.class"--%>
<%--@elvariable id="error" type="java.lang.String"--%>
<%@ page import="core.metadata.ManageNovelAction" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chỉnh sửa</title>
    <%@include file="/WEB-INF/view/layout/basic_stylesheet.jsp" %>
</head>
<body style="background-color: var(--silver)">
<%@include file="layout/header.jsp" %>

<div class="container mt-3">
    <c:if test="${error != null}">
        <div class="alert alert-danger mb-3" role="alert">
                ${error}
        </div>
    </c:if>
    <div class="row">
        <div class="col-12 col-md-3">
            <div class="basic-section">
                <div class="basic-section__header">
                    <h4 class="basic-section__title">
                        Mục lục
                    </h4>
                </div>
                <jsp:include page="layout/novel_menu.jsp"/>
            </div>
        </div>
        <div class="col">
            <div class="basic-section">
                <div class="basic-section__header">
                    <h4 class="basic-section__title">
                        <c:choose>
                            <c:when test="${managingAction.equals(ManageNovelAction.EDIT_NOVEL)}">
                                Chỉnh sửa truyện
                            </c:when>
                            <c:when test="${managingAction.equals(ManageNovelAction.ADD_VOLUME)}">
                                Thêm tập mới
                            </c:when>
                            <c:when test="${managingAction.equals(ManageNovelAction.EDIT_VOLUME)}">
                                Chỉnh sửa tập truyện
                            </c:when>
                            <c:when test="${managingAction.equals(ManageNovelAction.ADD_CHAPTER)}">
                                Thêm chương mới
                            </c:when>
                            <c:when test="${managingAction.equals(ManageNovelAction.EDIT_CHAPTER)}">
                                Chỉnh sửa chương truyện
                            </c:when>
                        </c:choose>
                    </h4>
                </div>
                <c:choose>
                    <c:when test="${managingAction.equals(ManageNovelAction.EDIT_NOVEL)}">
                        <jsp:include page="layout/form_novel.jsp"/>
                    </c:when>
                    <c:when test="${managingAction.equals(ManageNovelAction.ADD_VOLUME)}">
                        <jsp:include page="layout/form_volume.jsp"/>
                    </c:when>
                    <c:when test="${managingAction.equals(ManageNovelAction.EDIT_VOLUME)}">
                        <jsp:include page="layout/form_volume.jsp"/>
                    </c:when>
                    <c:when test="${managingAction.equals(ManageNovelAction.ADD_CHAPTER)}">
                        <jsp:include page="layout/form_chapter.jsp"/>
                    </c:when>
                    <c:when test="${managingAction.equals(ManageNovelAction.EDIT_CHAPTER)}">
                        <jsp:include page="layout/form_chapter.jsp"/>
                    </c:when>
                </c:choose>
            </div>
        </div>
    </div>
</div>

<!--Modal delete-->
<div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title title title--bold" id="staticBackdropLabel">Xoá truyện</h5>
                <i class="fas fa-compress-arrows-alt top-right-btn" data-dismiss="modal" aria-label="Close"
                   style="font-size: x-large"></i>
            </div>
            <div class="modal-body">
                Bạn có muốn xoá <span id="objectToDelete"></span>
                <span id="objectToDeleteName" class="text-success"></span> không?
            </div>
            <form id="deleteForm" method="POST">
                <div class="modal-footer justify-content-center pb-0">
                    <button type="button" class="basic-btn basic-btn--olive" data-dismiss="modal">Đóng</button>
                    <button type="submit" class="basic-btn basic-btn--red">Xoá</button>
                </div>
            </form>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/view/layout/boostrap_js.jsp" %>
<script src="/js/init.js"></script>
<script>
    const deleteModal = document.getElementById('deleteModal');

    function deleteNovel(novelId, novelName) {
        document.getElementById('objectToDelete').innerText = 'truyện ';
        document.getElementById('objectToDeleteName').innerText = novelName;
        document.getElementById('deleteForm').action = '/ca-nhan/tieu-thuyet/' + novelId + '?action=delete-novel';
        deleteModal.classList.toggle('show');
    }

    function deleteVolume(volumeId, volumeName) {
        document.getElementById('objectToDelete').innerText = 'tập ';
        document.getElementById('objectToDeleteName').innerText = volumeName;
        document.getElementById('deleteForm').action = '/ca-nhan/tap-truyen/' + volumeId + '?action=delete-volume';
        deleteModal.classList.toggle('show');
    }

    function deleteChapter(chapterId, chapterName) {
        document.getElementById('objectToDelete').innerText = 'chương ';
        document.getElementById('objectToDeleteName').innerText = chapterName;
        document.getElementById('deleteForm').action = '/ca-nhan/chuong-truyen/' + chapterId + '?action=delete-chapter';
        deleteModal.classList.toggle('show');
    }
</script>
</body>
</html>
