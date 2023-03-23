function initTree() {
    const toggles = document.querySelectorAll('.tree-toggle');
    toggles.forEach(toggle => {
        toggle.addEventListener('click', (e) => {
            const parent = e.target.parentElement;
            parent.classList.toggle('expand');
        });
    });
}

initTree();