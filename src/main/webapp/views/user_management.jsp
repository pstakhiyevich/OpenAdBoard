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
<c:if test="${!empty sessionScope.feedback_user_change}">
    <div class="position-fixed top-0 start-0 p-3" style="z-index: 11">
        <div id="changeUserFeedbackToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
            <div class="toast-header">
                <a class="rounded me-2">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="green" class="bi bi-envelope"
                         viewBox="0 0 16 16">
                        <path d="M0 4a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v8a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V4Zm2-1a1 1 0 0 0-1 1v.217l7 4.2 7-4.2V4a1 1 0 0 0-1-1H2Zm13 2.383-4.708 2.825L15 11.105V5.383Zm-.034 6.876-5.64-3.471L8 9.583l-1.326-.795-5.64 3.47A1 1 0 0 0 2 13h12a1 1 0 0 0 .966-.741ZM1 11.105l4.708-2.897L1 5.383v5.722Z"/>
                    </svg>
                </a>
                <strong class="me-auto"><fmt:message key="label.notification"/></strong>
                <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
            </div>
            <div class="toast-body">
                <div class="">
                    <fmt:message key="${sessionScope.feedback_user_change}"/>
                </div>
            </div>
        </div>
    </div>
    <script>
        const toast = new bootstrap.Toast(document.getElementById('changeUserFeedbackToast'));
        toast.show()
    </script>
    <c:remove var="feedback_user_change" scope="session"/>
</c:if>
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
                               href="${pageContext.request.contextPath}/controller?command=user_management_page"><fmt:message key="label.users"/></a>
                        </c:if>
                        <a class="btn btn-outline-primary"
                           href="${pageContext.request.contextPath}/controller?command=category_management_page"><fmt:message key="label.categories"/></a>
                        <a class="btn btn-outline-primary"
                           href="${pageContext.request.contextPath}/controller?command=city_management_page"><fmt:message key="label.cities"/></a>
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
                                        <a href="${pageContext.request.contextPath}/controller?command=user_page&user_id=${user.getId()}">
                                                ${user.getName()}
                                        </a>
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
                                        <span><button class="btn btn-outline-primary" type="submit"><fmt:message key="label.save"/></button></span>
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
