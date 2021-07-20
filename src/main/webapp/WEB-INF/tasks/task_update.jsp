<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Update Task</title>
</head>
<body>
<h1>Update task</h1>
<form action="/todo/task/update" method="post">
    <table>
        <tr>
            <td>ID</td>
            <td><input type="text" name="id" value='<c:out value="${task.id}"/>' readonly></td>
        </tr>
        <tr>
            <td>Update date</td>
            <td><input type="date" name="date" value='<c:out value="${task.date}"/>'></td>
        </tr>
        <tr>
            <td>Update description</td>
            <td><input type="text" name="desc" value='<c:out value="${task.desc}"/>'></td>
        </tr>
        <tr>
            <td>Mark done</td>
            <td><input type="checkbox" name="isDone"
                <c:if test="${task.isDone}">checked</c:if>
                >
            </td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" value="Update"></td>
        </tr>
    </table>
</form>
</body>
</html>