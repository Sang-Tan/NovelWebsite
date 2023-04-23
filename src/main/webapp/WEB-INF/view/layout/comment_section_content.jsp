<%--@elvariable id="reqRootComments" type="java.util.List<model.Comment>"--%>
<%--@elvariable id="user" type="model.User"--%>
<%@page import="core.string_process.TimeConverter" %>
<%--@elvariable id="TimeConverter" type="core.string_process.TimeConverter.class"--%>

<%@page import="core.string_process.HTMLParser" %>
<%--@elvariable id="HTMLParser" type="core.string_process.HTMLParser.class"--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="/css/novel_detail.css">
<style>
    .modal:nth-of-type(even) {
        z-index: 1062 !important;
    }
    .modal-backdrop.show:nth-of-type(even) {
        z-index: 1061 !important;
    }
</style>
<c:forEach items="${reqRootComments}" var="rootComment">
    <div class="cmt-group">
        <div class="cmt-group__item">
            <div class="cmt-group__avatar"
                 style="background-image: url('${rootComment.owner.avatar}');">
            </div>
            <div class="cmt-detail" style="display: block; hyphens: auto; overflow: hidden; overflow-wrap: break-word;">
                <a href="/thanh-vien/${rootComment.owner.id}"
                   class="cmt-detail__name">${rootComment.owner.displayName}</a>
                <time title="${TimeConverter.convertToVietnameseTime(rootComment.commentTime)}"
                      class="cmt-time">${TimeConverter.convertToTimeAgo(rootComment.commentTime)}</time>
                    ${HTMLParser.wrapEachLineWithTag(rootComment.content, "p")}
                <div class="cmt-toolkit">
                    <div class="cmt-toolkit__item">
                        <a href="#" data-reply-to="${rootComment.id}"
                           class="cmt-toolkit__link">Trả lời</a>
                    </div>
                    <div class="cmt-toolkit__item">
                        <a href="" class="cmt-toolkit__link" data-toggle="modal" data-target="#reportCommentModal"
                           onclick="showReportCommentForm(${rootComment.id}, ${user.id})">
                            Báo cáo
                        </a>
                    </div>
                    <div class="cmt-toolkit__item">
                        <a href="" class="cmt-toolkit__link">
                            <i class="fas fa-thumbs-up"></i>
                            <span>Thích</span>
                        </a>
                    </div>
                </div>
            </div>
        </div>
        <c:forEach items="${rootComment.replies}" var="replyComment">
            <div class="cmt-group__item">
                <div class="cmt-group__avatar"
                     style="background-image: url('${replyComment.owner.avatar}');">
                </div>
                <div class="cmt-detail"
                     style="display: block; hyphens: auto; overflow: hidden; overflow-wrap: break-word;">
                    <a href="/thanh-vien/${replyComment.owner.id}"
                       class="cmt-detail__name">${replyComment.owner.displayName}</a>
                    <time title="${TimeConverter.convertToVietnameseTime(replyComment.commentTime)}"
                          class="cmt-time">${TimeConverter.convertToTimeAgo(replyComment.commentTime)}</time>
                        ${HTMLParser.wrapEachLineWithTag(replyComment.content, "p")}
                    <div class="cmt-toolkit">
                        <div class="cmt-toolkit__item">
                            <a href="#" data-reply-to="${rootComment.id}" class="cmt-toolkit__link">Trả lời</a>
                        </div>
                        <div class="cmt-toolkit__item">
                            <a href="" class="cmt-toolkit__link" data-toggle="modal" data-target="#reportCommentModal"
                               onclick="showReportCommentForm(${replyComment.id}, ${user.id})">
                                Báo cáo
                            </a>
                        </div>
                        <div class="cmt-toolkit__item">
                            <a href="" class="cmt-toolkit__link">
                                <i class="fas fa-thumbs-up"></i>
                                <span>Thích</span>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
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
                        data-target="#confirmModal" >
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