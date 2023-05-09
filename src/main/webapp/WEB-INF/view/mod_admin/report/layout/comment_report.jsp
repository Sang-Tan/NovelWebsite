<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="commentReport" type="model.Comment"--%>
<%--@elvariable id="commentReportList" type="java.util.List<model.Comment>"--%>
<%--@elvariable id="comment" type="model.Comment"--%>
<%--@elvariable id="reporter" type="model.User"--%>

<%--@elvariable id="HTMLParser" type="core.string_process.HTMLParser"--%>

<%--@elvariable id="NovelManageService" type="service.upload.NovelManageService.class"--%>
<%@page import="service.upload.NovelManageService" %>

<%@page import="core.string_process.HTMLParser" %>
<style>
    .col-05 {
        width: 4.17%;
    }

    .col-115 {

    }
</style>
<div class="row">
    <div class="col-10 col-xl-9 thumb cmt-group">
        <c:choose>
            <c:when test="${commentReportList != null}">
                <c:forEach var="commentReport" items="${commentReportList}">
                    <div class="rpt-group__item">
                        <a href="" class="no-decor" data-toggle="modal" data-target="#report-modal"
                           onclick="reportCommentForm(${commentReport.id}, '${commentReport.owner.displayName}',
                                   '${commentReport.owner.avatar}', '${commentReport.owner.id}', '${HTMLParser.wrapEachLineWithTag(commentReport.content,"p")}')">
                            <div class="rpt-detail container-fluid"
                                 style="background-color: var(--dark-silver); border-left: 1rem solid var(--olive)">
                                <div class="row">
                                    <div class="col-05"></div>
                                    <div class="col-115" style="border-left: 20px">
                                        <span class="title title--bold overflow-elipsis" style="color: black; white-space: nowrap;
                                            display: block; overflow: hidden; overflow-wrap: break-word; max-width: 550px">
                                                ${commentReport.content}
                                        </span>
                                        <p class="overflow-elipsis" style=" color: black">
                                            Người đăng bình luận: ${commentReport.owner.displayName}
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </a>
                    </div>
                    <br>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <h3 class="text-center">Không có báo cáo</h3>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<%@include file="/WEB-INF/view/layout/pagination_footer.jsp" %>

<!--Modal report-->
<div class="modal fade" id="report-modal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-scrollable">
        <div class="modal-content" style="border-radius: 1rem; overflow: hidden">
            <div class="modal-body pl-4 pr-4">
                <h5 class="modal-title title title--bold">
                    Báo cáo bình luận
                    <a href="" id="linkComment" target="_blank" class="ms-auto"
                       style="text-underline: none; color: white">
                        <i class="fas fa-external-link-alt" style="color: black;"></i>
                    </a>
                </h5>
                <i class="fas fa-compress-arrows-alt top-right-btn" data-dismiss="modal" aria-label="Close"
                   style="font-size: x-large"></i>
                <div class="mt-4">
                    <a href="" target="_blank" id="ownerId" style="text-underline: none; color: white">
                        <img id="ownerAvatar" src="/images/default-avatar.jpg" alt="avatar"
                             class="navbar__avatar" style="text-underline: none; color: black">
                        <p id="ownerName" class="d-inline-block ml-2 mt-auto mb-auto"
                           style="font-weight: bold; text-underline: none; color: black">
                            Username
                        </p>
                    </a>
                    <div id="commentContent" class="paragraph-spacing-0 mt-2 ml-2"
                         style="overflow-wrap: break-word">

                    </div>
                </div>
                <hr/>
                <div class="row">
                    <div class="title title--bold col-md-12">
                        <p style="text-align: center">Chi tiết báo cáo</p>
                    </div>
                </div>
                <div class="ms-auto" id="reasons">
                    <p>Lý do báo cáo Lý do báo cáo Lý do báo cáo Lý do báo cáo Lý do báo cáo
                        Lý do báo cáo Lý do báo cáo Lý do báo cáo Lý do báo cáo Lý do báo cáo
                    </p>
                </div>
            </div>
            <div class="">
                <form action="/mod/bao-cao-binh-luan?action=delete" style="margin-block-end: 0">
                    <input id="commentId" name="commentId" hidden>
                    <button type="button" class="basic-btn basic-btn--flat basic-btn--olive col-md-12"
                            data-dismiss="modal"
                            onclick="deleteCommentReport()">
                        <i class="fas fa-check-circle"></i> Đã kiểm duyệt
                    </button>
                </form>
            </div>
        </div>
    </div>
</div>
<script src="/js/comment_report.js"></script>