function promoteUserToModerator(userId) {
    const url = "/admin/moderator-promotion";
    const bodyString = `action=promote&user-id=${userId}`;
    fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: bodyString
    }).then(response => {
        if (response.ok) {
            location.reload();
        } else {
            alert("Có lỗi xảy ra");
        }
    });
}

function demoteUserFromModerator(userId) {
    const url = "/admin/moderator-promotion";
    const bodyString = `action=demote&user-id=${userId}`;
    fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: bodyString
    }).then(response => {
        if (response.ok) {
            location.reload();
        } else {
            alert("Có lỗi xảy ra");
        }
    });
}