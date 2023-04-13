function postComment(e) {
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
    }).then(() => {
        reloadComments();
    });

}

function assignPostRootCommentForm() {
    const form = document.getElementById('root-comment-form');
    form.addEventListener('submit', postComment);
}

function loadRootComments(offset, limit, chapterId) {
    const requestUrl = `/comments?type=in_chapter&offset=${offset}&limit=${limit}&chapter-id=${chapterId}`;
    return fetch(requestUrl)
        .then(response => {
            if (response.ok) {
                return response.text();
            } else {
                throw new Error("Có lỗi xảy ra");
            }
        });
}

function nextCommentsButtonClicked(e) {
    e.preventDefault();
    const commentSection = document.getElementById('comment-section-contents');
    const currentOffset = parseInt(commentSection.dataset.offset);
    const limit = parseInt(commentSection.dataset.limit);
    const chapterId = commentSection.getAttribute('data-chapter-id');
    getRootCmtCountInChap(chapterId).then(commentCount => {
        if (currentOffset + limit >= commentCount) {
            return;
        }
        commentSection.dataset.offset = (currentOffset + limit).toString();
        reloadComments();
    }).catch(rejected => {
        console.error(rejected);
    });
}

function assignNextCommentsButton() {
    const button = document.getElementById('next-comments-button');
    button.addEventListener('click', nextCommentsButtonClicked);
}

function previousCommentsButtonClicked(e) {
    e.preventDefault();
    const commentSection = document.getElementById('comment-section-contents');
    if (parseInt(commentSection.dataset.offset) === 0) {
        return;
    }
    commentSection.dataset.offset =
        (parseInt(commentSection.dataset.offset) - parseInt(commentSection.dataset.limit)).toString();
    if (parseInt(commentSection.dataset.offset) < 0) {
        commentSection.dataset.offset = "0";
    }
    reloadComments();
}

function assignPreviousCommentsButton() {
    const button = document.getElementById('previous-comments-button');
    button.addEventListener('click', previousCommentsButtonClicked);
}

function replyCommentButtonClicked(event) {
    event.preventDefault();
    const commentIdToReply = this.getAttribute('data-reply-to');
    const thisCommentGroupItem = this.closest('.cmt-group__item');
    const existingReplyForm = document.getElementById('reply-comment-form');
    if (existingReplyForm) {
        if (thisCommentGroupItem.contains(existingReplyForm)) {
            existingReplyForm.remove();
            return;
        }
        existingReplyForm.remove();
    }

    const replyFormTemplate = document.getElementById('reply-comment-form-template');
    const replyForm = replyFormTemplate.content.cloneNode(true);
    replyForm.querySelector('input[name="comment-id"]').value = commentIdToReply;
    thisCommentGroupItem.appendChild(replyForm);
    document.getElementById('reply-comment-form').addEventListener('submit', postComment);
}

function assignReplyCommentButtons() {
    const buttons = document.querySelectorAll('[data-reply-to]');
    buttons.forEach(button => {
        button.addEventListener('click', replyCommentButtonClicked);
    });
}

function reloadComments() {
    const commentSection = document.getElementById('comment-section-contents');
    const offset = commentSection.dataset.offset;
    const limit = commentSection.dataset.limit;
    const chapterId = commentSection.getAttribute('data-chapter-id');
    loadRootComments(offset, limit, chapterId).then(resolved => {
            commentSection.innerHTML = resolved;
            assignReplyCommentButtons();
            const previousCommentsBtn = document.getElementById('previous-comments-button');
            if (offset == 0) {
                previousCommentsBtn.classList.add("inactive");
            } else {
                previousCommentsBtn.classList.remove("inactive");
            }

            getRootCmtCountInChap(chapterId).then(commentCount => {
                const nextCommentsBtn = document.getElementById('next-comments-button');
                if (parseInt(offset) + parseInt(limit) >= commentCount) {
                    nextCommentsBtn.classList.add("inactive");
                } else {
                    nextCommentsBtn.classList.remove("inactive");
                }
            }).catch(rejected => {
                console.error(rejected);
            });

        }
    ).catch(rejected => {
        console.error(rejected);
    });
}

function getRootCmtCountInChap(chapterId) {
    const requestUrl = `/comments?type=count_in_chapter&chapter-id=${chapterId}`;
    return fetch(requestUrl)
        .then(async response => {
            if (response.ok) {
                return parseInt(await response.text());
            } else {
                throw new Error("Something went wrong while fetching comment count");
            }
        });
}

assignPostRootCommentForm();
assignNextCommentsButton();
assignPreviousCommentsButton();
assignReplyCommentButtons();
reloadComments();