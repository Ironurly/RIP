<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel='stylesheet' href='../../bootstrap/bootstrap.css'>
    <link rel='stylesheet' href='../../styles.css'>
    <title>Редактирование исполнителей</title>
</head>
<body>
    <#include 'layout/nav.ftlh'>
    <form id="autoservice-form" action="/managers/save" method="post">
        <#if manager.id??>
            <input type="hidden" name="id" value="${manager.id}"/>
        </#if>
        <div>
            <span>ФИО:</span>
            <input type="text" name="name" value="${manager.name}"/>
        </div>
        <br/>
        <div>
            <span>Описание:</span>
            <input type="text" name="description" value="${manager.description}"/>
        </div>
        <br/>
        <input type="submit" style="width: 100%" value="Сохранить!" class="btn btn-primary"/>
    </form>
    <#include 'layout/footer.ftlh'>
</body>
</html>