<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.stakhiyevich.openadboard.model.entity.UserRole" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

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
<%--user change feedback toast--%>
<c:if test="${!empty sessionScope.categoryManagementPageFeedback}">
    <div class="position-fixed top-0 start-0 p-3" style="z-index: 11">
        <div id="categoryManagementPageToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
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
                    <fmt:message key="${sessionScope.categoryManagementPageFeedback}"/>
                </div>
            </div>
        </div>
    </div>
    <script>
        const toast = new bootstrap.Toast(document.getElementById('categoryManagementPageToast'));
        toast.show()
    </script>
    <c:remove var="categoryManagementPageFeedback" scope="session"/>
</c:if>
<!-- ===============MAIN START============ -->
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
        <main class="col mt-1">
            <div class="row">
                <div class="row mb-3">
                    <div class="col">
                        <c:if test="${sessionScope.user.role == UserRole.ADMIN}">
                            <a class="btn btn-outline-primary"
                               href="${pageContext.request.contextPath}/controller?command=user_management_page"><fmt:message
                                    key="label.users"/></a>
                        </c:if>
                        <a class="btn btn-primary"
                           href="${pageContext.request.contextPath}/controller?command=category_management_page"><fmt:message
                                key="label.categories"/></a>
                        <a class="btn btn-outline-primary"
                           href="${pageContext.request.contextPath}/controller?command=city_management_page"><fmt:message
                                key="label.cities"/></a>
                    </div>
                    <div class="col d-flex justify-content-end">
                        <div>
                            <button type="button" data-bs-toggle="modal"
                                    data-bs-target="#add_category_modal"
                                    class="btn btn-outline-primary ms-3"><fmt:message key="label.add.category"/>
                            </button>
                            <div class="modal fade" id="add_category_modal" tabindex="-1"
                                 aria-labelledby="addCategoryModalLabel"
                                 aria-hidden="true">
                                <div class="modal-dialog modal-dialog-centered">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h3 class="modal-title" id="addCategoryModalLabel"><fmt:message
                                                    key="label.add.category"/></h3>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                    aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body">
                                            <form class="text-end" id="add_category_form" method="post"
                                                  action="${pageContext.request.contextPath}/controller?command=add_category">
                                                <div class="form-outline mb-4">
                                                    <input pattern="^([\w\s:.'-]{1,30})$" title="wrong title"
                                                           required type="text" name="category_title"
                                                           id="add_category_title"
                                                           class="form-control form-control-lg"
                                                           placeholder="enter category"
                                                           value=""/>
                                                    <div class="error-hint text-danger custom_hidden"><fmt:message
                                                            key="message.wrong.category.title"/></div>
                                                    <c:if test="${sessionScope.validationFeedback.title != null}">
                                                        <div class="text-danger text-start">
                                                            <fmt:message
                                                                    key="${sessionScope.validationFeedback.title}"/>
                                                        </div>
                                                    </c:if>
                                                </div>
                                                <button type="submit"
                                                        class="btn btn-outline-primary btn-lg btn-block mt-4 w-50">
                                                    <fmt:message key="label.add"/>
                                                </button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <c:if test="${!empty sessionScope.validationFeedback}">
                                <script>
                                    const addCategoryModal = new bootstrap.Modal(document.getElementById('add_category_modal'));
                                    addCategoryModal.show()
                                </script>
                            </c:if>
                            <c:remove var="validationFeedback" scope="session"/>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th scope="col"><fmt:message key="label.id"/></th>
                            <th scope="col"><fmt:message key="label.title"/></th>
                            <th scope="col"><fmt:message key="label.action"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${categories}" var="category">
                            <tr>
                                <th scope="row">${category.getId()}</th>
                                <td>${category.getTitle()}</td>
                                <td>
                                    <div class="row">
                                        <div class="col-auto">
                                            <button type="button" data-bs-toggle="modal"
                                                    data-bs-target="#edit_category_modal_${category.getId()}"
                                                    class="btn btn-outline-primary"><fmt:message key="label.edit"/>
                                            </button>
                                            <div class="modal fade" id="edit_category_modal_${category.getId()}"
                                                 tabindex="-1"
                                                 aria-labelledby="exampleModalLabel"
                                                 aria-hidden="true">
                                                <div class="modal-dialog modal-dialog-centered">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <h3 class="modal-title" id="exampleModalLabel"><fmt:message
                                                                    key="label.edit.category"/></h3>
                                                            <button type="button" class="btn-close"
                                                                    data-bs-dismiss="modal"
                                                                    aria-label="Close"></button>
                                                        </div>
                                                        <div class="modal-body">
                                                            <form class="text-end" id="edit_category_form" method="post"
                                                                  action="${pageContext.request.contextPath}/controller?command=edit_category">
                                                                <!-- category input -->
                                                                <input type="hidden" name="category_id"
                                                                       value="${category.getId()}">
                                                                <div class="form-outline mb-4">
                                                                    <input pattern="^([\w\s:.'-]{1,30})$"
                                                                           title="wrong title"
                                                                           required type="text" name="category_title"
                                                                           id="category_title"
                                                                           class="form-control form-control-lg"
                                                                           placeholder="category"
                                                                           value="${category.getTitle()}"/>
                                                                    <div class="error-hint text-danger custom_hidden">
                                                                        <fmt:message key="message.wrong.category.title"/>
                                                                    </div>
                                                                    <c:if test="${sessionScope.editCategoryValidationFeedback.title != null}">
                                                                        <div class="text-danger text-start">
                                                                            <fmt:message
                                                                                    key="${sessionScope.editCategoryValidationFeedback.title}"/>
                                                                        </div>
                                                                    </c:if>
                                                                </div>
                                                                <button type="submit"
                                                                        class="btn btn-outline-primary btn-lg btn-block mt-4 w-50">
                                                                    Save
                                                                </button>
                                                            </form>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <c:if test="${ (category.getId() == sessionScope.selectedCategory)}">
                                                <script>
                                                    const addCategoryModal = new bootstrap.Modal(document.getElementById('edit_category_modal_${category.getId()}'));
                                                    addCategoryModal.show()
                                                </script>
                                            </c:if>
                                            <c:remove var="editCategoryValidationFeedback" scope="session"/>
                                            <c:remove var="selectedCategory" scope="session"/>
                                        </div>
                                        <div class="col-auto">
                                            <form type="hidden" method="post"
                                                  action="${pageContext.request.contextPath}/controller?command=delete_category">
                                                <input type="hidden" name="category_id" value="${category.getId()}">
                                                <button type="submit" class="btn btn-outline-danger">
                                                    <fmt:message key="label.delete"/>
                                                </button>
                                            </form>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <nav aria-label="navigation for items">
                        <ul class="pagination justify-content-center mt-3 mb-4">
                            <c:if test="${current_page != 1 && current_page != null}">
                                <li class="page-item">
                                    <a class="page-link"
                                       href="${pageContext.request.contextPath}/controller?command=category_management_page&categories_per_page=${categories_per_page}&page=${current_page-1}">
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
                                               href="${pageContext.request.contextPath}/controller?command=category_management_page&categories_per_page=${categories_per_page}&page=${i}">
                                                    ${i}
                                            </a>
                                        </li>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                            <c:if test="${current_page lt number_of_pages}">
                                <li class="page-item">
                                    <a class="page-link"
                                       href="${pageContext.request.contextPath}/controller?command=category_management_page&categoriesPerPage=${categories_per_page}&page=${current_page+1}">
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
<!-- ===============MAIN END============ -->
<jsp:include page="footer.jsp"/>
<script src="static/js/category_management_script.js"></script>
</body>
</html>
