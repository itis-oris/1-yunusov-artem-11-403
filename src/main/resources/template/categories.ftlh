<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Категории</title>
    <link rel="icon" href="${contextPath}/images/vision.png">
    <link rel="stylesheet" href="${contextPath}/css/header.css">
    <link rel="stylesheet" href="${contextPath}/css/categories.css">
</head>
<body>

<#include "header.ftlh">

<main class="main">
    <div class="main__content">

        <h1 class="categories__title">Категории</h1>

        <div class="categories__container">
            <#list categories as category>
                <a class="categories__card" href="${contextPath}/cards?categoryId=${category.id}">
                    <span class="categories__card-name">${category.name}</span>
                </a>
            </#list>

            <#if categories?size == 0>
                <p class="categories__empty">Категории отсутствуют.</p>
            </#if>
        </div>

    </div>
</main>
</body>