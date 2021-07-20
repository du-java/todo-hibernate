<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add Task</title>
</head>
<body>
<h1>Create task</h1>
<form action="/todo/task/create" method="post">
    <table>
        <tr>
            <td>Enter date</td>
            <td><input type="date" name="date"></td>
        </tr>
        <tr>
            <td>Enter description</td>
            <td><input type="text" name="desc"></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" value="Create"></td>
        </tr>
    </table>
</form>
</body>
</html>