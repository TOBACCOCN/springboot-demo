<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>freemarker-users</title>
</head>
<body>
<div>
    <ul>
        <#list users as user>
            <li>
                <span>${user.id}</span>-
                <span>${user.name}</span>-
                <span>${user.age}</span>-
                <span>${user.address}</span>
            </li>
        </#list>
    </ul>
</div>
</body>
</html>