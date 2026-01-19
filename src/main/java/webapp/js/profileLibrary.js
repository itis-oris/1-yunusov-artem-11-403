const createdTab = document.getElementById('createdTab');
const savedTab = document.getElementById('savedTab');
const createdDiv = document.getElementById('createdCardsDiv');
const savedDiv = document.getElementById('savedCardsDiv');

createdTab.onclick = () => {
    createdDiv.style.display = 'flex';
    savedDiv.style.display = 'none';
    createdTab.classList.add('library-section__tab--active');
    savedTab.classList.remove('library-section__tab--active');
};

savedTab.onclick = () => {
    createdDiv.style.display = 'none';
    savedDiv.style.display = 'flex';
    savedTab.classList.add('library-section__tab--active');
    createdTab.classList.remove('library-section__tab--active');
};