<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ACER
  Date: 08/04/2023
  Time: 10:34 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Kiểm duyệt truyện</title>
    <%@include file="/WEB-INF/view/layout/basic_stylesheet.jsp" %>
    <link rel="stylesheet" href="/css/novel_detail.css">
    <link rel="stylesheet" href="/css/main.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css"
          integrity="sha512-iBBXm8fW90+nuLcSKlbmrPcLa0OT92xO1BIsZ+ywDWZCvqsWgccV3gFoRBv0z+8dLJgyAHIhR35VZc2oM/gI1w=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
</head>
<jsp:include page="/WEB-INF/view/layout/header_admin.jsp"></jsp:include>
<body style="background-color: var(--silver)">
<main class="mt-2">
    <div class="container">
        <div class="row">
            <div class="col-12">
                <header class="mb-3 d-flex justify-content-between align-items-center">
                    <span class="title title--underline title--bold">Danh sách cần duyệt</span>
                </header>

                <div class="d-flex mb-3" style="flex-wrap: wrap">
                    <div class="tab-section col-12">
                        <ul class="tab-list">
                            <li class="tab active">Duyệt truyện</li>
                            <li class="tab ">Duyệt chương</li>
                        </ul>
                        <div class="tab-panels">
                            <div class="p-0 tab-panel active">
                                <div class="col-12 col-xl-12">
                                    <table class="table table-striped">
                                        <tr class="text-center">
                                            <th>Ảnh bìa</th>
                                            <th>Tên truyện</th>
                                            <th>Tác giả</th>
                                            <th>Thời gian đăng</th>
                                            <th>Hành động</th>
                                        </tr>
                                        <c:forEach var="novel" items="${novelList}">
                                            <tr class="text-center">
                                                <td><img src="${novel.image}" alt="Ảnh bìa" width="70px" height="100px">
                                                </td>
                                                <td>${novel.name}</td>
                                                <td>${novel.owner.displayName}</td>
                                                <td>${novel.createdTime}</td>
                                                <td>
                                                    <a href="/ca-nhan/tap-truyen/${novel.id}" target="_blank">
                                                        <button class="basic-btn basic-btn--olive"
                                                                style="background-color: dodgerblue; color: white">
                                                            <i class="fas fa-external-link-alt"></i> Chi tiết
                                                        </button>
                                                    </a>
                                                    <button class="basic-btn basic-btn--red"
                                                            data-toggle="modal" data-target="#rejectNovelModal"
                                                            onclick="showNovelForm(${novel.getId()}, '${novel.getName()}')">
                                                        <i class="fas fa-times-circle"></i> Từ chối
                                                    </button>
                                                    <button class="basic-btn basic-btn--olive"
                                                            data-toggle="modal" data-target="#approveNovelModal"
                                                            onclick="showNovelForm(${novel.getId()}, '${novel.getName()}')">
                                                        <i class="fas fa-check"></i> Duyệt
                                                    </button>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        <c:if test="${novelList == null}">
                                            <tr>
                                                <td colspan="10" class="text-center">Không có truyện cần duyệt</td>
                                            </tr>
                                        </c:if>
                                    </table>
                                </div>
                            </div>
                            <div class="p-0 tab-panel ">
                                <table class="table table-striped">
                                    <tr class="text-center">
                                        <th style="width: 20%">Tên chương</th>
                                        <th>Tên truyện</th>
                                        <th>Tác giả</th>
                                        <th>Thời gian sửa</th>
                                        <th>Hành động</th>
                                    </tr>
                                    <c:forEach var="chapter" items="${chapterList}">
                                        <tr class="text-center">
                                            <td style="overflow: hidden; text-overflow: ellipsis">${chapter.name}</td>
                                            <td style="overflow: hidden; text-overflow: ellipsis">
                                                    ${chapter.getBelongVolume().getBelongNovel().name}
                                            </td>
                                            <td>${chapter.getBelongVolume().getBelongNovel().owner.displayName}</td>
                                            <td>${chapter.modifyTime}</td>
                                            <td>
                                                <a href="/ca-nhan/chuong-truyen/${chapter.id}" target="_blank">
                                                    <button class="basic-btn basic-btn--olive"
                                                            style="background-color: dodgerblue; color: white">
                                                        <i class="fas fa-external-link-alt"></i> Chi tiết
                                                    </button>
                                                </a>
                                                <button class="basic-btn basic-btn--red"
                                                        data-toggle="modal" data-target="#rejectChapterModal"
                                                        onclick="showChapterForm(${chapter.getId()}, '${chapter.getName()}')">
                                                    <i class="fas fa-times-circle"></i> Từ chối
                                                </button>
                                                <button class="basic-btn basic-btn--olive"
                                                        data-toggle="modal" data-target="#approveChapterModal"
                                                        onclick="showChapterForm(${chapter.getId()}, '${chapter.getName()}')">
                                                    <i class="fas fa-check"></i> Duyệt
                                                </button>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    <c:if test="${chapterList == null}">
                                        <tr>
                                            <td colspan="10" class="text-center">Không có chương cần duyệt</td>
                                        </tr>
                                    </c:if>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
