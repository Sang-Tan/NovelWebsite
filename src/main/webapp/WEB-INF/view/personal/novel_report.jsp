<%--
  Created by IntelliJ IDEA.
  User: ACER
  Date: 01/04/2023
  Time: 9:36 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Báo cáo tiểu thuyết</title>
    <%@include file="/WEB-INF/view/layout/basic_stylesheet.jsp" %>
    <style>
        .section {
            font-weight: bold;
        }

        .section:hover {
            color: #3D9970;
            cursor: pointer;
        }
        .col-05{
            width: 4.17%;
        }
        .col-115{

        }
    </style>
</head>
<body style="background-color: var(--silver)">
<%@include file="layout/header.jsp" %>
<main class="mt-2">
    <div class="container">
        <div class="row">
            <div class="col-12 col-lg-9">
                <header class="mb-3">
                    <span class="title title--underline title--bold">Danh sách báo cáo</span>
                </header>
                <div class="row">
                    <div class="col-10 col-xl-6 thumb cmt-group">
                        <div class="rpt-group__item">
                            <a href="#report-modal" class="no-decor"
                               data-toggle="modal" data-target="#report-modal">
                                <div class="rpt-detail"
                                     style="background-color: var(--light-gray)">
                                    <div class="row">
                                        <div class="col-05">
                                            <button class="btn basic-btn--olive h-100"></button>
                                        </div>
                                        <div class="col-115">
                                            <span class="title title--bold" style="margin-left: 20px; color: black">
                                            Tên tiểu thuyết
                                        </span>
                                            <p style="margin-left: 20px; color: black">
                                                Báo cáo: 123
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </a>
                        </div>
                        <br>
                        <div class="rpt-group__item">
                            <a href="#report-modal" class="no-decor"
                               data-toggle="modal" data-target="#report-modal">
                                <div class="rpt-detail"
                                     style="background-color: var(--light-gray)">
                                    <div class="row">
                                        <div class="col-05">
                                            <button class="btn basic-btn--olive h-100"></button>
                                        </div>
                                        <div class="col-115">
                                            <span class="title title--bold" style="margin-left: 20px; color: black">
                                            Tên tiểu thuyết
                                        </span>
                                            <p style="margin-left: 20px; color: black">
                                                Báo cáo: 123
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </a>
                        </div>
                        <br>
                        <div class="rpt-group__item">
                            <a href="#report-modal" class="no-decor"
                               data-toggle="modal" data-target="#report-modal">
                                <div class="rpt-detail"
                                     style="background-color: var(--light-gray)">
                                    <div class="row">
                                        <div class="col-05">
                                            <button class="btn basic-btn--olive h-100"></button>
                                        </div>
                                        <div class="col-115">
                                            <span class="title title--bold" style="margin-left: 20px; color: black">
                                            Tên tiểu thuyết
                                        </span>
                                            <p style="margin-left: 20px; color: black">
                                                Báo cáo: 123
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </a>
                        </div>
                        <br>
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
                <table class="table table-light basic-section" border="1">
                    <tr style="background-color: var(--dark-silver)">
                        <th class="title title--bold">Loại báo cáo</th>
                    </tr>
                    <tr>
                        <td class="section"><a>Tiểu thuyết</a></td>
                    </tr>
                    <tr>
                        <td class="section"><a>Bình luận</a></td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
    <div class="modal fade" id="report-modal" tabindex="-1" aria-labelledby="exampleModalLabel"
         aria-hidden="true">
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
</main>
</body>
<%@include file="/WEB-INF/view/layout/boostrap_js.jsp" %>
</html>
