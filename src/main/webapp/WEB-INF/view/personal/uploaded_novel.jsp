<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 3/17/2023
  Time: 2:49 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cá nhân</title>
    <%@include file="/WEB-INF/view/layout/basic_stylesheet.jsp" %>
</head>
<body>
<%@include file="layout/header.jsp" %>

<div class="container mt-3">
    <div class="basic-section">
        <div class="basic-section__header ">
            <h4>Truyện đã đăng (10)</h4>
        </div>
        <div class="containter-fluid ml-3 mr-3">
            <div class="row">
                <div class="col-3 col-md-2 thumb">
                    <a href="#" class="no-decor">
                        <div class="thumb__wrapper">
                            <div class="thumb__img-panel a6-ratio">
                                <div class="img-wrapper border "
                                     style="background-image: url('https://images.fpt.shop/unsafe/filters:quality(90)/fptshop.com.vn/uploads/images/tin-tuc/152650/Originals/Hu%20Tao.jpg');">
                                </div>
                            </div>
                            <p class="thumb__caption">Hu Tao saves my life</p>
                        </div>
                    </a>
                </div>
            </div>
        </div>

    </div>
</div>

<%-- boostrap script --%>
<%@include file="/WEB-INF/view/layout/boostrap_js.jsp" %>
</body>
</html>