<!--Modal reject novel-->
<div class="modal fade" id="rejectNovelModal" tabindex="-1" aria-labelledby="staticBackdropLabel2" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title title title--bold" id="staticBackdropLabel2">Từ chối truyện</h5>
                <i class="fas fa-compress-arrows-alt top-right-btn" data-dismiss="modal" aria-label="Close"
                   style="font-size: x-large"></i>
            </div>
            <div class="modal-body">
                Bạn có muốn từ chối <span class="text-success nameNovel"></span><span> không?</span>
            </div>
            <form action="/mod/duyet-truyen" method="get">
                <div class="modal-footer justify-content-center">
                    <button type="button" class="basic-btn basic-btn--red" data-bs-dismiss="modal">Đóng</button>
                    <button type="submit" class="basic-btn basic-btn--olive">OK</button>
                    <input hidden name="action" value="reject" type="text">
                    <input hidden name="idNovel" type="text" class="idNovel">
                </div>
            </form>
        </div>
    </div>
</div>
<!--Modal approve novel-->
<div class="modal fade" id="approveNovelModal" tabindex="-1" aria-labelledby="staticBackdropLabel3" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title title title--bold" id="staticBackdropLabel3">Duyệt truyện</h5>
                <i class="fas fa-compress-arrows-alt top-right-btn" data-dismiss="modal" aria-label="Close"
                   style="font-size: x-large"></i>
            </div>
            <div class="modal-body">
                Bạn có muốn duyệt <span class="text-success nameNovel"></span><span> không?</span>
            </div>
            <form action="/mod/duyet-truyen" method="get">
                <div class="modal-footer justify-content-center">
                    <button type="button" class="basic-btn basic-btn--red" data-bs-dismiss="modal">Đóng</button>
                    <button type="submit" class="basic-btn basic-btn--olive">OK</button>
                    <input hidden name="action" value="reject" type="text">
                    <input hidden name="idNovel" type="text" class="idNovel">
                </div>
            </form>
        </div>
    </div>
</div>
<!--Modal reject chapter-->
<div class="modal fade" id="rejectChapterModal" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title title title--bold" id="staticBackdropLabel">Từ chối chương</h5>
                <i class="fas fa-compress-arrows-alt top-right-btn" data-dismiss="modal" aria-label="Close"
                   style="font-size: x-large"></i>
            </div>
            <div class="modal-body">
                Bạn có muốn từ chối <span class="text-success nameChapter"></span><span> không?</span>
            </div>
            <form action="/mod/duyet-truyen" method="get">
                <div class="modal-footer justify-content-center">
                    <button type="button" class="basic-btn basic-btn--red" data-bs-dismiss="modal">Đóng</button>
                    <button type="submit" class="basic-btn basic-btn--olive">OK</button>
                    <input hidden name="action" value="reject" type="text">
                    <input hidden name="idChapter" type="text" class="idChapter">
                </div>
            </form>
        </div>
    </div>
</div>
<!--Modal approve chapter-->
<div class="modal fade" id="approveChapterModal" tabindex="-1" aria-labelledby="staticBackdropLabel1" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title title title--bold" id="staticBackdropLabel1">Duyệt chương</h5>
                <i class="fas fa-compress-arrows-alt top-right-btn" data-dismiss="modal" aria-label="Close"
                   style="font-size: x-large"></i>
            </div>
            <div class="modal-body">
                Bạn có muốn duyệt <span class="text-success nameChapter"></span><span> không?</span>
            </div>
            <form action="/mod/duyet-truyen" method="get">
                <div class="modal-footer justify-content-center">
                    <button type="button" class="basic-btn basic-btn--red" data-bs-dismiss="modal">Đóng</button>
                    <button type="submit" class="basic-btn basic-btn--olive">OK</button>
                    <input hidden name="action" value="reject" type="text">
                    <input hidden name="idChapter" type="text" class="idChapter">
                </div>
            </form>
        </div>
    </div>
</div>
</body>
<%@include file="/WEB-INF/view/layout/boostrap_js.jsp" %>
<script>
    const tabs = document.querySelectorAll('.tab');

    tabs.forEach((tab) => {
        tab.addEventListener('click', (e) => {
            const target = e.target;
            const targetIndex = Array.from(target.parentElement.children).indexOf(target);

            //select panel
            const targetSection = target.closest('.tab-section');
            if (!targetSection) {
                throw new Error(`${target} does not in class tab-section`);
            }
            const targetPanels = targetSection.querySelector('.tab-panels');
            const targetPanel = targetPanels.children[targetIndex];

            //deactive tabs
            const parent = target.closest('.tab-list');
            if (!parent) {
                throw new Error(`Parent of ${target} does not have class tab-list`);
            }

            const activeTabs = parent.querySelectorAll('.tab.active');
            activeTabs.forEach((tab, index) => {
                tab.classList.remove('active');
            });

            //deactive panels
            const activePanels = targetPanels.querySelectorAll('.tab-panel.active');
            activePanels.forEach((panel, index) => {
                panel.classList.remove('active');
            });
            target.classList.add('active');
            targetPanels.children[targetIndex].classList.add('active');
        });
    });

    function showChapterForm(id, name) {
        document.getElementsByClassName("idChapter")[0].value = id;
        document.getElementsByClassName("nameChapter")[0].innerText = name;
        document.getElementsByClassName("idChapter")[1].value = id;
        document.getElementsByClassName("nameChapter")[1].innerText = name;
    }

    function showNovelForm(id, name) {
        document.getElementsByClassName("idNovel")[0].value = id;
        document.getElementsByClassName("nameNovel")[0].innerText = name;
        document.getElementsByClassName("idNovel")[1].value = id;
        document.getElementsByClassName("nameNovel")[1].innerText = name;
    }
</script>
</html>
