<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="BASE" value="${pageContext.request.contextPath"/>

<html>
<head>
<title>Example</title>
</head>

<body>
    <table>
        <c:forEach var="example" items="${customerList}">
            <tr>
                <td>${example.name}</td>
                <td>${example.age}</td>
            </tr>
        </c:forEach>
    </table>
</body>

</html>