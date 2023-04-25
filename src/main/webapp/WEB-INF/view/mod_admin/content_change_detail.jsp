<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--@elvariable id="newContents"
type="java.util.List<core.Pair<java.lang.String,core.media.MediaObject>>"--%>

<%--@elvariable id="changedContents"
type="java.util.List<core.Pair<java.lang.String,java.util.List<core.media.MediaObject>>>"--%>

<%--@elvariable id="detailTitle" type="java.lang.String"--%>

<%--@elvariable id="changeType" type="service.upload_change.ContentChangeType"--%>

<%--@elvariable id="ContentChangeType" type="service.upload_change.ContentChangeType.class"--%>
<%@page import="service.upload_change.ContentChangeType" %>

<%--@elvariable id="MediaType" type="core.media.MediaType.class"--%>
<%@ page import="core.media.MediaType" %>

<html>
<head>
    <title>${detailTitle}</title>
    <%@include file="/WEB-INF/view/layout/basic_stylesheet.jsp" %>
    <link href="/css/string_metric.css" rel="stylesheet">
</head>
<body>
<jsp:include page="/WEB-INF/view/layout/header_mod.jsp"></jsp:include>
<div class="container">
    <header>
        <span class="title">${detailTitle}</span>
    </header>
    <c:choose>
        <c:when test="${changeType.equals(ContentChangeType.NONE)}">
            <h3 class="text-center">Không có thay đổi nào</h3>
        </c:when>
        <c:otherwise>
            <table class="table table-bordered">
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
        </c:otherwise>
    </c:choose>


</div>
<jsp:include page="/WEB-INF/view/layout/boostrap_js.jsp"></jsp:include>
<script src="/js/content_detail.js"></script>
</body>
</html>
