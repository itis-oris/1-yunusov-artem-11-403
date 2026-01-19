<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Регистрация </title>
    <link rel="icon" href="${contextPath}/images/vision.png">
    <link rel="stylesheet" href="${contextPath}/css/header.css">
    <link rel="stylesheet" href="${contextPath}/css/back_button.css">
    <link rel="stylesheet" href="${contextPath}/css/signup.css">
</head>

<body>
<main class="main">
    <div class="main__content">
        <#include "back_button.ftlh">
        <div class="signup">

            <h1 class="signup__title">Добро пожаловать в Vision</h1>
            <p class="signup__subtitle">Создайте аккаунт, чтобы начать путешествие</p>

            <#if errormessage??>
                <p class="signup__error">${errormessage}</p>
            </#if>

            <form class="signup__form" action="${contextPath}/signup" method="post">

                <label class="signup__field">
                    <span class="signup__label">Имя пользователя</span>
                    <input class="signup__input" type="text" name="username" value="${(user.username)!}" placeholder="Придумайте уникальное имя" required>
                </label>

                <label class="signup__field">
                    <span class="signup__label">Пароль</span>
                    <input class="signup__input" type="password" name="password" placeholder="Минимум 7 символов" required>
                </label>

                <label class="signup__field">
                    <span class="signup__label">Email</span>
                    <input class="signup__input" type="email" name="email" value="${(user.email)!}" placeholder="example@gmail.com" required>
                </label>

                <label class="signup__field">
                    <span class="signup__label">Имя</span>
                    <input class="signup__input" type="text" name="firstName" value="${(user.firstName)!}" placeholder="Ваше имя" required>
                </label>

                <label class="signup__field">
                    <span class="signup__label">Фамилия</span>
                    <input class="signup__input" type="text" name="lastName" value="${(user.lastName)!}" placeholder="Ваша фамилия" required>
                </label>

                <button class="signup__submit" type="submit">Создать аккаунт</button>
            </form>

            <p class="signup__login-text">
                Уже есть аккаунт?
                <a class="signup__login-link" href="${contextPath}/login">Войти</a>
            </p>

        </div>

    </div>
</main>
<script src="${contextPath}/js/backButton.js"></script>
</body>
</html>