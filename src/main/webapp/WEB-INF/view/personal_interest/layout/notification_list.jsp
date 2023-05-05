<%--@elvariable id="notifications" type="java.util.List<model.Notification>"--%>

<%@ page import="model.Chapter" %>
<%@ page import="repository.ChapterRepository" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="core.string_process.TimeConverter" %>
<%--@elvariable id="TimeConverter" type="core.string_process.TimeConverter.class"--%>
<body style="background-color: var(--silver);">
<div class="col">
    <c:choose>
        <c:when test="${notifications.size() == 0}">
            <strong>Không có thông báo nào</strong>
        </c:when>
        <c:otherwise>
            <div class="d-flex justify-content-between align-items-center">
                <h4>Thông báo <i class="fas fa-bell ml-1"></i></h4>
                <button class="basic-btn basic-btn--red" onclick="deleteAllNotification()">
                    Xóa tất cả thông báo
                </button>
            </div>
        </c:otherwise>
    </c:choose>
    <div class="mt-2">
        <c:forEach items="${notifications}" var="notification">
            <article class="notif-item" data-id="${notification.id}">
                <a class="notif-item__link-wrap" href="${(notification.link != null) ? notification.link : "#" }"></a>
                <div>
                    <p class="notif-item__title">
                            ${notification.content}
                    </p>
                    <time class="notif-item__time" style="display: inline">
                            ${TimeConverter.convertToTimeAgo(notification.timePushNotif)}
                    </time>
                    <a class="seen-btn notif-item__link-inside" data-notification-id="${notification.id}"
                       data-action="seen"
                       onclick="deleteNotification([this])"
                       style="float: right;  color: black;">
                        <p><i class="fas fa-trash mr-1"></i>Xóa thông báo</p>
                    </a>
                </div>


            </article>
        </c:forEach>
    </div>
</div>

<script src="/js/personal_interest.js"></script>
<script src="/js/notification_manage.js"></script>
</body>
</html>

