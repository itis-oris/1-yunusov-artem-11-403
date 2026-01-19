<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Редактировать профиль</title>
    <link rel="icon" href="${contextPath}/images/vision.png">
    <link rel="stylesheet" href="${contextPath}/css/header.css">
    <link rel="stylesheet" href="${contextPath}/css/back_button.css">
    <link rel="stylesheet" href="${contextPath}/css/profile_edit.css">
</head>
<body>
<#include "header.ftlh">
<main class="main">
    <div class="main__content">
        <#include "back_button.ftlh">

        <div class="profile-edit">

            <h1 class="profile-edit__title">Изменение профиля</h1>

            <#if errormessage??>
                <p class="profile-edit__error">${errormessage}</p>
            </#if>

            <form class="profile-edit__form" action="${contextPath}/profile/edit" method="post" enctype="multipart/form-data">

                <input class="profile-edit__hidden-input" type="hidden" name="avatarPath" value="${user.avatarPath!''}">

                <div class="profile-edit__avatar-container">
                    <div class="profile-edit__avatar-wrapper">
                        <#if user.avatarPath?has_content>
                            <img id="previewImage" class="profile-edit__avatar" src="${contextPath}/uploads/${user.avatarPath}" alt="Аватар">
                        <#else>
                            <img id="previewImage" class="profile-edit__avatar" src="${contextPath}/images/emptyProfile.jpg" alt="Нет аватара">
                        </#if>
                    </div>
                    <label class="profile-edit__avatar-label">
                        Изменить
                        <input id="imageInput" type="file" name="avatar" class="profile-edit__avatar-input" accept="image/*">
                    </label>
                </div>

                <label class="profile-edit__field">
                    <span class="profile-edit__label">Имя пользователя</span>
                    <input class="profile-edit__input" type="text" name="username" value="${user.username!''}" placeholder="Придумайте уникальное имя" required>
                </label>

                <label class="profile-edit__field">
                    <span class="profile-edit__label">Email</span>
                    <input class="profile-edit__input" type="email" name="email" value="${user.email!''}" placeholder="example@gmail.com" required>
                </label>

                <label class="profile-edit__field">
                    <span class="profile-edit__label">Имя</span>
                    <input class="profile-edit__input" type="text" name="firstName" value="${user.firstName!''}" placeholder="Ваше имя" required>
                </label>

                <label class="profile-edit__field">
                    <span class="profile-edit__label">Фамилия</span>
                    <input class="profile-edit__input" type="text" name="lastName" value="${user.lastName!''}" placeholder="Ваша фамилия" required>
                </label>

                <label class="profile-edit__field">
                    <span class="profile-edit__label">Описание</span>
                    <textarea class="profile-edit__textarea" name="description" placeholder="Расскажите о себе">${user.description!''}</textarea>
                </label>

                <button class="profile-edit__submit" type="submit">Сохранить изменения</button>

            </form>

            <p class="profile-edit__links">
                <a class="profile-edit__link" href="${contextPath}/profile/change-password">Сменить пароль</a>
                <span> | </span>
                <a class="profile-edit__link" href="${contextPath}/profile">Вернуться в профиль</a>
            </p>

        </div>
    </div>
</main>
<script src="${contextPath}/js/imageInput.js"></script>
<script src="${contextPath}/js/backButton.js"></script>
</body>
</html>