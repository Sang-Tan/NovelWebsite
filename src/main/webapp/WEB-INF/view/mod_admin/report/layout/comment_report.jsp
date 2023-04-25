<%--
  Created by IntelliJ IDEA.
  User: ACER
  Date: 16/04/2023
  Time: 2:24 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="commentReport" type="model.CommentReport"--%>
<%--@elvariable id="comment" type="model.Comment"--%>
<%--@elvariable id="reporter" type="model.User"--%>
<style>
    .col-05 {
        width: 4.17%;
    }

    .col-115 {

    }
</style>
<div class="row">
    <div class="col-10 col-xl-9 thumb cmt-group">
        <c:forEach var="commentReport" items="${commentReportList}">
            <div class="rpt-group__item">
                <a href="#report-modal" class="no-decor" data-toggle="modal" data-target="#report-modal"
                   onclick="reportCommentForm(${commentReport.comment.id}, '${commentReport.comment.owner.displayName}',
                           '${commentReport.comment.owner.avatar}', '${commentReport.comment.owner.id}', '${commentReport.comment.content}')">
                    <div class="rpt-detail container-fluid"
                         style="background-color: var(--dark-silver); border-left: 1rem solid var(--olive)">
                        <div class="row">
                            <div class="col-05"></div>

                            <div class="col-115" style="border-left: 20px">
                    <span class="title title--bold overflow-elipsis" style="color: black; white-space: nowrap;
                     display: block; overflow: hidden; overflow-wrap: break-word; max-width: 550px">
                            ${commentReport.comment.content}
                    </span>
                                <p class="overflow-elipsis" style=" color: black">
                                    Người đăng bình luận: ${commentReport.comment.owner.displayName}
                                </p>
                            </div>
                        </div>
                    </div>
                </a>
            </div>
            <br>
            <c:if test="${commentReportList == null}">
                <h3 class="text-center">Không có báo cáo</h3>
            </c:if>
        </c:forEach>
    </div>
</div>
<%@include file="/WEB-INF/view/layout/pagination_footer.jsp" %>

<!--Modal report-->
<div class="modal fade" id="report-modal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-scrollable">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="col-md-12 modal-title title title--bold" id="exampleModalLabel">Báo cáo bình luận</h5>
                <i class="fas fa-compress-arrows-alt top-right-btn" data-dismiss="modal" aria-label="Close"
                   style="font-size: x-large"></i>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-md-12">
                            <a href="" id="ownerId" style="text-underline: none; color: white">
                                <img id="ownerAvatar" src="/images/default-avatar.jpg" alt="avatar"
                                     class="navbar__avatar" style="text-underline: none; color: black">
                                <p id="ownerName" class="d-inline-block ml-2 mt-auto mb-auto"
                                   style="font-weight: bold; text-underline: none; color: black">
                                    Username
                                </p>
                            </a>
                        </div>
                    </div>

                    <div class="row d-block">
                        <p id="commentContent" class="ms-auto col-md-12" style="overflow-wrap: break-word">
                            Chuyển sinh sang thế giới khác và tôi bị gọi là...
                        </p>
                    </div>
                    <hr>
                    <div class="row">
                        <div class="title title--bold col-md-12">
                            <p style="text-align: center">Chi tiết báo cáo</p>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 ms-auto" id="reasons">
                            <p>Lý do báo cáo Lý do báo cáo Lý do báo cáo Lý do báo cáo Lý do báo cáo
                                Lý do báo cáo Lý do báo cáo Lý do báo cáo Lý do báo cáo Lý do báo cáo
                            </p>
                        </div>
                    </div>

                </div>
            </div>
            <input id="commentId" name="commentId" hidden>
            <div class="">
                <button type="button" class="btn basic-btn--olive col-md-12" data-dismiss="modal"><i
                        class="fas fa-check-circle"></i>
                </button>
            </div>
        </div>
    </div>
</div>

<script src="/js/comment_report.js">


</script>