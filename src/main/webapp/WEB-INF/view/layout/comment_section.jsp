<%--@elvariable id="user" type="model.User"--%>
<%--@elvariable id="reqChapter" type="model.Chapter"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

            <c:otherwise>
                <form id="root-comment-form" method="post" class="mb-3">
                    <textarea name="content" title="cmt" id="" rows="10" class="cmt-box"
                              style="min-height: 100px;resize: none;scroll-behavior: auto"></textarea>
                    <input type="hidden" name="type" value="comment-chapter">
                    <input type="hidden" name="chapter_id" value="${reqChapter.id}">
                    <div class="d-flex justify-content-end">
                        <button type="submit" class="basic-btn basic-btn--green"
                                style="width: 100px">Gửi
                        </button>
                    </div>
                </form>
            </c:otherwise>
        </c:choose>
        <%@include file="comment_section_content.jsp" %>
    </div>
</section>

<script src="/js/comment.js"></script>