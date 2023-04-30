<%--@elvariable id="notifications" type="java.util.List<model.Notification>"--%>
<%@ page import="model.Chapter" %>
<%@ page import="repository.ChapterRepository" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="basic-section">
    <div class="basic-section__header" style="margin-bottom: 0px">
        <h4>Thông báo</h4>
    </div>
    <div>
        <div class="border-bottom">
            <table class="table ">
                <c:forEach items="${notifications}" var="notification">
                    <tr>
                        <td>
                            <div class="d-flex">
                                <div class="ml-3">
                                    <a href="${notification.link}" class="theme-link  w-600"
                                    >${notification.content}</a>
                                </div>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>

<script src="/js/personal_interest.js"></script>