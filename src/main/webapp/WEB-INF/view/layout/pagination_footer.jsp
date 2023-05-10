<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="pagination_wrap">

    <%--@elvariable id="paginator" type="core.pagination.Paginator"--%>
    <nav class="mt-1">
        <ul class="d-flex justify-content-end">
            <%--@elvariable id="pageItems" type="java.util.HashSet<core.pagination.PageItem>"--%>
            <c:forEach items="${pageItems}" var="pageItem">
                <li style="cursor: pointer;" class="page-list__item <c:if
                        test="${pageItem.active}"> active </c:if>
                        <c:if test="${pageItem.disabled}"> disabled</c:if>">
                    <a data-page="${pageItem.page}" class="page-list__link ">${pageItem.text}</a>
                </li>
            </c:forEach>
        </ul>
    </nav>

</div>
<script>
    function initPagination() {
        const queryParams = new URLSearchParams(window.location.search);
        const pageLinks = document.querySelectorAll('a[data-page]');
        for (const pageLink of pageLinks) {
            pageLink.addEventListener('click', function (event) {
                event.preventDefault();
                const page = pageLink.dataset.page;
                queryParams.set('page', page);
                window.location.search = queryParams.toString();
            });
        }
    }
    initPagination();
</script>