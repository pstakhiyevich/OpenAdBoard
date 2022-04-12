<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="com.stakhiyevich.openadboard.model.entity.UserRole" %>

<fmt:setLocale value="${sessionScope.localization}" scope="session"/>
<fmt:setBundle basename="text"/>

<!DOCTYPE html>
<html lang="${sessionScope.localization}">
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
    <link href="static/css/style.css" rel="stylesheet"/>
    <title>
        <fmt:message key="label.home_page"/>
    </title>
</head>
<body class="d-flex flex-column min-vh-100">
<jsp:include page="header.jsp"/>
<div class="container-fluid mt-3 mb-1">
    <div class="row">
        <div class="col-sm-3 ">
        </div>
        <div class="col ms-2 border-bottom">
            <div class="row justify-content-between">
                <div class="col-auto ms-2 me-2">
                </div>
                <div class="col-auto ms-2 me-2">
                </div>
            </div>
        </div>
    </div>
</div>
<div class="container-fluid">
    <div class="row">
        <main class="col m-1 mt-1">
            <div class="row">
                <div class="row justify-content-end me-2 mb-3">
                    <div class="col">
                        <c:if test="${sessionScope.user.role == UserRole.ADMIN}">
                            <a class="btn btn-primary"
                               href="${pageContext.request.contextPath}/controller?command=user_management_page"><fmt:message
                                    key="label.users"/></a>
                        </c:if>
                        <a class="btn btn-outline-primary"
                           href="${pageContext.request.contextPath}/controller?command=category_management_page"><fmt:message
                                key="label.categories"/></a>
                        <a class="btn btn-outline-primary"
                           href="${pageContext.request.contextPath}/controller?command=city_management_page"><fmt:message
                                key="label.cities"/></a>
                    </div>
                    <div class="col d-flex justify-content-end">
                    </div>
                </div>
                <div class="row">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th scope="col"><fmt:message key="label.id"/></th>
                            <th scope="col"><fmt:message key="label.name"/></th>
                            <th scope="col"><fmt:message key="label.email"/></th>
                            <th scope="col"><fmt:message key="label.status"/></th>
                            <th scope="col"><fmt:message key="label.role"/></th>
                            <th scope="col-1"><fmt:message key="label.action"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${users}" var="user">
                            <form method="post"
                                  action="${pageContext.request.contextPath}/controller?command=save_user_changes">
                                <input type="hidden" name="selected_user_id" value="${user.getId()}">
                                <tr>
                                    <th scope="row">${user.getId()}</th>
                                    <td>
                                            ${user.getName()}
                                    </td>
                                    <td>${user.getEmail()}</td>
                                    <td>
                                        <select name="selected_user_status" class="form-select"
                                                aria-label="Default select example">
                                            <option value="ACTIVATED" ${user.getStatus() == 'ACTIVATED' ? ' selected' : ''}>
                                                <fmt:message key="label.activated"/>
                                            </option>
                                            <option value="INACTIVATED" ${user.getStatus() == 'INACTIVATED' ? ' selected' : ''}>
                                                <fmt:message key="label.inactivated"/>
                                            </option>
                                            <option value="BANNED" ${user.getStatus() == 'BANNED' ? ' selected' : ''}>
                                                <fmt:message key="label.banned"/>
                                            </option>
                                        </select>
                                    </td>
                                    <td>
                                        <select name="selected_user_role" class="form-select"
                                                aria-label="Default select example">
                                            <option value="USER" ${user.getRole() == 'USER' ? ' selected' : ''}>
                                                <fmt:message key="label.user"/>
                                            </option>
                                            <option value="MODER" ${user.getRole() == 'MODER' ? ' selected' : ''}>
                                                <fmt:message key="label.moder"/>
                                            </option>
                                            <option value="ADMIN" ${user.getRole() == 'ADMIN' ? ' selected' : ''}>
                                                <fmt:message key="label.admin"/>
                                            </option>
                                        </select>
                                    </td>
                                    <td>
                                        <span><button class="btn btn-outline-primary" type="submit"><fmt:message
                                                key="label.save"/></button></span>
                                    </td>
                                </tr>
                            </form>
                        </c:forEach>
                        </tbody>
                    </table>
                    <nav aria-label="navigation for items">
                        <ul class="pagination justify-content-center mt-3 mb-4">
                            <c:if test="${current_page != 1}">
                                <li class="page-item">
                                    <a class="page-link"
                                       href="${pageContext.request.contextPath}/controller?command=user_management_page&users_per_page=${users_per_page}&page=${current_page-1}">
                                        <fmt:message key="label.previous"/>
                                    </a>
                                </li>
                            </c:if>
                            <c:forEach begin="1" end="${number_of_pages}" var="i">
                                <c:choose>
                                    <c:when test="${current_page eq i}">
                                        <li class="page-item active">
                                            <a class="page-link"> ${i} <span class="sr-only"></span></a>
                                        </li>
                                    </c:when>
                                    <c:otherwise>
                                        <li class="page-item">
                                            <a class="page-link"
                                               href="${pageContext.request.contextPath}/controller?command=user_management_page&users_per_page=${users_per_page}&page=${i}">
                                                    ${i}
                                            </a>
                                        </li>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                            <c:if test="${current_page lt number_of_pages}">
                                <li class="page-item">
                                    <a class="page-link"
                                       href="${pageContext.request.contextPath}/controller?command=user_management_page&users_per_page=${users_per_page}&page=${current_page+1}">
                                        <fmt:message key="label.next"/>
                                    </a>
                                </li>
                            </c:if>
                        </ul>
                    </nav>
                </div>
            </div>
        </main>
    </div>
</div>
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-2 col-auto col-lg-2 me-2">
        </div>
    </div>
</div>
<jsp:include page="footer.jsp"/>
<script src="static/js/script.js"></script>
</body>
</html>
