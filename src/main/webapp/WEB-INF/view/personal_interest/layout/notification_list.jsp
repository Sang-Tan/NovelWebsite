<%--@elvariable id="notifications" type="java.util.List<model.Notification>"--%>
<%@ page import="model.Chapter" %>
<%@ page import="repository.ChapterRepository" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<i class="bi bi-link"></i>--%>
<%--<div class="container">--%>
<div class="header" style="margin-bottom: 40px">
    <h4 style="display: inline">Thông báo</h4>
    <button class="btn btn-primary" style="float: right" onclick="deleteAllNotification()">Xóa tất cả thông báo</button>
</div>
<c:if test="${notifications.size() == 0}">
    <strong>Không có thông báo nào</strong>
</c:if>
<c:forEach items="${notifications}" var="notification">
    <div class="bg-white border" style="border-radius: 10px;margin-bottom: 10px;margin-top: 10px; padding: 10px">
        <c:choose>
            <c:when test="${notification.link != null}">
                <a href="${notification.link}">
                    <p class="" style="width: 800px; color: black">
                            ${notification.content}
                    </p>
                </a>
            </c:when>
            <c:otherwise>
                <p class="" style="width: 800px; color: black">
                        ${notification.content}
                </p>
            </c:otherwise>
        </c:choose>

        <p class="" style="display: inline">
                ${notification.timePushNotif}
        </p>
        <a data-notification-id="${notification.id}" data-action="seen" onclick="deleteThisNotification(this)"
           style="float: right;  color: black" href="">
            <p>Đã xem</p>
        </a>
    </div>
</c:forEach>

<script src="/js/personal_interest.js"></script>
<script src="/js/notification_handler.js"></script>
