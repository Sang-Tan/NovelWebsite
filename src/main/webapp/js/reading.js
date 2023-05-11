function toggleNavSidebar() {
    const navSidebar = document.querySelector("#navigation-sidebar");
    navSidebar.classList.toggle("show");
}

function toggleToolbar() {
    const toolbar = document.getElementById("toolbar");
    const isShow = toolbar.classList.contains("show");
    toolbar.classList.toggle("show");

    setToolbarShowStatus(!isShow);
}

/**
 * @return {boolean}
 */
function getToolbarShowStatus() {
    let isToolbarShowString = localStorage.getItem('isToolbarShow');
    if (isToolbarShowString === null) {
        localStorage.setItem('isToolbarShow', 'true');
        isToolbarShowString = 'true';
    }

    return isToolbarShowString === 'true';
}

/**
 * @param isShow {boolean}
 */
function setToolbarShowStatus(isShow) {
    localStorage.setItem('isToolbarShow', isShow ? 'true' : 'false');
}

function reserveToolbarState() {
    const toolbar = document.getElementById('toolbar');
    if (!toolbar) {
        return;
    }
    const isShow = getToolbarShowStatus();
    toolbar.classList.add('no-transition');
    if (isShow) {
        toolbar.classList.add('show');
    } else {
        toolbar.classList.remove('show');
    }
    setTimeout(() => {
        toolbar.classList.remove('no-transition');
        toolbar.style.opacity = '1';
    }, 5);
}


window.onload = function () {
    const body = document.querySelector('body');
    reserveToolbarState();

};
var ipAddress;
$.getJSON("https://api.ipify.org?format=json", function(data) {
    ipAddress = data.ip;
});

const readingTime = 180000; // 60000 miliseconds = 1 minute
function finishReading() {
    const chapterId = document.getElementById('readingScript').dataset.chapterId;
    const request = new XMLHttpRequest();
    request.open('POST', '/view-novel', true);
    request.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8')
    request.send(`action=viewed&chapterId=${chapterId}&ipAddress=${ipAddress}`);
}
setTimeout(finishReading, readingTime);