<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel='stylesheet' href='../../bootstrap/bootstrap.css'>
    <link rel='stylesheet' href='../../styles.css'>
    <title>Редактирование услуг</title>
</head>
<body>
    <#include 'layout/nav.ftlh'>
    <form id="autoservice-form" action="/products/save" method="post">
        <#if product.id??>
            <input type="hidden" name="id" value="${product.id}"/>
        </#if>
        <div>
            <span>Наименование:</span>
            <input type="text" name="name" value="${product.name!}"/>
        </div>
        <br/>
        <div>
            <span>Исполнитель:</span>
            <select style="margin-top: 3px" name="managerId">
                <#list managers! as manager>
                    <option value="${manager.id}">${manager.name}</option>
                </#list>
            </select>
        </div>
        <br/>
        <div>
            <span>Продолжительность (мин.):</span>
            <input type="text" name="duration" value="${product.duration!}"/>
        </div>
        <br/>
        <div>
            <span>Цена (₽):</span>
            <input type="text" name="cost" value="${product.cost!}"/>
        </div>
        <br/>
        <input type="submit" style="width: 100%" value="Сохранить!" class="btn btn-primary"/>
    </form>
    <#include 'layout/footer.ftlh'>
</body>
</html>