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
<c:if test="${error != null}">
    <div class="alert alert-danger" role="alert">
            ${error}
    </div>
</c:if>
<div class="container mt-5">
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
                    <c:when test="${managingAction.equals(ManageNovelAction.EDIT_CHAPTER)}">
                        <jsp:include page="layout/form_chapter.jsp"/>
                    </c:when>
                </c:choose>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/view/layout/boostrap_js.jsp" %>
<script src="/js/init.js"></script>
</body>
</html>
