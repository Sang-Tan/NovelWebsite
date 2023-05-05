/**
 *
 *
 * @param seenBtns : Element[] of button contain notification id need to delete
 */
deleteNotification = seenBtns => {
    if (seenBtns.length === 0) {
        alert('Không có thông báo nào để đánh dấu đã xem!');
        return;
    }

    const notificationIds = [];

    for (let seenBtn of seenBtns) {
        notificationIds.push(seenBtn.dataset.notificationId);
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

            for (var seenBtn of seenBtns) {
                const notifItem = seenBtn.closest('.notif-item');
                notifItem.classList.add('deleted');
                setTimeout(() => {
                    notifItem.remove();
                }, 300);
            }
        } else if (response.status === 'error') {
            alert(response.message);
        }
    };
    request.send(`notificationsId=${notificationIds.join(',')}&action=delete-notification`)
}
deleteAllNotification = () => {
    const seenBtns = document.querySelectorAll('[data-action="seen"]');
    const seenBtnsElements = Array.from(seenBtns);
    deleteNotification(seenBtnsElements);
}