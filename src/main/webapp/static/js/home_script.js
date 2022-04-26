const selectedElement = document.getElementById('sort_by_selector');
selectedElement.addEventListener('change', (event) => {
    const selectedSort = event.target.value;
    const sortByAnchorElement = document.getElementById('sort_by_anchor');
    const href = sortByAnchorElement.getAttribute('href');
    sortByAnchorElement.setAttribute('href', href + selectedSort);
    sortByAnchorElement.click();
});





