<%--@elvariable id="mediaItem" type="core.media.MediaObject"--%>

<%--@elvariable id="MediaType" type="core.media.MediaType.class"--%>
<%@ page import="core.media.MediaType" %>

<%--@elvariable id="HTMLParser" type="core.string_process.HTMLParser.class"--%>
<%@page import="core.string_process.HTMLParser" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:choose>
    <c:when test="${mediaItem.type.equals(MediaType.IMAGE_URL)}">
        <div class="a6-ratio" style="width: 140px;">
            <div class="img-wrapper border"
                 style="background-image: url('${mediaItem.data}')">
            </div>
        </div>
    </c:when>
    <c:when test="${mediaItem.type.equals(MediaType.INLINE_TEXT)}">
        <p>${mediaItem.data}</p>
    </c:when>
    <c:when test="${mediaItem.type.equals(MediaType.MULTILINE_TEXT)}">
        ${HTMLParser.wrapEachLineWithTag(mediaItem.data, "p")}
    </c:when>
</c:choose>
