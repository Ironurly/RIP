<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel='stylesheet' href='bootstrap/bootstrap.css'>
    <link rel='stylesheet' href='styles.css'>
    <link rel='stylesheet' href='../../bootstrap/bootstrap.css'>
    <link rel='stylesheet' href='../../styles.css'>
    <title>Услуги</title>
</head>
<body>
    <#include 'layout/nav.ftlh'>
    <#if role != "ADMIN">
        <div style="padding-top: 20px; padding-left: 20px;">
            BDV Автосервис предлагает целый спектр разнообразных услуг, с которыми можете ознакомиться ниже:
        </div>
    </#if>
    <div id="autoservice-table">
        <table id="auto-table" border="1" cellspacing="0" cellpadding="1">
            <tr class="tableHeader">
                <td>Наименование</td>
                <td>Продолжительность</td>
                <td>Исполнитель</td>
                <td>Цена</td>
                <#if role == "ADMIN">
                    <td style="background-color: transparent; border: none"></td>
                    <td style="background-color: transparent; border: none"></td>
                </#if>
                <#if role == "USER">
                    <td style="background-color: transparent; border: none"></td>
                </#if>
            </tr>
            <#foreach product in products>
                <tr class="tableBody">
                    <td>${product.name}</td>
                    <td>${product.duration} мин.</td>
                    <td>${product.manager}<br><span style="font-size: 80%">${product.managerDescription}</span></td>
                    <td>${product.cost} ₽</td>
                    <#if role == "ADMIN">
                        <td>
                            <form id="form" action="/products/edit/${product.id}" method="post">
                                <input type="submit" value="Редактировать..." class="btn btn-success btn-sm"/>
                            </form>
                        </td>
                        <td>
                            <form id="form" action="/products/delete/${product.id}" method="post">
                                <input type="submit" value="Удалить..." class="btn btn-danger btn-sm"/>
                            </form>
                        </td>
                    </#if>
                    <#if role == "USER">
                        <td>
                            <form id="form" action="/appointments/edit/-1/${product.id}" method="post">
                                <input type="submit" value="Записаться..." class="btn btn-primary btn-sm"/>
                            </form>
                        </td>
                    </#if>
                </tr>
            </#foreach>
        </table>
        <#if role == "ADMIN">
            <form id="form" action="/products/edit/-1" method="post">
                <input style="width: 100%" type="submit" value="Добавить услугу..." class="btn btn-primary"/>
            </form>
        </#if>
        <#if error??>
            <span style="margin-top: 10px; font-size: 125%; color: rgb(240, 50, 50)">${error}</span>
        </#if>
    </div>
    <#include 'layout/footer.ftlh'>
</body>
</html>