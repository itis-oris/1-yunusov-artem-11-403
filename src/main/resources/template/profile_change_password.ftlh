<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Сменить пароль</title>
    <link rel="icon" href="${contextPath}/images/vision.png">
    <link rel="stylesheet" href="${contextPath}/css/header.css">
    <link rel="stylesheet" href="${contextPath}/css/back_button.css">
    <link rel="stylesheet" href="${contextPath}/css/profile_change_password.css">
</head>
<body>

<#include "header.ftlh">

<main class="main">
    <div class="main__content">
        <#include "back_button.ftlh">

        <div class="change-password">
            <h1 class="change-password__title">Сменить пароль</h1>
            <p class="change-password__subtitle">Введите данные для обновления пароля</p>

            <#if errormessage??>
                <p class="change-password__error">${errormessage}</p>
            </#if>

            <form class="change-password__form" action="${contextPath}/profile/change-password" method="post">

                <label class="change-password__field">
                    <span class="change-password__label">Текущий пароль</span>
                    <input class="change-password__input" type="password" name="currentPassword" placeholder="Введите текущий пароль" required>
                </label>

                <label class="change-password__field">
                    <span class="change-password__label">Новый пароль</span>
                    <input class="change-password__input" type="password" name="newPassword" placeholder="Минимум 7 символов" required>
                </label>

                <label class="change-password__field">
                    <span class="change-password__label">Подтвердите новый пароль</span>
                    <input class="change-password__input" type="password" name="confirmPassword" placeholder="Повторите новый пароль" required>
                </label>

                <button class="change-password__submit" type="submit">Подтвердить</button>
            </form>

            <p class="change-password__cancel">
                <a class="change-password__cancel-link" href="${contextPath}/profile">Отменить и вернуться в профиль</a>
            </p>
        </div>

    </div>
</main>
<script src="${contextPath}/js/backButton.js"></script>
</body>
</html>