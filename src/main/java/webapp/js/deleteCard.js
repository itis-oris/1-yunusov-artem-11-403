document.getElementById("deleteForm").addEventListener("submit", (event) => {
    const confirmed = confirm('Удалить карточку?');
    if (!confirmed) {
        event.preventDefault();
    }
});