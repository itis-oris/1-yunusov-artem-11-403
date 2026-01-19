<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Карточки</title>
    <link rel="icon" href="${contextPath}/images/vision.png">
    <link rel="stylesheet" href="${contextPath}/css/header.css">
    <link rel="stylesheet" href="${contextPath}/css/cards.css">
</head>
<body>
    <#include "header.ftlh">
    <main class="main">
        <div class="main__content">
            <div class="cards">
                <#list cards as card>
                    <div class="cards__card">
                        <a class="cards__card-link" href="${contextPath}/card?id=${card.id}">
                            <img class="cards__card-image" src="${contextPath}/uploads/${card.imagePath!}" alt="${card.name!}">
                        </a>
                    </div>
                </#list>
                <#if cards?size == 0>
                    <p class="cards__empty">Карточки не найдены.</p>
                </#if>
            </div>
        </div>
    </main>
</body>
</html>