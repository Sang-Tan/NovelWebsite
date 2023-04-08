<%--@elvariable id="ChapterRepository" type="repository.ChapterRepository.class"--%>
<%--@elvariable id="novelChapterPairs" type="java.util.List<core.Pair<model.Novel,model.Chapter>>"--%>
<%@ page import="model.Chapter" %>
<%@ page import="repository.ChapterRepository" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="basic-section">
    <div>
        <table class="table mb-0">
            <thead>
            <tr style="background-color: var(--olive); color: var(--silver)">
                <th>Tên truyện</th>
                <th>Chương mới nhất</th>
                <th>Theo dõi</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${novelChapterPairs}" var="novelChapterPair">
                <c:set var="favouriteNovel" value="${novelChapterPair.key}"/>
                <c:set var="latestChapter" value="${novelChapterPair.value}"/>
                <tr>
                    <td>
                        <div class="d-flex">
                            <div class="a6-ratio" style="width: 3rem">
                                <div class="img-wrapper" style="background-image: url('${favouriteNovel.image}')">
                                </div>
                            </div>
                            <div class="ml-3">
                                <a class="theme-link  w-600" href="#">${favouriteNovel.name}</a>
                                <div>${favouriteNovel.owner.displayName}</div>
                            </div>
                        </div>
                    </td>
                    <td>
                        <a class="theme-link w-600" href="#">${latestChapter.name}</a>
                    </td>
                    <td>
                        <button data-action="unfollow" data-id="${favouriteNovel.id}" class="basic-btn basic-btn--red">
                            <i class="fas fa-times"></i>
                            Bỏ theo dõi
                        </button>
                        <button data-action="follow" data-id="${favouriteNovel.id}"
                                class="basic-btn basic-btn--green hidden">
                            <i class="fas fa-heart"></i>
                            Theo dõi
                        </button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<script src="/js/personal_interest.js"></script>