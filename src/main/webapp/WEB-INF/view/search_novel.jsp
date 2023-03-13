<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Landing page</title>
    <!-- normalize css -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/8.0.1/normalize.min.css"
          integrity="sha512-NhSC1YmyruXifcj/KFRWoC561YpHpc5Jtzgvbuzx5VozKpWvQ+4nXhPdFgmx8xqexRcpAglTj9sIBWINXa8x5w=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>

    <!-- bootstrap css -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

    <!-- Google Font -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;500;600;700&display=swap" rel="stylesheet">

    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css"
          integrity="sha512-iBBXm8fW90+nuLcSKlbmrPcLa0OT92xO1BIsZ+ywDWZCvqsWgccV3gFoRBv0z+8dLJgyAHIhR35VZc2oM/gI1w=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>

    <!-- Custom StyleSheet -->
    <!-- <link rel="stylesheet" href="./css/core.css"> -->
    <link rel="stylesheet" href="/css/main.css">
    <link rel="stylesheet" href="/css/search_novel.css">

</head>

<body style="background-color: var(--silver);">
<jsp:include page="layout/header_main.jsp"></jsp:include>
<div class="container mt-4">
    <form class="container-fluid basic-section p-3" id="search-form"
          onsubmit="event.preventDefault(); submitForm()">
        <div class="row">
            <div class="d-flex align-items-center col-10">
                <label for="novel" class="label">Tên truyện: </label>
                <input class="input-text" style="flex-grow: 1;" type="text" name="novel" id="novel"
                       placeholder="Tên truyện">
            </div>
            <div class="col d-flex">
                <button class="basic-btn basic-btn--olive" style="width: 100%;" type="submit">Tìm kiếm</button>
            </div>
        </div>
        <div class="row">
            <div class="col-12 col-lg-4">
                <div class="d-flex align-items-center mt-2">
                    <label for="author" class="label">Tác giả: </label>
                    <input class="input-text" style="flex-grow: 1;" type="text" name="author" id="author"
                           placeholder="Tên tác giả">
                </div>
            </div>
            <div class="col-12 col-lg-4">
                <div class="d-flex align-items-center mt-2">
                    <label for="status" class="label">Tình trạng: </label>
                    <!-- <input style="flex-grow: 1;" type="" name="author" id="status" placeholder="Tên tác giả"> -->
                    <select class="input-text" name="status" id="status" style="flex-grow: 1;">
                        <option value="dangtienhanh">Đang tiến hành</option>
                        <option value="finish">Hoàn thành</option>
                        <option value="delay">Tạm hoãn</option>
                    </select>
                </div>
            </div>
            <div class="col-12 col-lg-4">
                <div class="d-flex align-items-center mt-2">
                    <label for="sort" class="label">Sắp xếp theo: </label>
                    <select class="input-text" style="flex-grow: 1;" name="" id="sort">
                    </select>
                </div>
            </div>
        </div>
        <div class="mt-2 d-flex">
            <label for="genre" class="label">Thể loại: </label>
            <div style="flex-grow:1;" class="genre-holder" id="genre">
                <div class="checkbox-holder">
                    <input class="checkbox1" type="checkbox" data-genre="1" id="genre1">
                    <label for="genre1"> Genre1</label>
                </div>
                <div class="checkbox-holder">
                    <input class="checkbox1" type="checkbox" data-genre="2" id="genre2">
                    <label for="genre2"> Genre2</label>
                </div>
                <div class="checkbox-holder">
                    <input class="checkbox1" type="checkbox" data-genre="" id="genre3">
                    <label for="genre3"> Genre3</label>
                </div>
            </div>
        </div>
    </form>
</div>


<script src="./js/search_novel.js"></script>
</body>

</html>