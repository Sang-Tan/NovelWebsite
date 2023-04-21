<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--@elvariable id="user" type="model.User"--%>
<%--@elvariable id="reqChapter" type="model.Chapter"--%>
<%--@elvariable id="commentLimit" type="java.lang.Integer"--%>

<%--@elvariable id="RestrictionService" type="service.RestrictionService.class"--%>
<%@page import="service.RestrictionService" %>

<%--@elvariable id="Restriction" type="model.Restriction.class"--%>
<%@page import="model.intermediate.Restriction" %>

<%--@elvariable id="postCommentAllowed" type="boolean"--%>
<c:set var="postCommentAllowed"
       value="${(user != null) &&
       (RestrictionService.getUnexpiredRestriction(user.id, Restriction.TYPE_COMMENT) == null)}"/>

<section class="basic-section">
    <header class="basic-section__header">
        <h5>Bình luận</h5>
    </header>
    <div class="container-fluid">
        <c:choose>
            <c:when test="${user == null}">
                <p>Bạn phải
                    <a href="#login-modal" class="navbar__link navbar__list-text"
                       data-toggle="modal"
                       data-target="#login-modal">Đăng nhập</a>
                    hoặc
                    <a href="#register-modal"
                       class="navbar__link navbar__list-text"
                       data-toggle="modal"
                       data-target="#register-modal">Tạo
                        tài khoản</a>
                    để có thể bình luận</p>
            </c:when>
            <c:when test="${postCommentAllowed != true}">
                <p>Bạn đã bị cấm bình luận</p>
            </c:when>

            <c:otherwise>
                <form id="root-comment-form" method="post" class="mb-3">
                    <textarea name="content" title="cmt" id="" rows="10" class="cmt-box"
                              style="min-height: 100px;resize: none;scroll-behavior: auto"></textarea>
                    <input type="hidden" name="type" value="comment_chapter">
                    <input type="hidden" name="chapter-id" value="${reqChapter.id}">
                    <div class="d-flex justify-content-end">
                        <button type="submit" class="basic-btn basic-btn--green"
                                style="width: 100px">Gửi
                        </button>
                    </div>
                </form>
            </c:otherwise>
        </c:choose>
        <span id="comment-section-contents" data-limit="${(commentLimit != null)? commentLimit : 10}" data-offset="0"
              data-chapter-id="${reqChapter.id}">
        </span>
        <ul class="d-flex justify-content-end mb-3">
            <li class="page-list__item">
                <a href="#" class="page-list__link w-600" id="previous-comments-button"
                   style="width: 100px">Trước
                </a>
            </li>
            <li class="page-list__item">
                <a href="#" class="page-list__link w-600" id="next-comments-button"
                   style="width: 100px">Sau
                </a>
            </li>
        </ul>
    </div>
</section>

<template id="reply-comment-form-template">
    <form method="post" id="reply-comment-form" class="cmt-form mb-3">
                    <textarea name="content" title="cmt" rows="10" class="cmt-box"
                              style="min-height: 100px;resize: none;scroll-behavior: auto"></textarea>
        <input type="hidden" name="type" value="reply_comment">
        <input type="hidden" name="comment-id">
        <div class="d-flex justify-content-end">
            <button type="submit" class="basic-btn basic-btn--green"
                    style="width: 100px">Gửi
            </button>
        </div>
    </form>
</template>

<script src="/js/comment.js"></script>