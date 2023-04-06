<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="pagination_wrap">

    <%--@elvariable id="paginator" type="service.Pagination.Paginator"--%>
    <nav class="mt-1">
        <ul class="d-flex justify-content-end">
            <%--@elvariable id="pageItems" type="java.util.HashSet<service.Pagination.PageItem>"--%>
            <c:forEach items="${pageItems}" var="pageItem">
                <li class="page-list__item <c:if
                        test="${pageItem.active}"> active </c:if>
                        <c:if test="${pageItem.disabled}"> disabled</c:if>">
                    <a href="${pageItem.url}" class="page-list__link ">${pageItem.text}</a>
                </li>
            </c:forEach>
        </ul>
    </nav>

</div>