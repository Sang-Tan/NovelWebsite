<%--@elvariable id="user" type="model.User"--%>
<%--@elvariable id="userInWall" type="model.User"--%>
<%--@elvariable id="userNovels" type="java.util.Collection<model.Novel>"--%>
<%--@elvariable id="User" type="model.User.class"--%>
<%@page import="model.User" %>
<%--@elvariable id="Restriction" type="model.Restriction.class"--%>
<%@page import="model.intermediate.Restriction" %>
<%--@elvariable id="RestrictionService" type="service.RestrictionService.class"--%>
<%@page import="service.RestrictionService" %>
<%--@elvariable id="TimeConverter" type="core.string_process.TimeConverter.class"--%>
<%@page import="core.string_process.TimeConverter" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${userInWall.displayName}</title>
    <%@include file="layout/basic_stylesheet.jsp" %>
    <link href="/css/user_wall.css" rel="stylesheet">
</head>
<body style="background-color: var(--silver)">
<%@include file="layout/header_main.jsp" %>
<div class="container mt-4">
    <div class="row">
        <div class="col-12 col-lg-3">
            <div class="basic-section mt-2" style="background-color: var(--dark-silver);">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-3 col-lg-5">
                            <div class="mt-2 mb-2">
                                <div class="square-ratio border-radius-50">
                                    <div class="img-wrapper"
                                         style="background-image: url('${userInWall.avatar}')"></div>
                                </div>
                            </div>
                        </div>
                        <div class="col-7 pl-0">
                            <div class="d-flex flex-column justify-content-center h-100">
                                <div class="text-center">
                                    <h6 title="${userInWall.displayName}"
                                        class="overflow-elipsis overflow-elipsis--wrap w-700">${userInWall.displayName}</h6>
                                </div>
                                <div class="pr-3 pl-3">
                                    <c:choose>
                                        <c:when test="${userInWall.role == User.ROLE_MEMBER}">
                                            <div class="role-box role-box--member">
                                                Member
                                            </div>
                                        </c:when>
                                        <c:when test="${userInWall.role == User.ROLE_ADMIN}">
                                            <div class="role-box role-box--admin">
                                                Admin
                                            </div>
                                        </c:when>
                                        <c:when test="${userInWall.role == User.ROLE_MODERATOR}">
                                            <div class="role-box role-box--moderator">
                                                Moderator
                                            </div>
                                        </c:when>
                                    </c:choose>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
            <c:if test="${user != null && user.role.equals(User.ROLE_MODERATOR)}">
                <%--@elvariable id="commentRestriction" type="model.intermediate.Restriction"--%>
                <c:set var="commentRestriction"
                       value="${RestrictionService.getUnexpiredRestriction(user.id,Restriction.TYPE_COMMENT)}"/>

                <%--@elvariable id="novelRestriction" type="model.intermediate.Restriction"--%>
                <c:set var="novelRestriction"
                       value="${RestrictionService.getUnexpiredRestriction(user.id,Restriction.TYPE_NOVEL)}"/>

                <div class="d-flex align-items-center flex-column">
                    <c:choose>
                        <c:when test="${commentRestriction == null}">
                            <button id="add-restriction-comment-btn" data-restriction="comment"
                                    data-user-id="${user.id}" data-toggle="modal"
                                    data-target="#add-restriction-modal"
                                    class="basic-btn basic-btn--red mb-3">
                                Cấm đăng bình luận
                            </button>
                        </c:when>
                        <c:otherwise>
                            <div class="basic-section">
                                <button id="remove-restriction-comment-btn" data-user-id="${user.id}"
                                        data-restriction="comment"
                                        class="basic-btn basic-btn--blue mb-3}"
                                        data-toggle="modal" data-target="#remove-restriction-modal">
                                    Gỡ lệnh cấm bình luận
                                </button>
                            </div>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${novelRestriction == null}">
                            <button id="add-restriction-novel-btn" data-restriction="novel"
                                    data-user-id="${user.id}" data-toggle="modal" data-target="#add-restriction-modal"
                                    class="basic-btn basic-btn--red mb-3">
                                Cấm đăng truyện
                            </button>
                        </c:when>
                        <c:otherwise>
                            <button id="remove-restriction-novel-btn" data-toggle="modal"
                                    data-target="#remove-restriction-modal"
                                    data-user-id="${user.id}" data-restriction="novel"
                                    class="basic-btn basic-btn--blue mb-3 ">
                                Gỡ lệnh cấm đăng truyện
                            </button>
                        </c:otherwise>
                    </c:choose>
                </div>
                <%--Add restriction modal--%>
                <div class="modal" tabindex="-1" role="dialog" id="add-restriction-modal" aria-labelledby="...">
                    <div class="modal-dialog modal-dialog-centered" role="document">
                        <form class="pt-3 pb-4 pl-4 pr-4 modal-content rounded-form" id="add-restriction-form">
                            <h3 class="text-center w-700" id="restriction-modal-header"></h3>
                            <i class="fas fa-compress-arrows-alt top-right-btn" data-dismiss="modal" aria-label="Close"
                               style="font-size: x-large"></i>
                            <div class="container mb-4">
                                <div class="row mb-3">
                                    <label class="col-12 pl-0 mb-1">Thời gian cấm</label>
                                    <select name="restrict-time" required>
                                        <option value="86400000">1 ngày</option>
                                        <option value="259200000">3 ngày</option>
                                        <option value="604800000">1 tuần</option>
                                        <option value="1209600000">2 tuần</option>
                                        <option value="2592000000">1 tháng</option>
                                        <option value="7776000000">3 tháng</option>
                                        <option value="31536000000">1 năm</option>
                                    </select>
                                </div>
                                <div class="row mb-3">
                                    <label class="col-12 pl-0 mb-1">Lý do cấm</label>
                                    <input class="col input-text" type="text" name="reason"
                                           placeholder="Lý do cấm" required>
                                </div>
                            </div>
                            <input type="hidden" name="action" value="add_restriction">
                            <input type="hidden" name="restriction-type">
                            <input type="hidden" name="user-id" value="${user.id}">
                            <div class="d-flex justify-content-center mb-2">
                                <button class="pl-3 pr-3 mr-3 basic-btn basic-btn--gray" data-dismiss="modal">Đóng
                                </button>
                                <button type="submit" class="pl-3 pr-3 basic-btn basic-btn--olive">
                                    Xác nhận
                                </button>
                            </div>
                        </form>
                    </div>
                </div>

                <%--Remove restriction modal--%>
                <div class="modal" tabindex="-1" role="dialog" id="remove-restriction-modal" aria-labelledby="...">
                    <div class="modal-dialog modal-dialog-centered" role="document">
                        <div class="pt-3 pb-4 pl-4 pr-4 modal-content rounded-form">
                            <h3 class="text-center w-700" id="remove-restriction-modal-header"></h3>
                            <i class="fas fa-compress-arrows-alt top-right-btn" data-dismiss="modal" aria-label="Close"
                               style="font-size: x-large"></i>
                            <p data-name="reason">Lí do cấm</p>
                            <p data-name="due-time">Thời gian cấm</p>
                            <div class="d-flex justify-content-center mb-2">
                                <button class="pl-3 pr-3 mr-3 basic-btn basic-btn--gray" data-dismiss="modal">Đóng
                                </button>
                                <button data-name="submit-btn" class="pl-3 pr-3 basic-btn basic-btn--olive">
                                    Xác nhận
                                </button>
                            </div>
                        </div>
                    </div>
                </div>

            </c:if>
        </div>
        <div class="col ml-4">
            <header class="mb-3">
                <span class="title title--bold">Truyện đã đăng</span>
            </header>
            <div class="row pl-1">
                <c:forEach items="${userNovels}" var="userNovel">
                    <div class="col-3 col-xl-2 thumb">
                        <a href="/truyen/${userNovel.id}" class="no-decor">
                            <div class="thumb__wrapper">
                                <div class="thumb__img-panel shadow a6-ratio">
                                    <div class="img-wrapper img-wrapper--shadow"
                                         style="background-image: url('${userNovel.image}');">
                                    </div>
                                </div>
                                <p class="thumb__caption">${userNovel.name}</p>
                            </div>
                        </a>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
    <%@include file="layout/basic_js.jsp" %>
    <script src="/js/restriction.js"></script>
</body>
</html>
