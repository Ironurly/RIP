<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel='stylesheet' href='bootstrap/bootstrap.css'>
    <link rel='stylesheet' href='styles.css'>
    <title>Регистрация</title>
</head>
<body>
    <#include 'layout/nav.ftlh'>
    <form id="autoservice-form" action="/register" method="post">
        <div>
            <span>Логин:</span>
            <input type="text" name="login" required/>
        </div>
        <br/>
        <div>
            <span>ФИО:</span>
            <input type="text" name="name" required/>
        </div>
        <br/>
        <div>
            <span>Пароль:</span>
            <input type="password" name="password" required/>
        </div>
        <br/>
        <div>
            <span>Телефон:</span>
            <input type="text" name="phone" required/>
        </div>
        <br/>
        <input type="submit" style="width: 100%" value="Зарегистрироваться!" class="btn btn-primary"/>
        <#if error??>
            <span style="color: rgb(240, 50, 50)">${error}</span>
        </#if>
        <#if ok??>
            <span style="color: rgb(25, 200, 25)">${ok}</span>
        </#if>
    </form>
    <#include 'layout/footer.ftlh'>
</body>
</html>