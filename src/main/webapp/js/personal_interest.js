function followButtonClick() {
    const followButton = this;
    const unfollowButton = followButton.parentNode.querySelector('[data-action="unfollow"]');

    const request = new XMLHttpRequest();
    request.open('POST', '/theo-doi', true);
    request.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8')
    request.onload = function () {
        if (request.status >= 400) {
            alert('Có lỗi xảy ra, vui lòng thử lại sau!');
            return;
        }
        const response = JSON.parse(request.responseText);
        if (response.status === 'success') {
            followButton.classList.add('hidden');
            unfollowButton.classList.remove('hidden');
        } else if (response.status === 'error') {
            alert(response.message);
        }
    }
    request.send(`id=${followButton.dataset.id}&action=follow-novel`);
}

function unfollowButtonClick() {
    const unfollowButton = this;
    const followButton = unfollowButton.parentNode.querySelector('[data-action="follow"]');

    const request = new XMLHttpRequest();
    request.open('POST', '/theo-doi', true);
    request.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8')
    request.onload = function () {
        if (request.status >= 400) {
            alert('Có lỗi xảy ra, vui lòng thử lại sau!');
            return;
        }
        const response = JSON.parse(request.responseText);
        if (response.status === 'success') {
            unfollowButton.classList.add('hidden');
            followButton.classList.remove('hidden');
        } else if (response.status === 'error') {
            alert(response.message);
        }
    }
    request.send(`id=${unfollowButton.dataset.id}&action=unfollow-novel`);
}

function assignFollowButtons() {
    const followButtons = document.querySelectorAll('[data-action="follow"]');
    followButtons.forEach(button => {
        button.addEventListener('click', followButtonClick);
    });
}

function assignUnfollowButtons() {
    const unfollowButtons = document.querySelectorAll('[data-action="unfollow"]');
    unfollowButtons.forEach(button => {
        button.addEventListener('click', unfollowButtonClick);
    });
}

assignFollowButtons();
assignUnfollowButtons();