<%--@elvariable id="reqRootComments" type="java.util.List<model.Comment>"--%>
<%--@elvariable id="reqChapter" type="model.Chapter"--%>
<%--@elvariable id="user" type="model.User"--%>
<%--@elvariable id="postCommentAllowed" type="boolean"--%>

<%--@elvariable id="HTMLParser" type="core.string_process.HTMLParser.class"--%>
<%@page import="core.string_process.HTMLParser" %>

<%--@elvariable id="RestrictionService" type="service.RestrictionService.class"--%>
<%@page import="service.RestrictionService" %>

<%--@elvariable id="Restriction" type="model.Restriction.class"--%>
<%@page import="model.intermediate.Restriction" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--@elvariable id="postCommentAllowed" type="boolean"--%>
<c:set var="postCommentAllowed"
       value="${(user != null) &&
       (RestrictionService.getUnexpiredRestriction(user.id, Restriction.TYPE_COMMENT) == null)}"
       scope="request"/>

<c:forEach items="${reqRootComments}" var="rootComment">
    <div class="cmt-group">
        <c:set var="reqComment" value="${rootComment}"/>
        <%@include file="comment_item.jsp" %>

        <c:forEach items="${rootComment.replies}" var="replyComment">
            <c:set var="reqComment" value="${replyComment}"/>
            <%@include file="comment_item.jsp" %>
        </c:forEach></div>
</c:forEach>

<!--Comment report modal-->
<div class="modal fade" id="reportCommentModal" tabindex="-1" aria-labelledby="staticBackdropLabel2" aria-hidden="true">
    <div class="modal-dialog modal-dialog-scrollable">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title title title--bold" id="staticBackdropLabel2">Báo cáo bình luận</h5>
                <i class="fas fa-compress-arrows-alt top-right-btn" data-dismiss="modal" aria-label="Close"
                   style="font-size: x-large"></i>
            </div>
            <form action="/mod/bao-cao-binh-luan?action=report_comment" method="post" id="reportCommentForm">
                <div class="modal-body">
                    <b>Lý do</b>
                    <textarea id="reason" name="reason" class="col-12" rows="5" style="padding: 5px"></textarea>
                </div>

                <div class="modal-footer justify-content-center">
                    <button type="button" class="basic-btn basic-btn--red" data-dismiss="modal">Đóng</button>
                    <button type="button" class="basic-btn basic-btn--olive" data-toggle="modal"
                            data-target="#confirmModal">
                        <%--                    onclick="confirmForm()"--%>
                        OK
                    </button>
                    <%--                <input hidden name="action" value="report_comment" type="text">--%>
                    <input name="commentId" hidden id="commentId" type="text">
                    <input name="userId" hidden id="userId" type="text">
                </div>
            </form>
        </div>
    </div>
</div>
<!--Comment report modal 2-->
<div class="modal fade" id="confirmModal" tabindex="-1" aria-labelledby="staticBackdropLabel2" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title title title--bold" id="">Bạn chắc chứ?</h5>
                <i class="fas fa-compress-arrows-alt top-right-btn" data-dismiss="modal" aria-label="Close"
                   style="font-size: x-large"></i>
            </div>
            <div class="modal-body text-center">
                <p>Bạn có muốn báo cáo bình luận này không?</p>
            </div>

            <div class="modal-footer justify-content-center">
                <button type="button" class="basic-btn basic-btn--red" data-dismiss="modal">Đóng</button>
                <button type="button" class="basic-btn basic-btn--olive" id="send"
                        onclick="submitCommentReport()" data-dismiss="modal">
                    OK
                </button>
                <%--                    <input hidden name="action" value="report_comment" type="text">--%>
                <%--                    <input hidden name="commentId" id="comment_id" type="text">--%>

                <%--                    <input name="userId" id="user_id" type="text">--%>
                <%--                    <input name="reasonReport" id="reasonReport" type="text">--%>
            </div>

        </div>
    </div>
</div>
<script>

</script>