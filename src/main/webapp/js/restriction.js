function submitAddRestrictionForm(e) {
    e.preventDefault();
    const formData = new FormData(this);
    const restrictionType = formData.get("restriction-type");
    let formDataArr = [];
    for (const [key, value] of formData.entries()) {
        formDataArr.push(key + "=" + value);
    }
    const requestBody = formDataArr.join("&");
    fetch("/mod/restrictions", {
        method: "POST",
        body: requestBody,
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        }
    }).then(response => {
        if (response.ok) {
            location.reload();
        } else {
            console.log("Error adding restriction");
        }
    })
}

function removeRestriction(userId, restrictionType) {
    fetch('/mod/restrictions', {
        body: `action=remove_restriction&user-id=${userId}&restriction-type=${restrictionType}`,
        method: 'POST',
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        }
    }).then(response => {
        if (response.ok) {
            location.reload();
        } else {
            console.log("Error when removing restriction");
        }
    })
}

async function getRestriction(userId, restrictionType) {
    return fetch(`/mod/restrictions?type=get_one&user-id=${userId}&restriction-type=${restrictionType}`,
        {method: "GET"}).then(response => {
        if (response.ok) {
            return response.json();
        } else {
            throw new Error("Error when getting restriction");
        }
    });
}

function showButton(button) {
    button.classList.remove("hidden");
}

function hideButton(button) {
    button.classList.add("hidden");
}

const addRestrictionCmtBtn = document.getElementById("add-restriction-comment-btn");
const removeRestrictionCmtBtn = document.getElementById("remove-restriction-comment-btn");
const addRestrictionNovelBtn = document.getElementById("add-restriction-novel-btn");
const removeRestrictionNovelBtn = document.getElementById("remove-restriction-novel-btn");

const addRestrictionForm = document.getElementById("add-restriction-form");
$('#add-restriction-modal').on("show.bs.modal", function (event) {
    const button = $(event.relatedTarget);
    const restrictionType = button.data("restriction");
    const modal = $(this);

    let restrictionHeaderText = button.text();
    modal.find("#restriction-modal-header").text(restrictionHeaderText);
    modal.find("input[name='reason']").val('');
    modal.find("input[name='restriction-type']").val(restrictionType);
});
addRestrictionForm.addEventListener("submit", submitAddRestrictionForm);

$('#remove-restriction-modal').on("show.bs.modal", function (event) {
    const button = $(event.relatedTarget);
    const modal = $(this);
    const restrictionType = button.data("restriction");
    const userId = button.data("user-id");

    let restrictionHeaderText = button.text();

    getRestriction(userId, restrictionType).then(restriction => {
        console.log(restriction);
        modal.find("#remove-restriction-modal-header").text(restrictionHeaderText);
        modal.find("[data-name='reason']").text("Lí do cấm : " + restriction['reason']);
        modal.find("[data-name='due-time']").text("Thời gian cấm đến : " + restriction['dueTime']);
        modal.find("[data-name='submit-btn']").on("click", function () {
            removeRestriction(userId, restrictionType);
        });
    }).catch(error => {
        console.log(error);
    })
});
