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
    toolbar.classList.add('no-transition');
    const isShow = getToolbarShowStatus();
    if (isShow) {
        toolbar.classList.add('show');
    } else {
        toolbar.classList.remove('show');
    }
}


window.onload = function () {
    const body = document.querySelector('body');
    body.style.display = 'none';
    reserveToolbarState();
    body.style.display = 'block';
};