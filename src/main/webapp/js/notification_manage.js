/**
 *
 *
 * @param seenBtns : array of button contain notification id need to delete
 */
deleteNotification = seenBtns => {
    if (seenBtns.length === 0) {
        alert('Không có thông báo nào để đánh dấu đã xem!');
        return;
    }

    const notificationIds = [];
    if (seenBtns instanceof NodeList) {
        for (var seenBtn of seenBtns) {
            notificationIds.push(seenBtn.dataset.notificationId);
        }
    }
    else {
        notificationIds.push(seenBtns.dataset.notificationId);
    }






    const request = new XMLHttpRequest();
    request.open('POST', '/thong-bao', true);
    request.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8');
    request.onload = () => {
        if (request.status >= 400) {
            alert('Có lỗi xảy ra, vui lòng thử lại sau!');
            return;
        }
        const response = JSON.parse(request.responseText);
        if (response.status === 'success') {
            if (seenBtns instanceof NodeList) {
                for (var seenBtn of seenBtns) {
                    seenBtn.parentElement.remove();
                }
            }
            else {
                seenBtns.parentElement.remove();
            }
        } else if (response.status === 'error') {
            alert(response.message);
        }
    };
    request.send(`notificationsId=${notificationIds.join(',')}&action=delete-notification`)
}
deleteAllNotification = () => {
    const seenBtns = document.querySelectorAll('[data-action="seen"]');
    deleteNotification(seenBtns);
}