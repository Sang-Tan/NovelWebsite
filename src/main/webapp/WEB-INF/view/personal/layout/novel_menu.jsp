<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="novel" type="model.Novel"--%>
<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 3/18/2023
  Time: 4:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<ul class="tree-list">
    <li class="tree-node">
        <span class="tree-item">
            <a class="no-decor black" href="#" role="button" id="novel-item"
               data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                ${novel.name}
            </a>
            <div class="dropdown-menu" aria-labelledby="novel-item">
                <a class="dropdown-item" href="/ca-nhan/tieu-thuyet/${novel.id}">Chỉnh sửa</a>
                <a class="dropdown-item" href="/ca-nhan/tieu-thuyet/${novel.id}?tap-moi">Thêm tập</a>
                <a class="dropdown-item" href="#">Sắp xếp tập</a>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="#">Xoá truyện</a>
            </div>
        </span>
        <c:if test="${novel.volumes.size() > 0}">
            <span class="tree-toggle"></span>
            <ul class="tree-nested">
                <c:forEach items="${novel.volumes}" var="volume">
                    <c:choose>
                        <c:when test="${volume.chapters.size() == 0}">
                            <li class="tree-item overflow-elipsis">
                                <a class="no-decor black" href="#" role="button" id="volume-item-1"
                                   data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                        ${volume.name}
                                </a>
                                <div class="dropdown-menu" aria-labelledby="volume-item-1">
                                    <a class="dropdown-item" href="#">Chỉnh sửa</a>
                                    <a class="dropdown-item" href="#">Thêm chapter</a>
                                    <a class="dropdown-item" href="#">Sắp xếp chapter</a>
                                    <div class="dropdown-divider"></div>
                                    <a class="dropdown-item" href="#">Xoá volume</a>
                                </div>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="tree-node">
                                <span class="tree-toggle"></span>
                                <span class="tree-item overflow-elipsis">
                                    <a class="no-decor black" href="#" role="button" id="volume-item-2"
                                       data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                            ${volume.name}
                                    </a>
                                    <div class="dropdown-menu" aria-labelledby="volume-item-2">
                                        <a class="dropdown-item" href="#">Chỉnh sửa</a>
                                        <a class="dropdown-item" href="#">Thêm chapter</a>
                                        <a class="dropdown-item" href="#">Sắp xếp chapter</a>
                                        <div class="dropdown-divider"></div>
                                        <a class="dropdown-item" href="#">Xoá volume</a>
                                    </div>
                                </span>
                                <ul class="tree-nested">
                                    <c:forEach items="${volume.chapters}" var="chapter">
                                        <li class="tree-item overflow-elipsis">
                                            <a class="no-decor black" href="#" role="button" id="chapter-item-1"
                                               data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                    ${chapter.name}
                                            </a>
                                            <div class="dropdown-menu" aria-labelledby="chapter-item-1">
                                                <a class="dropdown-item" href="#">Chỉnh sửa</a>
                                                <div class="dropdown-divider"></div>
                                                <a class="dropdown-item" href="#">Xoá chapter</a>
                                            </div>
                                        </li>
                                    </c:forEach>

                                </ul>
                            </li>
                        </c:otherwise>
                    </c:choose>


                </c:forEach>

            </ul>
        </c:if>

    </li>
</ul>