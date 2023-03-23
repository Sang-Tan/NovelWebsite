<%@ page contentType="text/html;charset=UTF-8" language="java" %>

    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>Novel</title>

        <%@ include file="layout/basic_stylesheet.jsp" %>
            <link rel="stylesheet" href="/css/novel_detail.css">
    </head>

    <body style="background-color: var(--silver);">
        <jsp:include page="layout/header_main.jsp"></jsp:include>
        <div style="height: 30px"></div>
        <div class="container">
            <div class="row">
                <div class="col-12 col-lg-9">
                    <section class="basic-section pt-3">
                        <div class="container-fluid">
                            <div class="row">
                                <div class="col-12 col-md-3 d-flex">
                                    <div class="series-cover">
                                        <div class="a6-ratio">
                                            <div class="img-wrapper border"
                                                style="background-image: url('https://i.etsystatic.com/20023820/r/il/e12dd6/2885404230/il_fullxfull.2885404230_tb41.jpg');">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col">
                                    <h1 class="text-center user-select-none mb-3">Team có 2 thằng nhưng thằng kia làm BE
                                        rồi
                                        nên tôi phải làm FE</h1>
                                    <div class="d-flex mb-3">
                                        <div class="genre-item">
                                            <a href="#" class="genre-link">Slice of life</a>
                                        </div>
                                        <div class="genre-item">
                                            <a href="#" class="genre-link">Comedy</a>
                                        </div>
                                        <div class="genre-item">
                                            <a href="#" class="genre-link">Shounen</a>
                                        </div>
                                    </div>
                                    <div class="flex-column">
                                        <p class="mb-1"><b>Tác giả:</b> SPRV</p>
                                        <p class="mb-1"><b>Tình trạng:</b> Đang tiến hành</p>
                                        <p class="mb-1"><b>Lượt xem:</b> 100.000</p>
                                        <button class="basic-btn basic-btn--green">Theo dõi</button>
                                        <button class="basic-btn basic-btn--red">Bỏ theo dõi</button>
                                    </div>
                                </div>
                            </div>

                        </div>
                        <hr />
                        <div class="container-fluid">
                            <h5>Tóm tắt</h5>
                            <p>Tôi và thằng bạn của mình đã tham gia vào một dự án mới, tưởng chừng như mọi thứ sẽ dễ
                                dàng
                                với kinh nghiệm của chúng tôi trong lĩnh vực backend. Tuy nhiên, dự án đòi hỏi cả phần
                                frontend và backend, và đồng nghiệp của tôi được giao nhiệm vụ trên phần backend. Và
                                tôi?
                                Tôi phải làm việc trên phần frontend - một lĩnh vực mà tôi không có kinh nghiệm.</p>
                            <p>Một vài ngày đầu tiên là cực kỳ khó khăn. Tôi phải tìm hiểu và học hỏi rất nhiều thứ mới.
                                Tuy
                                nhiên, sau một thời gian, tôi bắt đầu cảm thấy thoải mái hơn. Tôi đã nắm bắt được cách
                                thức
                                hoạt động của các công nghệ phía frontend và đã bắt đầu tạo ra các giao diện người dùng
                                đơn
                                giản.</p>
                            <p>Những ngày tiếp theo là những ngày đầy thử thách. Tôi phải làm việc khẩn trương và chăm
                                chỉ
                                để hoàn thành dự án đúng thời hạn. Tuy nhiên, tôi không bỏ cuộc. Tôi đã học được rất
                                nhiều
                                từ việc làm việc trên phần frontend và trở thành một lập trình viên full-stack chuyên
                                nghiệp.</p>
                            <p>Sau những ngày đầy thử thách, tôi đã trở thành một lập trình viên full-stack đích thực.
                                Tôi
                                học được rất nhiều và vượt qua được rào cản của việc làm việc trên phần frontend. Nhưng
                                liệu
                                rằng điều gì sẽ chờ tôi trong tương lai? Hãy cùng đón xem những thử thách và cơ hội mới
                                sẽ
                                đến với tôi trong thế giới lập trình.</p>
                        </div>
                    </section>
                    <section class="basic-section">
                        <div class="basic-section__header">
                            <h5>Volume 1</h5>
                        </div>
                        <div class="container fluid">
                            <div class="row">
                                <div class="col-12 col-md-3 d-flex justify-content-center">
                                    <div class="a6-ratio volume-cover">
                                        <div class=" img-wrapper border"
                                            style="background-image: url('https://ecdn.game4v.com/g4v-content/uploads/2021/01/Hu-Tao-1-game4v.png');">
                                        </div>
                                    </div>
                                </div>
                                <div class="col col-md-9">
                                    <ul class="chapters">
                                        <li class="chapters__item">
                                            <div class="chapters__title">
                                                <a class="chapters__link" href="">Chap 1
                                                    overflow test overflow test overflow test overflow test</a>
                                            </div>
                                            <span class="chapters__time">1/1/2022</span>
                                        </li>
                                        <li class="chapters__item">
                                            <div class="chapters__title">
                                                <a class="chapters__link" href="">Chap 2</a>
                                            </div>
                                            <span class="chapters__time">2/1/2022</span>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>

                    </section>
                    <section class="basic-section">
                        <header class="basic-section__header">
                            <h5>Bình luận</h5>
                        </header>
                        <div class="container-fluid">
                            <p>Bạn phải <a href="#">Đăng nhập</a> hoặc <a href="#">Đăng ký</a> để có thể bình luận</p>
                            <form action="novel_detail.jsp" method="post" class="mb-3">
                                <textarea name="cmtText" title="cmt" id="" rows="10" class="cmt-box"
                                    style="min-height: 100px;resize: none;scroll-behavior: auto"></textarea>
                                <div class="d-flex justify-content-end">
                                    <button type="submit" class="basic-btn basic-btn--green"
                                        style="width: 100px">Gửi</button>
                                </div>
                            </form>
                            <div class="cmt-group">
                                <div class="cmt-group__item">
                                    <div class="cmt-group__avatar"
                                        style="background-image: url(https://a.ppy.sh/10969666_1614400439.jpeg);">
                                    </div>
                                    <div class="cmt-detail">
                                        <a href="" class="cmt-detail__name">Hu Tao</a>
                                        <p>Tin chuẩn chưa a</p>
                                        <div class="cmt-toolkit">
                                            <div class="cmt-toolkit__item">
                                                <a href="" class="cmt-toolkit__link">Trả lời</a>
                                            </div>
                                            <div class="cmt-toolkit__item">
                                                <a href="" class="cmt-toolkit__link">Báo cáo</a>
                                            </div>
                                            <div class="cmt-toolkit__item">
                                                <a href="" class="cmt-toolkit__link">
                                                    <i class="fas fa-thumbs-up"></i>
                                                    <span>Thích</span>
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="cmt-group__item">
                                    <div class="cmt-group__avatar"
                                        style="background-image: url('https://statics.voz.tech/data/avatars/m/1824/1824025.jpg?1659768273');">
                                    </div>
                                    <div class="cmt-detail">
                                        <a href="" class="cmt-detail__name">BLV Trương Anh Ngọc</a>
                                        <p>Chuẩn e nhé</p>
                                        <div class="cmt-toolkit">
                                            <div class="cmt-toolkit__item">
                                                <a href="" class="cmt-toolkit__link">Trả lời</a>
                                            </div>
                                            <div class="cmt-toolkit__item">
                                                <a href="" class="cmt-toolkit__link">Báo cáo</a>
                                            </div>
                                            <div class="cmt-toolkit__item">
                                                <a href="" class="cmt-toolkit__link">
                                                    <i class="fas fa-thumbs-up"></i>
                                                    <span>Thích</span>
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="cmt-group__item">
                                    <div class="cmt-group__avatar"
                                        style="background-image: url(https://cdn.shopify.com/s/files/1/0508/7775/9669/products/O1CN01zM7mRH1v7IcxM1vso__2208460416125.jpg?v=1639325196);">
                                    </div>
                                    <div class="cmt-detail">
                                        <a href="" class="cmt-detail__name">Boo Tao</a>
                                        <p>:))))))))</p>
                                        <p>:))))))))</p>
                                        <p>:))))))))</p>
                                        <p>:))))))))</p>
                                        <p>:))))))))</p>
                                        <div class="cmt-toolkit">
                                            <div class="cmt-toolkit__item">
                                                <a href="" class="cmt-toolkit__link">Trả lời</a>
                                            </div>
                                            <div class="cmt-toolkit__item">
                                                <a href="" class="cmt-toolkit__link">Báo cáo</a>
                                            </div>
                                            <div class="cmt-toolkit__item">
                                                <a href="" class="cmt-toolkit__link">
                                                    <i class="fas fa-thumbs-up"></i>
                                                    <span>Thích</span>
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>
                </div>
            </div>
        </div>

        <!-- Bootstrap -->
        <%@ include file="layout/boostrap_js.jsp" %>
    </body>
    <%@ include file="layout/boostrap_js.jsp" %>

    </html>