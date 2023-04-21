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
<div class="row">
    <div class="col-10 col-xl-9 thumb cmt-group">
        <c:forEach var="novelReport" items="${novelReportList}">
            <div class="rpt-group__item">
                <a href="#report-modal" class="no-decor" data-toggle="modal" data-target="#report-modal">
                    <div class="rpt-detail"
                         style="background-color: var(--dark-silver)">
                        <div class="row">
                            <div class="col-05">
                                <button class="btn basic-btn--olive h-100"></button>
                            </div>
                            <div class="col-115">
                    <span class="title title--bold" style="margin-left: 20px; color: black">
                            ${novelReport.novel.name}
                    </span>
                                <p style="margin-left: 20px; color: black">
                                    Người gửi: ${novelReport.reporter.displayName}
                                </p>
                            </div>
                        </div>
                    </div>
                </a>
            </div>
            <br>
        </c:forEach>
        <c:if test="${novelReportList == null}">
            <h3 class="text-center">Không có báo cáo</h3>
        </c:if>
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
                        <p id="showNovelDetail" class="ms-auto col-md-12">Chuyển sinh sang thế giới khác và tôi bị
                            gọi là...</p>
                    </div>

                    <div class="row">
                        <div class="col-md-12">
                            <img id="showAvatarDetail" src="/images/default-avatar.jpg" alt="avatar"
                                 class="navbar__avatar">
                            <p id="showUsernameDetail" class="d-inline-block ml-2 mt-auto mb-auto"
                               style="font-weight: bold">Username</p>
                        </div>
                    </div>
                    <hr>
                    <div class="row">
                        <div class="title title--bold col-md-12">
                            <p style="text-align: center">Chi tiết báo cáo</p>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 ms-auto" id="showReasonDetail">
                            <p>Lý do báo cáo Lý do báo cáo Lý do báo cáo Lý do báo cáo Lý do báo cáo
                                Lý do báo cáo Lý do báo cáo Lý do báo cáo Lý do báo cáo Lý do báo cáo
                            </p>
                        </div>
                    </div>

                </div>
            </div>
            <div class="">
                <button type="button" class="btn basic-btn--olive col-md-12" data-dismiss="modal">Đóng</button>
            </div>
        </div>
    </div>
</div>
