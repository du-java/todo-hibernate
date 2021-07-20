<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Tasks</title>
</head>
<body>
<h1>Tasks</h1>
<c:if test="${tasks!=null && tasks.size() > 0}">
    <table>
        <th>
        <td>ID</td>
        <td>Date</td>
        <td>Desc</td>
        <td>isDone</td>
        <td></td>
        </th>
        <c:forEach items="${tasks}" var="task">
            <tr>
                <td>
                    <c:out value="${task.id}"/>
                </td>
                <td>
                    <c:out value="${task.date}"/>
                </td>
                <td>
                    <c:out value="${task.desc}"/>
                </td>
                <td>
                    <c:out value="${task.isDone}"/>
                </td>
                <td>
                    <a href='/todo/task/update?id=<c:out value="${task.id}"/>'>Update</a><br>
                    <a href='/todo/task/delete?id=<c:out value="${task.id}"/>'>Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>
<a href="/todo/task/create">Create task</a>
</body>
</html>