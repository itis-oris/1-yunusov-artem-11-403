<!DOCTYPE html>
<html lang="ru" class="html">
<head class="head">
    <meta charset="UTF-8" class="meta">
    <title class="title">Создать карточку</title>
    <link rel="icon" href="${contextPath}/images/vision.png">
    <link rel="stylesheet" href="${contextPath}/css/header.css">
    <link rel="stylesheet" href="${contextPath}/css/back_button.css">
    <link rel="stylesheet" href="${contextPath}/css/card_create.css">
</head>
<body class="body">
<#include "header.ftlh">

<main class="main">
    <div class="main__content">
        <#include "back_button.ftlh">

        <div class="card-create">
            <form class="card-create__form" action="${contextPath}/card/create" method="post" enctype="multipart/form-data">
                <input class="card-create__hidden-input" type="hidden" name="imagePath" value="${card.imagePath!''}">

                <div class="card-create__left">
                    <div class="card-create__image-container">
                        <img class="card-create__image" id="previewImage" src="${contextPath}/images/emptyPhoto.jpg" alt="Превью изображения">
                    </div>
                    <label class="card-create__file-label">
                        Выбрать изображение
                        <input class="card-create__file-input" type="file" id="imageInput" name="image" accept="image/*">
                    </label>
                </div>

                <div class="card-create__right">
                    <h1 class="card-create__title">Создать карточку</h1>

                    <#if errormessage??>
                        <p class="card-create__error">${errormessage}</p>
                    </#if>

                    <label class="card-create__field">
                        <span class="card-create__field-label">Название:</span>
                        <input class="card-create__input-text" type="text" name="name" value="${card.name!''}" placeholder="Введите название" required>
                    </label>

                    <label class="card-create__field">
                        <span class="card-create__field-label">Описание:</span>
                        <textarea class="card-create__textarea" name="description" placeholder="Добавьте описание" required>${card.description!''}</textarea>
                    </label>

                    <label class="card-create__field">
                        <span class="card-create__field-label">Категория:</span>
                        <select name="categoryId" class="card-create__select">
                            <option value="0" <#if (card.categoryId!"")?string == "">selected</#if>>— выберите категорию —</option>
                            <#list categories as cat>
                                <option value="${cat.id?string}" <#if (card.categoryId!"")?string == cat.id?string>selected</#if>>${cat.name}</option>
                            </#list>
                        </select>
                    </label>

                    <button class="card-create__submit" type="submit">Создать</button>
                </div>

            </form>
        </div>

    </div>
</main>
<script src="${contextPath}/js/imageInput.js"></script>
<script src="${contextPath}/js/backButton.js"></script>
</body>
</html>