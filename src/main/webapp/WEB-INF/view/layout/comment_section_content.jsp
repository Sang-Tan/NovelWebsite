<%--@elvariable id="reqRootComments" type="java.util.List<model.Comment>"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:forEach items="${reqRootComments}" var="rootComment">
    <div class="cmt-group">
        <div class="cmt-group__item">
            <div class="cmt-group__avatar"
                 style="background-image: url('${rootComment.owner.avatar}');">
            </div>
            <div class="cmt-detail">
                <a href="" class="cmt-detail__name">${rootComment.owner.displayName}</a>
                <p>${rootComment.content}</p>
                <div class="cmt-toolkit">
                    <div class="cmt-toolkit__item">
                        <a href="" class="cmt-toolkit__link">Trả lời</a>
                    </div>
                    <div class="cmt-toolkit__item">
                        <a href="" class="cmt-toolkit__link">Báo cáo</a>
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
                    <a href="" class="cmt-detail__name">${replyComment.owner.displayName}</a>
                    <p>${replyComment.content}</p>
                    <div class="cmt-toolkit">
                        <div class="cmt-toolkit__item">
                            <a href="" class="cmt-toolkit__link">Trả lời</a>
                        </div>
                        <div class="cmt-toolkit__item">
                            <a href="" class="cmt-toolkit__link">Báo cáo</a>
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