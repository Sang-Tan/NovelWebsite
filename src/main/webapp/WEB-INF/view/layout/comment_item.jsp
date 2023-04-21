<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="reqComment" type="model.Comment"--%>
<%@page import="core.string_process.TimeConverter" %>
<%--@elvariable id="TimeConverter" type="core.string_process.TimeConverter.class"--%>
<div class="cmt-group__item">
    <div class="cmt-group__avatar"
         style="background-image: url('${reqComment.owner.avatar}');">
    </div>
    <div class="cmt-detail" style="display: block; hyphens: auto; overflow: hidden; overflow-wrap: break-word;">
        <a href="/thanh-vien/${reqComment.owner.id}"
           class="cmt-detail__name">${reqComment.owner.displayName}</a>
        <time title="${TimeConverter.convertToVietnameseTime(reqComment.commentTime)}"
              class="cmt-time">${TimeConverter.convertToTimeAgo(reqComment.commentTime)}</time>
        ${HTMLParser.wrapEachLineWithTag(reqComment.content, "p")}
        <div class="cmt-toolkit">
            <c:if test="${user != null}">
                <div class="cmt-toolkit__item">
                    <a href="#" data-reply-to="${reqComment.id}"
                       class="cmt-toolkit__link">Trả lời</a>
                </div>
                <div class="cmt-toolkit__item">
                    <a href="" class="cmt-toolkit__link" data-toggle="modal" data-target="#reportCommentModal"
                       onclick="showReportCommentForm(${reqComment.id}, ${user.id})">
                        Báo cáo
                    </a>
                </div>
            </c:if>
        </div>
    </div>
</div>
