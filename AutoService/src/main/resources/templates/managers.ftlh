<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel='stylesheet' href='bootstrap/bootstrap.css'>
    <link rel='stylesheet' href='styles.css'>
    <link rel='stylesheet' href='../../bootstrap/bootstrap.css'>
    <link rel='stylesheet' href='../../styles.css'>
    <title>Исполнители</title>
</head>
<body>
    <#include 'layout/nav.ftlh'>
    <div id="autoservice-table">
        <table id="auto-table" border="1" cellspacing="0" cellpadding="1">
            <tr class="tableHeader">
                <td>Имя</td>
                <td>Описание</td>
                <td style="background-color: transparent; border: none"></td>
                <td style="background-color: transparent; border: none"></td>
            </tr>
            <#foreach manager in managers>
                <tr class="tableBody">
                    <td>${manager.name}</td>
                    <td style="font-weight: 200; font-size: 80%">${manager.description}</td>
                    <td>
                        <form id="form" action="/managers/edit/${manager.id}" method="post">
                            <input type="submit" style="width: 100%" value="Редактировать..." class="btn btn-success btn-sm"/>
                        </form>
                    </td>
                    <td>
                        <form id="form" action="/managers/delete/${manager.id}" method="post">
                            <input type="submit" style="width: 100%" value="Удалить..." class="btn btn-danger btn-sm"/>
                        </form>
                    </td>
                </tr>
            </#foreach>
        </table>
        <form id="form" action="/managers/edit/-1" method="post">
            <input style="width: 100%" type="submit" value="Добавить исполнителя..." class="btn btn-primary btn-sm"/>
        </form>
        <#if error??>
            <span style="margin-top: 10px; font-size: 125%; color: rgb(240, 50, 50)">${error}</span>
        </#if>
    </div>
    <#include 'layout/footer.ftlh'>
</body>
</html>