<!DOCTYPE html>
<html lang="ru" class="html">
<head class="head">
    <meta charset="UTF-8" class="meta">
    <title class="title">Редактировать карточку</title>
    <link rel="icon" href="${contextPath}/images/vision.png">
    <link rel="stylesheet" href="${contextPath}/css/header.css">
    <link rel="stylesheet" href="${contextPath}/css/back_button.css">
    <link rel="stylesheet" href="${contextPath}/css/card_edit.css">
</head>
<body class="body">
<#include "header.ftlh">

<main class="main">
    <div class="main__content">
        <#include "back_button.ftlh">

        <div class="card-edit">
            <form class="card-edit__form" action="${contextPath}/card/edit" method="post" enctype="multipart/form-data">

                <input class="card-edit__hidden-input" type="hidden" name="imagePath" value="${card.imagePath!''}">
                <input class="card-edit__hidden-input" type="hidden" name="id" value="${card.id!''}">

                <div class="card-edit__left">
                    <div class="card-edit__image-container">
                        <img class="card-edit__image" id="previewImage"
                             src="${contextPath}/uploads/${card.imagePath!''}"
                             alt="Превью изображения">
                    </div>
                    <label class="card-edit__file-label">
                        Выберите изображение
                        <input class="card-edit__file-input" type="file" id="imageInput" name="image" accept="image/*">
                    </label>
                </div>

                <div class="card-edit__right">
                    <h1 class="card-edit__title">Редактировать карточку</h1>

                    <#if errormessage??>
                        <p class="card-edit__error">${errormessage}</p>
                    </#if>

                    <label class="card-edit__field">
                        <span class="card-edit__field-label">Название:</span>
                        <input class="card-edit__input-text" type="text" name="name" value="${card.name!''}" placeholder="Введите название" required>
                    </label>

                    <label class="card-edit__field">
                        <span class="card-edit__field-label">Описание:</span>
                        <textarea class="card-edit__textarea" name="description" placeholder="Добавьте описание" required>${card.description!''}</textarea>
                    </label>

                    <label class="card-edit__field">
                        <span class="card-edit__field-label">Категория:</span>
                        <select name="categoryId" class="card-edit__select">
                            <option value="0" <#if (card.categoryId!"")?string == "">selected</#if>>— выберите категорию —</option>
                            <#list categories as cat>
                                <option value="${cat.id?string}" <#if (card.categoryId!"")?string == cat.id?string>selected</#if>>${cat.name}</option>
                            </#list>
                        </select>
                    </label>

                    <button class="card-edit__submit" type="submit">Сохранить</button>
                </div>
            </form>
            <form class="card-edit__form-delete" id="deleteForm" action="${contextPath}/card/delete" method="post">
                <input class="card-edit__hidden-input" type="hidden" name="id" value="${card.id!''}">
                <button class="card-edit__delete" type="submit">Удалить карточку</button>
            </form>
        </div>
    </div>
</main>
<script src="${contextPath}/js/imageInput.js"></script>
<script src="${contextPath}/js/backButton.js"></script>
<script src="${contextPath}/js/deleteCard.js"></script>
</body>
</html>