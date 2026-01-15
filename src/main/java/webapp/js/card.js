const body = document.body;
const contextPath = body.dataset.contextPath;
const cardId = body.dataset.cardId;
const userUsername = body.dataset.userUsername;
const userAvatarPath = body.dataset.userAvatarPath;

document.getElementById("likeBtn").addEventListener("click", () => {
    toggleLike(cardId);
});
document.getElementById("commentBtn").addEventListener("click", () => {
    toggleComments()
});
document.getElementById("saveBtn").addEventListener("click", () => {
    toggleSave(cardId)
});
document.getElementById("commentSendBtn").addEventListener("click", () => {
    addComment(cardId);
});
document.getElementById("commentsList").addEventListener("click", e => {
    const btn = e.target.closest(".card__comment-delete");
    if (!btn) return;
    const commentId = btn.dataset.commentId;
    deleteComment(commentId, cardId);
});

function toggleComments() {
    const block = document.getElementById("cardRight");
    block.style.display = block.style.display === "flex" ? "none" : "flex";
}

function toggleLike(cardId) {
    const icon = document.getElementById("likeIcon");
    const count = document.getElementById("likesCount");
    const isLiked = icon.src.includes("like.png");
    const action = isLiked ? "unlike" : "like";

    fetch(contextPath + "/like", {
        method: "POST",
        headers: {"Content-Type": "application/x-www-form-urlencoded"},
        body: "action=" + action + "&cardId=" + cardId
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                icon.src = data.liked ? contextPath + "/images/like.png" : contextPath + "/images/likeEmpty.png";
                count.textContent = data.likesCount;
            }
        });
}

function toggleSave(cardId) {
    const icon = document.getElementById("saveIcon");
    const isSaved = icon.src.includes("save.png");
    const action = isSaved ? "unsave" : "save";

    fetch(contextPath + "/save", {
        method: "POST",
        headers: {"Content-Type": "application/x-www-form-urlencoded"},
        body: "action=" + action + "&cardId=" + cardId
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                icon.src = data.saved ? contextPath + "/images/save.png" : contextPath + "/images/saveEmpty.png";
            }
        });
}
function addComment(cardId) {
    const textArea = document.getElementById("newCommentText");
    const content = textArea.value.trim();
    if (!content) return;

    fetch(contextPath + "/comment", {
        method: "POST",
        headers: {"Content-Type": "application/x-www-form-urlencoded"},
        body: `action=add&cardId=` + cardId + `&content=` + encodeURIComponent(content)
    })
        .then(response => response.json())
        .then(response => {
            if(response.success) {

                const div = document.createElement("div");
                div.id = "comment-" + response.comment.id;
                div.classList.add("card__comment");
                div.innerHTML = `<div class="card__comment-top">
                                        <div class="card__comment-author">
                                            <img class="card__comment-avatar" src="` + contextPath + `/uploads/` + userAvatarPath + `" alt="Аватарка" />
                                            <span class="card__comment-author">` + userUsername + `</span>
                                        </div>
                                        <button class="card__comment-delete" data-comment-id="` + response.comment.id + `" title="Удалить">
                                            <img class="card__comment-delete-icon" src="`+ contextPath + `/images/delete.png" alt="Удалить">
                                        </button>
                                     </div>
                                     <p class="card__comment-text">` + content + `</p>`;

                document.getElementById("commentsList").appendChild(div);

                const count = document.getElementById("commentsCount");
                count.textContent = response.commentsCount;

                textArea.value = "";
            } else alert("Ошибка: " + response.error);
        });
}

function deleteComment(commentId, cardId) {
    fetch(contextPath + "/comment", {
        method: "POST",
        headers: {"Content-Type": "application/x-www-form-urlencoded"},
        body: `action=delete&commentId=` + commentId + `&cardId=` + cardId
    })
        .then(response => response.json())
        .then(response => {
            if(response.success) {
                const div = document.getElementById("comment-" + commentId);
                if(div) div.remove();

                const count = document.getElementById("commentsCount");
                count.textContent = response.commentsCount;
            } else alert("Ошибка при удалении: " + (response.error || "unknown"));
        });
}