async function getReportsInNovel(novelId) {
    let res = await fetch(`/mod/bao-cao-truyen?type=novel_report&novel-id=${novelId}`)
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

function novelReportChecked(novelId) {
    // let novelId = document.getElementById("novelId").value;
    fetch(`/mod/bao-cao-truyen?action=checked&novelId=${novelId}`, {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        }
    }).then(response => {
        console.log(response);
    });
}

function deleteNovelReport() {
    let novelId = document.getElementById("novelId").value;
    fetch(`/mod/bao-cao-truyen?action=delete&novelId=${novelId}`, {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        }
    }).then(response => {
        console.log(response);
        location.reload();
    });
}

async function reportNovelForm(novelId, ownerName, ownerAvatar, ownerId, novelName) {
    let linkNovel = document.getElementById("linkNovel");
    linkNovel.href = "/truyen/" + novelId;
    document.getElementById("novelId").value = novelId;
    document.getElementById("ownerName1").innerText = ownerName;
    document.getElementById("ownerAvatar1").src = ownerAvatar;
    let link = document.getElementById("ownerId1");
    link.href = "/thanh-vien/" + ownerId;
    document.getElementById("novelName").innerText = novelName;
    const reasonDiv = document.getElementById("reasons1");
    reasonDiv.innerHTML = "";

    getReportsInNovel(novelId).then(reportArr => {
        console.log(reportArr);
        reportArr.forEach((value) => {
            const node = document.createElement("p");
            node.innerText = value;
            reasonDiv.appendChild(node);
            reasonDiv.appendChild(document.createElement("hr"));
        });
    });
    novelReportChecked(novelId);
}

function showReportNovelForm(novelId, userId) {
    document.getElementById("novelId").value = novelId;
    document.getElementById("userId1").value = userId;
}

function submitNovelReport() {
    const form = document.getElementById('reportNovelForm');
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
                        confirmButtonColor: '#3d9970',
                        icon: 'success'
                    });
                    novelReportChecked();
                } else if (status === "error") {
                    const errorMsg = data.message;
                    Swal.fire({
                        title: 'Có lỗi xảy ra',
                        text: errorMsg,
                        confirmButtonColor: '#3d9970',
                        icon: 'error'
                    });
                }

            });
            document.getElementById("reason").value = "";
        } else {
            Swal.fire({
                title: 'Đã có lỗi xảy ra!',
                confirmButtonColor: '#3d9970',
                icon: 'error'
            });
        }
    });
    $('#reportNovelModal').hide();
    $('.modal-backdrop').remove();
}
