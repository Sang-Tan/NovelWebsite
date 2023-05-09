<%--@elvariable id="novelChapterList" type="java.util.List<core.Pair<model.Novel,java.util.List<model.Chapter>>>"--%>
<%@page import="model.Novel" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="basic-section">
    <div class="basic-section__header">
        <h4>Chương đã đánh dấu</h4>
    </div>
    <div>

        <c:forEach items="${novelChapterList}" var="novelChapterPair">
            <div class="border-bottom">
                <div class="d-flex d-row align-items-center justify-content-start m-3">
                    <div class="a6-ratio" style="width: 40px">
                        <div class="img-wrapper" style="background-image: url('${novelChapterPair.key.image}')">
                        </div>
                    </div>
                    <div style="flex-grow: 1">
                        <a href="/truyen/${novelChapterPair.key.id}"
                           class="theme-link w-600 ml-2">${novelChapterPair.key.name}</a>
                        <a role="button" href="#collapse${novelChapterPair.key.id}" data-toggle="collapse"
                           class="float-right theme-link">
                            <i class="fas fa-chevron-down "></i>
                        </a>
                    </div>
                </div>
                <div class="collapse" id="collapse${novelChapterPair.key.id}">
                    <table class="table ">
                        <c:forEach items="${novelChapterPair.value}" var="chapter">
                            <tr>
                                <td>
                                    <a href="/doc-tieu-thuyet/${novelChapterPair.key.id}/${chapter.id}"
                                       class="theme-link">${chapter.name}</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
