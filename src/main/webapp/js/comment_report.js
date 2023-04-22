//["Bao cao 1", "Bao cao 2"]
async function getReportsInComment(commentId) {
    let res = await fetch(`/mod/bao-cao-binh-luan?type=comment_report&comment-id=${commentId}`)
        .then(response => {
            if (response.ok) {
                const respText = response.text();
                return respText.then(result => {
                    const arr = JSON.parse(result);
                    return arr;
                });
            } else {
                return Promise.reject("Something went wrong");
            }
        });
    return res;
}

async function reportCommentForm(commentId, ownerName, ownerAvatar, ownerId, commentContent) {
    document.getElementById("commentId").value = commentId;
    document.getElementById("ownerName").innerText = ownerName;
    document.getElementById("ownerAvatar").src = ownerAvatar;
    let link = document.getElementById("ownerId");
    link.href = "/thanh-vien/" + ownerId;
    document.getElementById("commentContent").innerText = commentContent;
    const reasonDiv = document.getElementById("reasons");
    reasonDiv.innerHTML = null;

    getReportsInComment(commentId).then(reportArr => {
        console.log(reportArr);
        reportArr.forEach((value) => {
            const node = document.createElement("p");
            node.innerText = value;
            reasonDiv.appendChild(node);
            reasonDiv.appendChild(document.createElement("hr"));
        });
    });
}