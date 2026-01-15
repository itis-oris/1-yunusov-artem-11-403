<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Профиль пользователя</title>
    <link rel="icon" href="${contextPath}/images/vision.png">
    <link rel="stylesheet" href="${contextPath}/css/header.css">
    <link rel="stylesheet" href="${contextPath}/css/profile.css">
</head>
<body>
<#include "header.ftlh">
<main class="main">
    <div class="main__content">

        <section class="profile-section">
            <img class="profile-section__avatar"
                 src="<#if user.avatarPath?? && user.avatarPath?has_content> ${contextPath}/uploads/${user.avatarPath}
                      <#else>${contextPath}/images/emptyProfile.jpg</#if>"
                 alt="Фото профиля"/>
            <h2 class="profile-section__name">${user.firstName!""} ${user.lastName!""}</h2>
            <p class="profile-section__username">@${user.username!""}</p>
            <p class="profile-section__description">${user.description!""}</p>
            <div class="profile-section__actions">
                <a href="${contextPath}/profile/edit" class="profile-section__btn">Изменить</a>
                <a href="${contextPath}/logout" class="profile-section__btn profile-section__btn--logout">Выйти</a>
            </div>
        </section>

        <section class="library-section">
            <div class="library-section__tabs">
                <div id="createdTab" class="library-section__tab library-section__tab--active">Созданные</div>
                <div id="savedTab" class="library-section__tab">Добавленные</div>
            </div>

            <div id="createdCardsDiv" class="library-section__cards">
                <#list createdCards as card>
                    <div class="cards__card">
                        <a class="cards__card-link" href="${contextPath}/card/edit?id=${card.id}">
                            <img class="cards__card-image" src="${contextPath}/uploads/${card.imagePath!}" alt="${card.name!}">
                        </a>
                    </div>
                </#list>
                <#if createdCards?size == 0>
                    <p class="cards__empty">Созданные карточки отсутствуют.</p>
                </#if>
            </div>

            <div id="savedCardsDiv" class="library-section__cards" style="display:none">
                <#list savedCards as card>
                    <div class="cards__card">
                        <a class="cards__card-link" href="${contextPath}/card?id=${card.id}">
                            <img class="cards__card-image" src="${contextPath}/uploads/${card.imagePath!}" alt="${card.name!}">
                        </a>
                    </div>
                </#list>
                <#if savedCards?size == 0>
                    <p class="cards__empty">Добавленные карточки отсутствуют.</p>
                </#if>
            </div>
        </section>

    </div>
</main>
<script src="${contextPath}/js/profileLibrary.js"></script>
</body>
</html>