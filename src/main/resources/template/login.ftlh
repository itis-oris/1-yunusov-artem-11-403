<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Вход</title>
    <link rel="icon" href="${contextPath}/images/vision.png">
    <link rel="stylesheet" href="${contextPath}/css/header.css">
    <link rel="stylesheet" href="${contextPath}/css/back_button.css">
    <link rel="stylesheet" href="${contextPath}/css/login.css">
</head>

<body>
<main class="main">
    <div class="main__content">
        <#include "back_button.ftlh">
        <div class="login">

            <h1 class="login__title">С возвращением в Vision</h1>
            <p class="login__subtitle">Войдите в аккаунт, чтобы продолжить</p>

            <#if errormessage??>
                <p class="login__error">${errormessage}</p>
            </#if>

            <form class="login__form" action="${contextPath}/login" method="post">

                <label class="login__field">
                    <span class="login__label">Имя пользователя</span>
                    <input class="login__input" type="text" name="username" value="${(username)!}" placeholder="Введите имя пользователя" required>
                </label>

                <label class="login__field">
                    <span class="login__label">Пароль</span>
                    <input class="login__input" type="password" name="password" value="${(password)!}" placeholder="Введите пароль" required>
                </label>

                <button class="login__submit" type="submit">Войти</button>
            </form>

            <p class="login__signup-text">
                Ещё нет аккаунта?
                <a class="login__signup-link" href="${contextPath}/signup">Зарегистрируйтесь</a>
            </p>

        </div>

    </div>
</main>
<script src="${contextPath}/js/backButton.js"></script>
</body>
</html>