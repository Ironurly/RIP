<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel='stylesheet' href='bootstrap/bootstrap.css'>
    <link rel='stylesheet' href='styles.css'>
    <title>Вход</title>
</head>
<body>
    <#include 'layout/nav.ftlh'>
    <form id="autoservice-form" action="/login" method="post">
        <div>
            <span>Логин:</span>
            <input type="text" name="login"/>
        </div>
        <br/>
        <div>
            <span>Пароль:</span>
            <input type="password" name="password"/>
        </div>
        <br />
        <input type="submit" style="width: 100%" value="Войти!" class="btn btn-primary"/>
        <#if error??>
            <span style="color: rgb(240, 50, 50)">${error}</span>
        </#if>
    </form>
    <#include 'layout/footer.ftlh'>
</body>
</html>