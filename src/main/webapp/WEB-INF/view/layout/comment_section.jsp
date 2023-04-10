<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<section class="basic-section">
    <header class="basic-section__header">
        <h5>Bình luận</h5>
    </header>
    <div class="container-fluid">
        <p>Bạn phải <a href="#">Đăng nhập</a> hoặc <a href="#">Đăng ký</a> để có thể bình luận</p>
        <form action="../novel_detail.jsp" method="post" class="mb-3">
                                <textarea name="cmtText" title="cmt" id="" rows="10" class="cmt-box"
                                          style="min-height: 100px;resize: none;scroll-behavior: auto"></textarea>
            <div class="d-flex justify-content-end">
                <button type="submit" class="basic-btn basic-btn--green"
                        style="width: 100px">Gửi
                </button>
            </div>
        </form>
        <%@ include file="comment_section_content.jsp" %>
    </div>
</section>