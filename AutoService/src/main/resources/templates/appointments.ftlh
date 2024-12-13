<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel='stylesheet' href='bootstrap/bootstrap.css'>
    <link rel='stylesheet' href='styles.css'>
    <!-- в случае переадресаций -->
    <link rel='stylesheet' href='../../bootstrap/bootstrap.css'>
    <link rel='stylesheet' href='../../styles.css'>
    <title>Записи</title>
</head>
<body>
    <#include 'layout/nav.ftlh'>
        <div id="autoservice-table">
        <#if role == "ADMIN">
            <div style="margin-bottom: 3px">
                <span style="font-size: 115%"><b>Поиск по критериям:</b></span>
                <form id="form" action="/appointments/by-criteria" method="post">
                    <table id="criteria" border="0" style="width: 400px">
                        <tr>
                            <td>Исполнитель:</td>
                            <td>
                                <select style="margin-top: 3px; border-radius: 5px; padding: 3px; padding-left: 5px; padding-right: 5px" name="managerId">
                                    <option selected value="-1"></option>
                                    <#list managers! as manager>
                                        <option value="${manager.id}">${manager.name}</option>
                                    </#list>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>ФИО Заказчика:</td>
                            <td><input type="text" style="padding: 3px; border-radius: 5px" name="clientFio"></td>
                        </tr>
                        <tr>
                            <td>Телефон Заказчика:</td>
                            <td><input type="text" style="padding: 3px; border-radius: 5px" name="clientPhone"></td>
                        </tr>
                        <tr>
                            <td>Дата с:</td>
                            <td><input type="datetime-local" max="9999-12-31" style="padding: 3px; border-radius: 5px" name="fromDate"></td>
                        </tr>
                        <tr>
                            <td>Дата по:</td>
                            <td><input type="datetime-local" max="9999-12-31" style="padding: 3px; border-radius: 5px" name="toDate"></td>
                        </tr>
                    </table>
                    <input type="submit" style="width: 350px; margin-top: 5px; margin-bottom: 5px;" value="Найти!" class="btn btn-primary btn-sm"/>
            </form>
            </div>
        </#if>
        <table id="auto-table" border="1" cellspacing="0" cellpadding="1">
            <tr id="tr" class="tableHeader">
                <#if role == "ADMIN">
                    <td>ФИО заказчика</td>
                    <td>Телефон заказчика</td>
                </#if>
                <td>Наименование</td>
                <td>Продолжительность</td>
                <td>Исполнитель</td>
                <td>Марка машины</td>
                <td>Номер машины</td>
                <td style="padding-left: 30px; padding-right: 30px;">Время</td>
                <td></td>
                <td></td>
            </tr>
            <#foreach appointment in appointments>
                <tr class="tableBody">
                    <#if role == "ADMIN">
                        <td>${appointment.userFio}</td>
                        <td>${appointment.userPhone}</td>
                    </#if>
                    <td>${appointment.productName}</td>
                    <td>${appointment.duration} мин.</td>
                    <td>${appointment.managerName}</td>
                    <td>${appointment.carBrand}</td>
                    <td>${appointment.carNumber}</td>
                    <td>${appointment.startTimeStr}</td>
                    <td>
                        <form id="form" action="/appointments/edit/${appointment.id}/${appointment.productId}" method="post">
                            <input type="submit" style="width: 100%" value="Редактировать..." class="btn btn-primary btn-sm"/>
                        </form>
                    </td>
                    <td>
                        <form id="form" action="/appointments/delete/${appointment.id}" method="post">
                            <input type="submit" style="width: 100%" value="Отменить..." class="btn btn-danger btn-sm"/>
                        </form>
                    </td>
                </tr>
            </#foreach>
        </table>
    </div>
    <#include 'layout/footer.ftlh'>
</body>
</html>