<%--@elvariable id="reqRootComments" type="java.util.List<model.Comment>"--%>
<%@page import="core.string_process.TimeConverter" %>
<%--@elvariable id="TimeConverter" type="core.string_process.TimeConverter.class"--%>

<%@page import="core.string_process.HTMLParser" %>
<%--@elvariable id="HTMLParser" type="core.string_process.HTMLParser.class"--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:forEach items="${reqRootComments}" var="rootComment">
    <div class="cmt-group">
        <div class="cmt-group__item">
            <div class="cmt-group__avatar"
                 style="background-image: url('${rootComment.owner.avatar}');">
            </div>
            <div class="cmt-detail">
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
                           onclick="showReportChapterForm(${rootComment.id}, '${rootComment.owner.id}')">
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
                <div class="cmt-detail">
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
                               onclick="showReportChapterForm(${replyComment.id}, '${replyComment.owner.id}')">
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

<!--Modal reject novel-->
<div class="modal fade" id="reportCommentModal" tabindex="-1" aria-labelledby="staticBackdropLabel2" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title title title--bold" id="staticBackdropLabel2">Báo cáo bình luận</h5>
                <i class="fas fa-compress-arrows-alt top-right-btn" data-dismiss="modal" aria-label="Close"
                   style="font-size: x-large"></i>
            </div>
            <div class="modal-body">
                Bạn có muốn từ chối <span class="text-success nameNovel"></span><span> không?</span>
            </div>
            <form action="#" method="get">
                <div class="modal-footer justify-content-center">
                    <label>
                        <textarea></textarea>
                    </label>
                    <button type="button" class="basic-btn basic-btn--red" data-dismiss="modal">Đóng</button>
                    <button type="button" class="basic-btn basic-btn--olive" data-toggle="modal" data-target="#confirmModal">
                        OK
                    </button>
                    <input hidden name="action" value="reject" type="text">
                    <input hidden name="idNovel" type="text" class="idNovel">
                </div>
            </form>
        </div>
    </div>
</div>
<!--Modal reject novel-->
<div class="modal fade" id="confirmModal" tabindex="-1" aria-labelledby="staticBackdropLabel2" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title title title--bold" id="">Bạn chắc chứ?</h5>
                <i class="fas fa-compress-arrows-alt top-right-btn" data-dismiss="modal" aria-label="Close"
                   style="font-size: x-large"></i>
            </div>
            <div class="modal-body">
                Bạn có muốn từ chối <span class="text-success nameNovel"></span><span> không?</span>
            </div>
            <form action="#" method="get">
                <div class="modal-footer justify-content-center">
                    <button type="button" class="basic-btn basic-btn--red" data-dismiss="modal">Đóng</button>
                    <button class="basic-btn basic-btn--olive" data-toggle="modal" data-target="#confirmModal">
                        OK
                    </button>
                    <input hidden name="action" value="reject" type="text">
                    <input hidden name="idNovel" type="text" class="idNovel">
                </div>
            </form>
        </div>
    </div>
</div>