<!DOCTYPE html>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Vision</title>
    <link rel="stylesheet" href="${contextPath}/css/header.css">
    <link rel="stylesheet" href="${contextPath}/css/index.css">
    <link rel="icon" href="${contextPath}/images/vision.png">
</head>

<body>

<#include "header.ftlh">

<video class="video" autoplay muted loop playsinline>
    <source src="${contextPath}/videos/background.mp4" type="video/mp4">
</video>
<div class="overlay"></div>
<main class="main">
    <div class="main__content ">

        <h1 class="index__title">Vision</h1>
        <p class="index__subtitle">Collect ideas. Shape your vision.</p>

        <a href="${contextPath}/cards" class="index__button">Начать исследовать</a>

    </div>
</main>
</body>
</html>