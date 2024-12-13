<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel='stylesheet' href='../../../bootstrap/bootstrap.css'>
    <link rel='stylesheet' href='../../../styles.css'>
    <title>Редактировать запись</title>
</head>
<body>
    <#include 'layout/nav.ftlh'>
    <form id="autoservice-form" action="/appointments/save" method="post">
        <input type="hidden" name="id" value="${appointment.id!}"/>
        <input type="hidden" name="productId" value="${appointment.productId!}"/>
        <input type="hidden" name="userId" value="${appointment.userId!}"/>
        <div>
            <span>Наименование:</span>
            <span style="float: right">${appointment.productName!}</span>
        </div>
        <br/>
        <div>
            <span>Исполнитель:</span>
            <span style="float: right">${appointment.managerName!}</span>
        </div>
        <br/>
        <div>
            <span>Продолжительность:</span>
            <span style="float: right">${appointment.duration!} мин.</span>
        </div>
        <br/>
        <div>
            <span>Цена:</span>
            <span style="float: right">${appointment.cost!} ₽</span>
        </div>
        <br/>
        <div>
            <span>Марка машины:</span>
            <input required type="text" name="carBrand" value="${appointment.carBrand!}"/>
        </div>
        <br/>
        <div>
            <span>Номер машины:</span>
            <input required type="text" name="carNumber" value="${appointment.carNumber!}"/>
        </div>
        <br/>
        <div>
            <span>Дата и время:</span>
            <input required type="datetime-local" max="9999-12-31" name="startTimeStr" value="${appointment.startTimeStr!}"/>
        </div>
        <br/>
        <input type="submit" style="width: 100%" value="Сохранить!" class="btn btn-primary"/>
    </form>
    <#include 'layout/footer.ftlh'>
</body>
</html>