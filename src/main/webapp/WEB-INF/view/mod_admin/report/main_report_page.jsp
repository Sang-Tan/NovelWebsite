<%--
  Created by IntelliJ IDEA.
  User: ACER
  Date: 01/04/2023
  Time: 9:36 SA
  To change this template use File | Settings | File Templates.
--%>
<%--@elvariable id="selection" type="core.metadata.ReportSelection"--%>
<%--@elvariable id="ReportSelection" type="core.metadata.ReportSelection.class"--%>
<%--@elvariable id="user" type="model.User"--%>

<%@page import="core.metadata.ReportSelection" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>
        <c:choose>
            <c:when test="${selection.equals(ReportSelection.COMMENT_REPORT)}">
                Báo cáo bình luận
            </c:when>

            <c:when test="${selection.equals(ReportSelection.NOVEL_REPORT)}">
                Báo cáo truyện
            </c:when>
        </c:choose>
    </title>
    <%@include file="/WEB-INF/view/layout/basic_stylesheet.jsp" %>
</head>
<body style="background-color: var(--silver)">
<%@include file="../../layout/header_mod.jsp" %>
<link rel="stylesheet" href="/css/main.css">
<main class="mt-2">
    <div class="container">
        <div class="row">
            <div class="col-12 col-lg-9">
                <header class="mb-3">
                    <span class="title title--underline title--bold">Danh sách báo cáo</span>
                </header>
                <div class="row">
                    <div class="col-10 col-xl-9 thumb cmt-group">
                        <c:choose>
                            <c:when test="${selection.equals(ReportSelection.COMMENT_REPORT)}">
                                <%@include file="layout/comment_report.jsp"%>
                            </c:when>

                            <c:when test="${selection.equals(ReportSelection.NOVEL_REPORT)}">
                                <%@include file="layout/novel_report.jsp"%>
                            </c:when>
                        </c:choose>

                    </div>
                </div>

                <nav class="mt-1">
                    <ul class="d-flex justify-content-end">
                        <li class="page-list__item">
                            <a href="#" class="page-list__link">Đầu</a>
                        </li>
                        <li class="page-list__item">
                            <a href="#" class="page-list__link">1</a>
                        </li>
                        <li class="page-list__item active">
                            <a href="#" class="page-list__link">2</a>
                        </li>
                        <li class="page-list__item">
                            <a href="#" class="page-list__link">3</a>
                        </li>
                        <li class="page-list__item">
                            <a href="#" class="page-list__link">4</a>
                        </li>
                        <li class="page-list__item">
                            <a href="#" class="page-list__link">5</a>
                        </li>
                        <li class="page-list__item">
                            <a href="#" class="page-list__link">Cuối</a>
                        </li>
                    </ul>
                </nav>
            </div>
            <div class="col col-lg-3">
                <table class="table table-hover mb-0 basic-section">
                    <tbody>
                    <tr>
                        <td class="p-0">
                            <a class="theme-link link-fill p-3 ${selection.equals(ReportSelection.COMMENT_REPORT) ? "left-highlight": ""}"
                               href="/mod/bao-cao-binh-luan">
                                Bình luận
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td class="p-0">
                            <a class="theme-link link-fill p-3 ${selection.equals(ReportSelection.NOVEL_REPORT) ? "left-highlight": ""}"
                               href="/mod/bao-cao-truyen">
                                Tiểu thuyết
                            </a>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

</main>
</body>
<%@include file="/WEB-INF/view/layout/boostrap_js.jsp" %>
</html>
