<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--@elvariable id="postCommentAllowed" type="boolean"--%>
<%--@elvariable id="deactivateCommentAllowed" type="boolean"--%>

<%--@elvariable id="TimeConverter" type="core.string_process.TimeConverter.class"--%>
<%@page import="core.string_process.TimeConverter" %>

<%--@elvariable id="User" type="model.User.class"--%>
<%@page import="model.User" %>


<c:set var="isDeactivated" value="${reqComment.deactiveBy != null}"/>

<div class="cmt-group__item" id="novel-comment-${reqComment.id}">
    <div class="cmt-group__avatar"
         style="background-image: url('${reqComment.owner.avatar}');">
    </div>
    <div class="cmt-detail" style="display: block; hyphens: auto; overflow: hidden; overflow-wrap: break-word;">
        <a href="/thanh-vien/${reqComment.owner.id}"
           class="cmt-detail__name">${reqComment.owner.displayName}</a>
        <c:choose>
            <c:when test="${reqComment.owner.role.equals(User.ROLE_MODERATOR)}">
                <div class="cmt-role-box cmt-role-box--mod">Mod</div>
            </c:when>
            <c:when test="${reqComment.owner.role.equals(User.ROLE_ADMIN)}">
                <div class="cmt-role-box cmt-role-box--admin">Admin</div>
            </c:when>
        </c:choose>
        <time title="${TimeConverter.convertToVietnameseTime(reqComment.commentTime)}"
              class="cmt-time">${TimeConverter.convertToTimeAgo(reqComment.commentTime)}</time>
        <c:choose>
            <c:when test="${!isDeactivated}">
                ${HTMLParser.wrapEachLineWithTag(reqComment.content, "p")}
            </c:when>
            <c:otherwise>
                <p style="color: var(--gray)">Bình luận này đã bị ẩn</p>
            </c:otherwise>
        </c:choose>

        <div class="cmt-toolkit">
            <c:if test="${user != null && postCommentAllowed && !isDeactivated}">
                <div class="cmt-toolkit__item">
                    <a href="#" data-reply-to="${reqComment.id}"
                       class="cmt-toolkit__link">Trả lời</a>
                </div>
            </c:if>
            <c:if test="${user != null && !isDeactivated}">
                <div class="cmt-toolkit__item">
                    <a href="" class="cmt-toolkit__link" data-toggle="modal" data-target="#reportCommentModal"
                       onclick="showReportCommentForm(${reqComment.id}, ${user.id})">
                        Báo cáo
                    </a>
                </div>
            </c:if>
            <c:if test="${deactivateCommentAllowed && !isDeactivated && reqComment.owner.role.equals(User.ROLE_MEMBER)}">
                <div class="cmt-toolkit__item">
                    <a href="#" class="cmt-toolkit__link" data-toggle="modal" data-target="#deactivateCommentModal"
                       onclick="showDeactivateCommentForm(${reqComment.id})">
                        Ẩn
                    </a>
                </div>
            </c:if>
        </div>
    </div>
</div>
