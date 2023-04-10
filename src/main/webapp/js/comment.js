function postRootComment(e) {
    e.preventDefault();
    const form = this;
    const formData = new FormData(form);
    const url = "/comments";
    let body = [];
    formData.forEach(
        (value, key) => {
            const encodedKey = encodeURIComponent(key);
            const encodedValue = encodeURIComponent(value);
            body.push(encodedKey + "=" + encodedValue);
        });
    const bodyString = body.join("&");
    fetch(url, {
        method: 'POST',
        body: bodyString,
        headers: {
            "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8"
        }
    }).then(response => {
        if (response.ok) {
            console.log("Comment posted");
        } else {
            alert("Có lỗi xảy ra");
        }
    })
}

function assignPostRootCommentForm() {
    const form = document.getElementById('root-comment-form');
    form.addEventListener('submit', postRootComment);
}

assignPostRootCommentForm();