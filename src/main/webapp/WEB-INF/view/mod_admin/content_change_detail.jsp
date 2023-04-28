<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--@elvariable id="newContents"
type="java.util.List<core.Pair<java.lang.String,core.media.MediaObject>>"--%>

<%--@elvariable id="changedContents"
type="java.util.List<core.Pair<java.lang.String,java.util.List<core.media.MediaObject>>>"--%>

<%--@elvariable id="novelRelatedContentType" type="service.upload_change.metadata.NovelRelatedContentType"--%>

<%--@elvariable id="changeType" type="service.upload_change.metadata.ContentChangeType"--%>

<%--@elvariable id="reqNovel" type="model.Novel"--%>

<%--@elvariable id="reqVolume" type="model.Volume"--%>

<%--@elvariable id="reqChapter" type="model.Chapter"--%>

<%--@elvariable id="ContentChangeType" type="service.upload_change.metadata.ContentChangeType.class"--%>
<%@page import="service.upload_change.metadata.ContentChangeType" %>

<%--@elvariable id="MediaType" type="core.media.MediaType.class"--%>
<%@ page import="core.media.MediaType" %>

<%--@elvariable id="NovelRelatedContentType" type="service.upload_change.metadata.NovelRelatedContentType.class"--%>
<%@page import="service.upload_change.metadata.NovelRelatedContentType" %>

<%--@elvariable id="detailTitle" type="java.lang.String"--%>
<c:choose>
    <c:when test="${novelRelatedContentType.equals(NovelRelatedContentType.NOVEL)}">
        <c:set var="detailTitle" value="Chi tiết chỉnh sửa tiểu thuyết"></c:set>
    </c:when>
    <c:when test="${novelRelatedContentType.equals(NovelRelatedContentType.VOLUME)}">
        <c:set var="detailTitle" value="Chi tiết chỉnh sửa tập"></c:set>
    </c:when>
    <c:when test="${novelRelatedContentType.equals(NovelRelatedContentType.CHAPTER)}">
        <c:set var="detailTitle" value="Chi tiết chỉnh sửa chương"></c:set>
    </c:when>
</c:choose>

<html>

<head>
    <title>${detailTitle}</title>
    <%@include file="/WEB-INF/view/layout/basic_stylesheet.jsp" %>
    <link href="/css/string_metric.css" rel="stylesheet">
</head>
<body>
<jsp:include page="/WEB-INF/view/layout/header_mod.jsp"></jsp:include>
<div class="container mt-3">
    <c:choose>
        <c:when test="${changeType.equals(ContentChangeType.NONE)}">
            <h3 class="text-center">Không có thay đổi nào</h3>
        </c:when>
        <c:otherwise>
            <header>
                <span class="title">${detailTitle}</span>
            </header>
            <table class="table table-bordered mt-3">
                <thead>
                <tr>
                    <c:choose>
                        <c:when test="${changeType.equals(ContentChangeType.NEW)}">
                            <th>Tên</th>
                            <th>Nội dung</th>
                        </c:when>
                        <c:when test="${changeType.equals(ContentChangeType.UPDATE)}">
                            <th>Tên</th>
                            <th>Cũ</th>
                            <th>Mới</th>
                        </c:when>
                    </c:choose>
                </tr>
                </thead>
                <tbody class="paragraph-spacing-1">
                <c:choose>
                    <c:when test="${changeType.equals(ContentChangeType.NEW)}">
                        <c:forEach items="${newContents}" var="newContent">
                            <tr>
                                <th>${newContent.key}</th>
                                <c:set var="mediaItem" value="${newContent.value}"/>
                                <td>
                                    <%@include file="media_item.jsp" %>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:when test="${changeType.equals(ContentChangeType.UPDATE)}">
                        <c:forEach items="${changedContents}" var="changedContent">
                            <tr>
                                <th>${changedContent.key}</th>
                                <c:choose>
                                    <c:when test="${changedContent.value.get(0).type.equals(MediaType.MULTILINE_TEXT)}">
                                        <c:set var="mediaItem" value="${changedContent.value.get(0)}"/>
                                        <td id="oldMultiline">
                                            <%@include file="media_item.jsp" %>
                                        </td>
                                        <c:set var="mediaItem" value="${changedContent.value.get(1)}"/>
                                        <td id="newMultiline">
                                            <%@include file="media_item.jsp" %>
                                        </td>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="mediaItem" value="${changedContent.value.get(0)}"/>
                                        <td>
                                            <%@include file="media_item.jsp" %>
                                        </td>
                                        <c:set var="mediaItem" value="${changedContent.value.get(1)}"/>
                                        <td>
                                            <%@include file="media_item.jsp" %>
                                        </td>
                                    </c:otherwise>
                                </c:choose>

                            </tr>
                        </c:forEach>
                    </c:when>
                </c:choose>
                </tbody>
            </table>
            <div class="mt-3 d-flex justify-content-center">
                <button class="basic-btn basic-btn--red mr-3"
                        data-toggle="modal" data-target="#rejectModal"
                        onclick="showNovelForm(${novel.id}, '${novel.name}')">
                    <i class="fas fa-times-circle"></i> Từ chối
                </button>
                <button class="basic-btn basic-btn--olive"
                        data-toggle="modal" data-target="#approveModal"
                        onclick="showNovelForm(${novel.id}, '${novel.name}')">
                    <i class="fas fa-check"></i> Phê duyệt
                </button>
            </div>
        </c:otherwise>
    </c:choose>
    <!--Approve modal-->
    <div class="modal fade" id="approveModal" tabindex="-1" aria-labelledby="staticBackdropLabel3"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title title title--bold" id="staticBackdropLabel3">Phê duyệt nội dung</h5>
                    <i class="fas fa-compress-arrows-alt top-right-btn" data-dismiss="modal" aria-label="Close"
                       style="font-size: x-large"></i>
                </div>
                <div class="modal-body">
                    Xác nhận phê duyệt nội dung này?
                </div>
                <form method="get">
                    <div class="modal-footer justify-content-center">
                        <button type="button" class="basic-btn basic-btn--gray" data-dismiss="modal">Đóng</button>
                        <button type="submit" class="basic-btn basic-btn--olive">Xác nhận</button>
                        <input hidden name="action" value="approve" type="text">
                    </div>
                </form>
            </div>
        </div>
    </div>
    <!--Reject modal-->
    <div class="modal fade" id="rejectModal" tabindex="-1" aria-labelledby="staticBackdropLabel2"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title title title--bold" id="staticBackdropLabel2">Từ chối phê duyệt</h5>
                    <i class="fas fa-compress-arrows-alt top-right-btn" data-dismiss="modal" aria-label="Close"
                       style="font-size: x-large"></i>
                </div>
                <div class="modal-body">
                    <div class="d-flex">
                        <label class="basic-label" for="rejectReasonInp" class="mr-3" style="min-width: unset;">Lý
                            do:</label>
                        <input class="input-text" id="rejectReasonInp" type="text"
                               placeholder="Lý do không chấp nhận nội dung" style="flex:1;">
                    </div>
                    <form method="post" class="mt-3">
                        <div class="d-flex justify-content-center">
                            <button type="button" class="basic-btn basic-btn--gray mr-2" data-dismiss="modal">Đóng
                            </button>
                            <button type="submit" class="basic-btn basic-btn--olive">Xác nhận</button>
                        </div>
                        <input hidden name="action" value="reject" type="text">
                    </form>
                </div>

            </div>
        </div>
    </div>

</div>
<jsp:include page="/WEB-INF/view/layout/boostrap_js.jsp"></jsp:include>
<script src="/js/content_detail.js"></script>
</body>
</html>
