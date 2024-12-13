<nav id="autoservice-nav" class="navbar navbar-expand-lg navbar-light">
    <div class="navbar-nav">
        <a <#if current_page == "index"> id="active" </#if> class="nav-item nav-link" href="/index">
            Главная
        </a>
        <#if role == "ADMIN">
        <a <#if current_page == "managers"> id="active" </#if> class="nav-item nav-link" href="/managers">
            Исполнители
        </a>
        </#if>
        <a <#if current_page == "products"> id="active" </#if> class="nav-item nav-link" href="/products">
            Услуги
        </a>
        <#if role != "GUEST">
            <a <#if current_page == "appointments"> id="active" </#if> class="nav-item nav-link" href="/appointments">
                Записи
            </a>
        </#if>
        <#if role == "GUEST">
            <a <#if current_page == "register"> id="active" </#if> class="nav-item nav-link" href="/register">
                Зарегистрироваться
            </a>
        </#if>
        <#if role == "GUEST">
            <a <#if current_page == "login"> id="active" </#if> class="nav-item nav-link" href="/login">
                Войти
            </a>
        </#if>
        <#if role != "GUEST">
            <a <#if current_page == "logout"> id="active" </#if> class="nav-item nav-link" href="/logout">
                Выйти
            </a>
        </#if>
    </div>
    <#if role != "GUEST">
    <div id="username">
        ${name}
    </div>
    </#if>
</nav>