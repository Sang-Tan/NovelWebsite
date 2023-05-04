<%--@elvariable id="notifications" type="java.util.List<model.Notification>"--%>

<%@ page import="model.Chapter" %>
<%@ page import="repository.ChapterRepository" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="core.string_process.TimeConverter" %>
<%--@elvariable id="TimeConverter" type="core.string_process.TimeConverter.class"--%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Chapter name</title>
    <%@ include file="../../layout/basic_stylesheet.jsp" %>
    <link rel="stylesheet" type="text/css" href="/css/notification.css">
</head>

<body style="background-color: var(--silver);">
<div class="col">
    <div class="basic-section" style=" padding: 20px 10px 0px 10px; min-height: 80px">

        <c:choose>
            <c:when test="${notifications.size() == 0}">
                <strong>Không có thông báo nào</strong>
            </c:when>
            <c:otherwise>
                <button class="btn btn-primary" style="margin: auto" onclick="deleteAllNotification()">
                    Đánh dấu đã xem tất cả thông báo
                </button>
            </c:otherwise>
        </c:choose>
        <c:forEach items="${notifications}" var="notification">
            <article class="bg-white border noti-item"
                     style="border-radius: 10px;margin-bottom: 10px;margin-top: 10px; padding: 10px">
                <c:choose>
                    <c:when test="${notification.link != null}">
                        <a class="noti-url noti-info" href="${notification.link}">
                            <p class="noti-content" style="width: 700px">
                                    ${notification.content}
                            </p>
                            <time class="time-ago block" style="display: inline">
                                    ${TimeConverter.convertToTimeAgo(notification.timePushNotif)}
                            </time>
                        </a>
                    </c:when>
                    <c:otherwise>
                        <p class="noti-content" style="width: 700px; color: black">
                                ${notification.content}
                        </p>
                        <time class="time-ago block" style="display: inline; color: black">
                                ${TimeConverter.convertToTimeAgo(notification.timePushNotif)}
                        </time>
                    </c:otherwise>
                </c:choose>
                <a class="seen-btn" data-notification-id="${notification.id}" data-action="seen"
                   onclick="deleteThisNotification(this)"
                   style="float: right;  color: black" href="">
                    <p>Đánh dấu đã xem</p>
                </a>
            </article>
        </c:forEach>

    </div>

</div>

<script src="/js/personal_interest.js"></script>
<script src="/js/notification_manage.js"></script>
</body>
</html>

