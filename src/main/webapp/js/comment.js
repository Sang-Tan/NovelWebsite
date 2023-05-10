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
        } else {
            alert("Có lỗi xảy ra");
        }
    }).then(() => {
        const commentSection = document.getElementById('comment-section-contents');
        commentSection.dataset.offset = "0";
        reloadComments();
    });

}

function assignPostRootCommentForm() {
    const form = document.getElementById('root-comment-form');
    if (form) {
        form.addEventListener('submit', (e) => {
            postComment.bind(form)(e);
            form.reset();
        });
    }
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
    const offset = Number.parseInt(commentSection.dataset.offset);
    const limit = Number.parseInt(commentSection.dataset.limit);
    const chapterId = Number.parseInt(commentSection.getAttribute('data-chapter-id'));
    loadRootComments(offset, limit, chapterId).then(resolved => {
            commentSection.innerHTML = resolved;
            assignReplyCommentButtons();

            const previousCommentsBtn = document.getElementById('previous-comments-button');
            if (offset === 0) {
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

function showReportCommentForm(commentId, userId) {
    document.getElementById("commentId").value = commentId;
    document.getElementById("userId").value = userId;
}

function submitCommentReport() {
    const form = document.getElementById('reportCommentForm');
    const formData = new FormData(form);
    let parameters = [];
    for (const [key, value] of formData) {
        parameters.push(`${key}=${value}`)
    }
    let parameterString = "&" + parameters.join("&");
    fetch(`${form.action}${parameterString}`, {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        }
    }).then(response => {
        if (response.ok) {
            response.json().then(data => {
                const status = data.status;
                if (status === "success") {
                    Swal.fire({
                            title: 'Báo cáo thành công!',
                            text: 'Cảm ơn bạn đã báo cáo',
                            icon: 'success',
                            confirmButtonColor: '#3d9970'
                        }
                    )
                } else {
                    const message = data.message;
                    Swal.fire({
                            title: 'Đã xảy ra lỗi!',
                            text: message,
                            icon: 'error',
                            confirmButtonColor: '#3d9970'
                        }
                    );
                }
            });
        } else {
            Swal.fire({
                title: 'Có lỗi xảy ra!',
                icon: 'error'
            });
        }
    });

    document.getElementById("reason").value = "";
    $('#reportCommentModal').hide();
    $('.modal-backdrop').remove();
}

function showDeactivateCommentForm(commentId) {
    const deactivateCmtSubmitBtn = document.getElementById('deactivate-cmt-submit');
    deactivateCmtSubmitBtn.onclick = () => {
        submitDeactivateComment(commentId);
    };
}

function submitDeactivateComment(commentId) {
    const requestUrl = '/mod/moderate-comment';
    const bodyData = `action=deactivate&comment-id=${commentId}`
    fetch(requestUrl, {
        method: "POST",
        body: bodyData,
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        }
    }).then(response => {
        if (response.ok) {
            Swal.fire({
                title: 'Đã ẩn bình luận!',
                icon: 'success'
            }).then(() => {
                reloadComments();
            });

        } else {
            Swal.fire({
                title: 'Có lỗi xảy ra!',
                icon: 'error',
                text: 'Lỗi không xác định'
            });
        }
    });
}

/**
 * @param fragmentId {string}
 */
function goToFragment(fragmentId) {
    const element = document.getElementById(fragmentId);
    if (element) {
        element.scrollIntoView();
        element.classList.add('highlight');
    }
}

/**
 * @param commentId {number}
 * @return {Promise<number>}
 */
async function getCommentOffset(commentId) {
    const requestUrl = `/comments?type=comment_offset&comment-id=${commentId}`;
    return fetch(requestUrl)
        .then(async response => {
            if (response.ok) {
                return parseInt(await response.text());
            } else {
                throw new Error("Something went wrong while fetching comment offset");
            }
        });
}

function detectCommentParamOnLoad() {
    const cmtParam = new URLSearchParams(window.location.search).get('comment-id');
    if (!cmtParam) {
        return;
    }
    const commentId = parseInt(cmtParam);
    getCommentOffset(commentId).then(offset => {
        const commentSection = document.getElementById('comment-section-contents');
        commentSection.dataset.offset = offset.toString();
        reloadComments();

        setTimeout(() => {
            goToFragment(`comment-id-${commentId}`);
        }, 400);
    });


}

document.addEventListener("DOMContentLoaded", function () {
    detectCommentParamOnLoad();
});

assignPostRootCommentForm();
assignNextCommentsButton();
assignPreviousCommentsButton();
assignReplyCommentButtons();
reloadComments();
