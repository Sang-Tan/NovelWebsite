/**
 *
 *
 * @param notificationIds : array of notification id need to delete
 */
deleteNotification = notificationIds => {

    if (notificationIds.length === 0) {
        alert('Không có thông báo nào để đánh dấu đã xem!');
        return;
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
            for(var notificationId of notificationIds){
                const notification = document.querySelector(`[data-notification-id="${notificationId}"]`);
                notification.parentElement.remove();
            }
        } else if (response.status === 'error') {
            alert(response.message);
        }
    };
    request.send(`notificationsId=${notificationIds.join(',')}&action=delete-notification`)
}
deleteThisNotification = element =>{
    const notificationIds = [];
    notificationIds.push(element.dataset.notificationId);
    deleteNotification(notificationIds);
}
deleteAllNotification = () => {
    const seenBtns = document.querySelectorAll('[data-action="seen"]');
    const notificationIds = [];
    for(var seenBtn of seenBtns){
        notificationIds.push(seenBtn.dataset.notificationId);
    }
    deleteNotification(notificationIds);
}