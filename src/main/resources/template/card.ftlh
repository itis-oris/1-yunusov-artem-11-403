<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Просмотр карточки</title>
    <link rel="icon" href="${contextPath}/images/vision.png">
    <link rel="stylesheet" href="${contextPath}/css/header.css">
    <link rel="stylesheet" href="${contextPath}/css/back_button.css">
    <link rel="stylesheet" href="${contextPath}/css/card.css">
</head>
<body data-context-path="${contextPath}"
      data-card-id="${card.id}"
      data-user-username="${user.username!""}"
      data-user-avatar-path="${user.avatarPath!""}">

<#include "header.ftlh">

<main class="main">
    <div class="main__content">
        <#include "back_button.ftlh">

        <div class="card" data-card-id="${card.id}">

            <div class="card__left">

                <div class="card__image-container">
                    <img class="card__image" src="${contextPath}/uploads/${card.imagePath!""}" alt="Изображение">
                </div>

                <div class="card__left-footer">

                    <div class="card__author">
                        <img class="card__author-avatar"
                             src="<#if author.avatarPath?? && author.avatarPath?has_content>${contextPath}/uploads/${author.avatarPath}
                                    <#else>${contextPath}/images/emptyProfile.jpg</#if>"
                                    alt="Автор">
                        <p class="card__author-username">${author.username!""}</p>
                    </div>

                    <div class="card__actions">
                        <button class="card__action-button" id="likeBtn" title="Лайк">
                            <img id="likeIcon" class="card__button-icon"
                                 src="${contextPath}/images/${likedByUser?string('like.png','likeEmpty.png')}">
                        </button>
                        <span id="likesCount">${likesCount}</span>

                        <button class="card__action-button" id="commentBtn" title="Коммент">
                            <img class="card__button-icon" src="${contextPath}/images/comment.png">
                        </button>
                        <span id="commentsCount">${commentsCount}</span>

                        <button class="card__action-button" id="saveBtn" title="Сохранить">
                            <img id="saveIcon" class="card__button-icon"
                                 src="${contextPath}/images/${savedByUser?string('save.png','saveEmpty.png')}">
                        </button>
                    </div>
                </div>
            </div>

            <div class="card__right" id="cardRight">

                <div class="card__info">
                    <h1 class="card__title">${card.name!""}</h1>
                    <p class="card__description">${card.description!""}</p>
                </div>

                <div class="card__comments-section" id="commentsSection">

                    <div class="card__comment-input">
                    <textarea id="newCommentText" class="card__comment-textarea" placeholder="Написать комментарий..."></textarea>
                        <button class="card__comment-send" id="commentSendBtn" title="Отправить">
                            <img class="card__comment-send-icon" src="${contextPath}/images/send.png">
                        </button>
                    </div>

                    <div class="card__comments-list" id="commentsList">
                        <#if comments??>
                            <#list comments as comment>
                                <div class="card__comment" id="comment-${comment.id}">
                                    <div class="card__comment-top">
                                        <div class="card__comment-author">
                                            <img class="card__comment-avatar"
                                                 src="<#if comment.avatarPath?? && comment.avatarPath?has_content>${contextPath}/uploads/${comment.avatarPath}
                                                  <#else> ${contextPath}/images/emptyProfile.jpg</#if>"
                                                 alt="Аватарка" >
                                            <span class="card__comment-author">${comment.username}</span>
                                        </div>
                                        <#if comment.userId?string == user.id?string>
                                            <button class="card__comment-delete" data-comment-id="${comment.id}" title="Удалить">
                                                <img class="card__comment-delete-icon" src="${contextPath}/images/delete.png" alt=="Удалить">
                                            </button>
                                        </#if>
                                    </div>
                                    <p class="card__comment-text">${comment.content}</p>
                                </div>
                            </#list>
                        <#else>
                            <p class="card__no-comments">Комментариев пока нет</p>
                        </#if>

                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
<script src="${contextPath}/js/backButton.js"></script>
<script src="${contextPath}/js/card.js"></script>
</body>
</html>