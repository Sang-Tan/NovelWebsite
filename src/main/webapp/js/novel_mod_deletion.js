/**
 * @param form {HTMLFormElement}
 * @param e {Event}
 */
function modDeleteNovel(form, e) {
    e.preventDefault();
    submitFormAjax(form).then(function (response) {
        if (response.status === 'success') {
            Swal.fire({
                title: 'Xóa truyện thành công!',
                icon: 'success',
                confirmButtonText: 'OK'
            }).then(function () {
                window.location.href = '/';
            });
        } else if (response.status === 'error') {
            Swal.fire({
                title: 'Xóa truyện thất bại!',
                text: response.message,
                icon: 'error',
                confirmButtonText: 'OK'
            });
        }
    }).catch(function () {
        Swal.fire({
            title: 'Xóa truyện thất bại!',
            text: 'Có lỗi xảy ra, vui lòng thử lại sau!',
            icon: 'error',
            confirmButtonText: 'OK'
        });
    });
}