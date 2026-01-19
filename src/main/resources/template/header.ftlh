<header class="header">
    <div class="header__content">

        <div class="header__logo">
            <a class="header__logo-link" href="${contextPath}" title="Главная">
                <img class="header__logo-image" src="${contextPath}/images/vision.png" alt="Логотип"/>
                <span class="header__logo-name">Vision</span>
            </a>
        </div>

        <div class="header__actions">

            <nav class="header__nav">
                <a class="header__link" href="${contextPath}/cards" title="Исследовать">
                    <img class="header__link-icon" src="${contextPath}/images/navigate.png" alt="Исследовать"/>
                </a>
                <a class="header__link" href="${contextPath}/categories" title="Категории">
                    <img class="header__link-icon" src="${contextPath}/images/categories.png" alt="Категории"/>
                </a>
                <a class="header__link" href="${contextPath}/card/create" title="Создать">
                    <img class="header__link-icon" src="${contextPath}/images/create.png" alt="Создать"/>
                </a>
            </nav>

            <form class="header__search" action="${contextPath}/cards" method="get">
                <input class="header__search-query" type="text" name="query" placeholder="Поиск..."
                       value="<#if query??>${query}
                                <#elseif category??>${category.name}</#if>"/>
                <button class="header__search-button" type="submit" title="Поиск">
                    <img class="header__search-icon" src="${contextPath}/images/search.png" alt="поиск"/>
                </button>
            </form>

        </div>

        <#if user??>
            <div class="header__profile">
                <a class = header__profile-link href="${contextPath}/profile" title="Профиль">
                    <img class ="header__profile-image" src="<#if (user.avatarPath?? && user.avatarPath?has_content)>${contextPath}/uploads/${user.avatarPath}
                                                        <#else>${contextPath}/images/emptyProfile.jpg</#if>"
                                                        alt="Профиль"/>
                </a>
            </div>
            <#else>
            <div class="header__auth">
                <a class="header__auth-login" href="${contextPath}/login">Войти</a>
                <a class="header__auth-signup" href="${contextPath}/signup">Регистрация</a>
            </div>
        </#if>

    </div>
</header>