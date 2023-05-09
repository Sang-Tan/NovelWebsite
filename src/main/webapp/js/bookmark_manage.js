function addBookMark() {
    const addBookmarkBox = document.getElementById("add-bookmark");
    const deleteBookmarkBox = document.getElementById("delete-bookmark");

    const request = new XMLHttpRequest();
    request.open('POST', '/danh-dau', true);
    request.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8')
    request.onprogress = function (event) {

        if (request.status >= 400) {
            alert('Có lỗi xảy ra, vui lòng thử lại sau!');
            return;
        }
        const response = JSON.parse(request.responseText);
        if (response.status === 'success') {
            addBookmarkBox.hidden = true;
            deleteBookmarkBox.hidden = false;
        } else if (response.status === 'error') {
            alert(response.message);
        }
    }
    request.send(`id=${addBookmarkBox.dataset.chapterId}&action=add-bookmark`);
}

function deleteBookmark() {
    const deleteBookmarkBtn = document.getElementById("delete-bookmark");
    const addBookmarkBtn = document.getElementById("add-bookmark");

    const request = new XMLHttpRequest();
    request.open('POST', '/danh-dau', true);
    request.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8')
    request.onload = function () {
        if (request.status >= 400) {
            alert('Có lỗi xảy ra, vui lòng thử lại sau!');
            return;
        }
        const response = JSON.parse(request.responseText);
        if (response.status === 'success') {
            deleteBookmarkBtn.hidden = true;
            addBookmarkBtn.hidden = false;
        } else if (response.status === 'error') {
            alert(response.message);
        }
    }
    request.send(`id=${deleteBookmarkBtn.dataset.chapterId}&action=remove-bookmark`);
}


