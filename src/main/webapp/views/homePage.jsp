<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>

    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
            crossorigin="anonymous"
    />
    <script
            src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
            crossorigin="anonymous"
    ></script>

    <title>Home page</title>

</head>
<body>
<h1>Home page</h1>

<div class="row">
    <div class="col-auto ms-2 mf-3">
        <table class="table table-striped">
            <thead>
            <tr>
                <th scope="col">id</th>
                <th scope="col">Name</th>
                <th scope="col">Email</th>
                <th scope="col">Status</th>
                <th scope="col">Role</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${users}" var="user">
                <tr>
                    <th scope="row">${user.getId()}</th>
                    <td>${user.getName()}</td>
                    <td>${user.getEmail()}</td>
                    <td>${user.getStatus()}</td>
                    <td>${user.getRole()}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
