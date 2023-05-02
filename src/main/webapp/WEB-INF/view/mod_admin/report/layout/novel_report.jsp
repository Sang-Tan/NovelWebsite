<%--
  Created by IntelliJ IDEA.
  User: ACER
  Date: 16/04/2023
  Time: 2:30 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="reporter" type="model.User"--%>
<%--@elvariable id="novel" type="model.Novel"--%>
<%--@elvariable id="novelReport" type="model.NovelReport"--%>
<%--@elvariable id="novelReportList" type="java.util.List<model.NovelReport>"--%>
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
            <c:when test="${novelReportList != null}">
                <c:forEach var="novelReport" items="${novelReportList}">
                    <div class="rpt-group__item">
                        <a href="" class="no-decor" data-toggle="modal" data-target="#report-modal"
                           onclick="reportNovelForm(${novelReport.novel.id}, '${novelReport.novel.owner.displayName}',
                                   '${novelReport.novel.owner.avatar}', ${novelReport.novel.owner.id},
                                   '${novelReport.novel.name}', '${novelReport.novel.image}', '${novelReport.novel.summary}')">
                            <div class="rpt-detail" style="background-color: var(--dark-silver); border-left: 1rem solid var(--olive)">
                                <div class="row">
                                    <div class="col-05"></div>
                                    <div class="col-115" style="border-left: 20px">
                                        <span class="title title--bold overflow-elipsis" style="color: black; white-space: nowrap;
                                            display: block; overflow: hidden; overflow-wrap: break-word; max-width: 550px">
                                                ${novelReport.novel.name}
                                        </span>
                                        <p class="overflow-elipsis" style="color: black">
                                            Người đăng truyện: ${novelReport.novel.owner.displayName}
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
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="col-md-12 modal-title title title--bold" id="exampleModalLabel">Báo cáo</h5>
                <i class="fas fa-compress-arrows-alt top-right-btn" data-dismiss="modal" aria-label="Close"
                   style="font-size: x-large"></i>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <div class="row">
                        <a href="" id="linkNovel" target="_blank" class="ms-auto col-md-12 title title--small text-center"
                           style="text-underline: none; color: white">
                            <p id="novelName" style="font-weight: bold; text-underline: none; color: black">
                                Chuyển sinh sang thế giới khác và tôi bị gọi là...
                            </p>
                            <img src="" id="image"  style="height: 200px;" alt="Bìa truyện">
                        </a>
                    </div>

                    <div class="row">
                        <div class="col-md-12">
                            <a href="" target="_blank" id="ownerId1" style="text-underline: none; color: white">
                                <img id="ownerAvatar1" src="/images/default-avatar.jpg" alt="avatar"
                                     class="navbar__avatar" style="text-underline: none; color: black">
                                <p id="ownerName1" class="d-inline-block ml-2 mt-auto mb-auto"
                                   style="font-weight: bold; text-underline: none; color: black">
                                    Username
                                </p>
                            </a>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12">
                            <p class="title title--small" style="font-weight: bold; text-underline: none; color: black">Tóm tắt</p>
                            <p id="summary" style="overflow-wrap: break-word">Chi tiết tóm tắt truyện</p>
                        </div>
                    </div>

                    <hr>
                    <div class="row">
                        <div class="title title--bold col-md-12">
                            <p style="text-align: center">Chi tiết báo cáo</p>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 ms-auto" id="reasons1">
                            <p>Lý do báo cáo Lý do báo cáo Lý do báo cáo Lý do báo cáo Lý do báo cáo
                                Lý do báo cáo Lý do báo cáo Lý do báo cáo Lý do báo cáo Lý do báo cáo
                            </p>
                        </div>
                    </div>
                </div>
            </div>
            <div class="">
                <form action="/mod/bao-cao-truyen?action=checked" style="margin-block-end: 0">
                    <input id="novelId" name="novelId" hidden>
                    <button type="button" class="btn basic-btn--olive col-md-12" data-dismiss="modal"
                            onclick="novelReportChecked()">
                        <i class="fas fa-check-circle"></i> Đã đọc
                    </button>
                </form>
            </div>
        </div>
    </div>
</div>
<script src="/js/novel_report.js"></script>