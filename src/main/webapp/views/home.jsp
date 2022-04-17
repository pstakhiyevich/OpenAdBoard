<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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



<c:if test="${!empty sessionScope.deleteItemFeedback}">
    <div class="position-fixed top-0 start-0 p-3" style="z-index: 11">
        <div id="deleteItemFeedbackToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
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
                <div class="text-dark">
                    <fmt:message key="${sessionScope.deleteItemFeedback}"/>
                </div>
            </div>
        </div>
    </div>
    <script>
        const toast = new bootstrap.Toast(document.getElementById('deleteItemFeedbackToast'));
        toast.show()
    </script>
    <c:remove var="deleteItemFeedback" scope="session"/>
</c:if>

<!-- ===============MAIN START============ -->
<div class="container-fluid mt-3 mb-1">
    <div class="row">
        <div class="col-sm-3 ">
        </div>
        <div class="col border-bottom">
            <div class="row justify-content-between">
                <div class="col-auto d-flex align-items-end">
                    <c:if test="${!empty requestScope.search_query}">
                        <h5 class="text-muted fw-normal"><fmt:message key="message.search.resource"/> "${requestScope.search_query}"</h5>
                    </c:if>
                </div>
                <div class="col-auto ms-0 me-2">
                    <div class="row justify-content-end">
                        <div style="display:none">
                            <a id="sort_by_anchor" class="nav-link"
                               href="${pageContext.request.contextPath}/controller?command=home_page<c:if test="${!empty requestScope.search_query}">&search_query=${requestScope.search_query}</c:if><c:if test="${requestScope.selected_category != 0}">&selected_category=${requestScope.selected_category}</c:if><c:if test="${requestScope.selected_city != 0}">&selected_city=${requestScope.selected_city}</c:if>&sort_by="></a>
                        </div>
                        <c:if test="${!empty sort_by}">
                            <div style="display:none">
                                <input id="select_by_div" value="${sort_by}"></input>
                            </div>
                        </c:if>
                        <div class="col-auto d-flex align-items-end">
                            <h5 class="text-muted fw-normal"><fmt:message key="label.sort.by"/></h5>
                        </div>
                        <div class="col-auto d-flex align-items-end">
                            <select id="sort_by_selector" class="form-select form-select-lg border-0"
                                    aria-label="Default select example">
                                <option value="new" <c:if test="${requestScope.sort_by == 'new'}"> selected </c:if> >
                                    <fmt:message key="label.new"/>
                                </option>
                                <option value="old" <c:if test="${requestScope.sort_by == 'old'}"> selected </c:if> >
                                    <fmt:message key="label.old"/>
                                </option>
                                <option value="expensive" <c:if
                                        test="${requestScope.sort_by == 'expensive'}"> selected </c:if>><fmt:message key="label.expensive.first"/>
                                </option>
                                <option value="cheap" <c:if
                                        test="${requestScope.sort_by == 'cheap'}"> selected </c:if> ><fmt:message key="label.cheap.first"/>
                                </option>
                            </select>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="container-fluid">
    <div class="row">
        <aside class="col-sm-2 col-md-2 m-1 border-end">
            <div>
                <form id="select_category_city_form" method="get"
                      action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="command" value="home_page">
                    <c:if test="${!empty requestScope.search_query}">
                        <input type="hidden" name="search_query" value="${requestScope.search_query}">
                    </c:if>
                    <ul class="nav nav-pills flex-sm-column flex-row ms-3 mb-auto justify-content-center text-truncate">
                        <li class="mb-3">
                            <a data-bs-toggle="collapse" data-bs-target="#categories-collapse" aria-expanded="true"
                               href="#"
                               class="nav-link link-dark px-2 text-truncate">
                                <span class=""><fmt:message key="label.categories"/></span>
                            </a>
                            <div class="collapse show" id="categories-collapse">
                                <select name="selected_category" class="form-select" size="8"
                                        aria-label="size 5 select example">
                                    <c:forEach items="${categories}" var="category">
                                        <option class="mt-2" value="${category.id}"${selected_category == category.id ? ' selected' : ''}>${category.getTitle()}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </li>
                        <li class="mb-1">
                            <a data-bs-toggle="collapse" data-bs-target="#cities-collapse" aria-expanded="true" href="#"
                               class="nav-link link-dark px-2 text-truncate">
                                <span class=" "><fmt:message key="label.cities"/></span>
                            </a>
                            <div class="collapse show" id="cities-collapse">
                                <select name="selected_city" class="form-select" size="8"
                                        aria-label="size 5 select example">
                                    <c:forEach items="${cities}" var="city">
                                        <option class="mt-2" value="${city.id}"${selected_city == city.id ? ' selected' : ''}>${city.getTitle()}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </li>
                        <li class="nav-item mt-4 text-center">
                            <button form="select_category_city_form" class="btn btn-outline-success w-75" type="submit">
                                <fmt:message key="label.apply"/>
                            </button>
                        </li>
                        <li class="nav-item mt-1 text-center">
                            <button form="reset_form" class="btn btn-outline-primary w-75" type="submit"><fmt:message key="label.reset"/></button>
                        </li>
                    </ul>
                </form>
                <form id="reset_form" action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="command" value="home_page">
                </form>
            </div>
        </aside>
        <main class="col m-1 mt-1">
            <div class="row row-cols-auto justify-content-center">
                <c:if test="${empty items}">
                    <div class="mt-5">
                        <h3 class="text-muted fw-normal"><fmt:message key="message.empty"/></h3>
                    </div>
                </c:if>
                <c:forEach items="${items}" var="item">
                    <div class="col mb-1">
                        <div class="card mx-auto my-1" style="width: 18rem; height: 35rem;">
                            <div class="bg-image hover-overlay ripple" data-mdb-ripple-color="light">
                                <a href="${pageContext.request.contextPath}/controller?command=item_page&item_id=${item.id}">
                                    <img src="${pageContext.request.contextPath}/upload?upload_file_path=${item.picture}"
                                         class="img-fluid w-100">
                                </a>
                            </div>
                            <div class="card-body">
                                <h5 class="card-title font-weight-bold"><a>${item.getTitle()}</a></h5>
                                <ul class="list-unstyled list-inline mb-0">
                                    <li class="list-inline-item">
                                        <p class="text-danger">$ ${item.getPrice()}</p>
                                    </li>
                                </ul>
                                <p class="mb-1">${item.getCity().getTitle()}</p>
                                <hr class="my-3"/>
                                <p class="text test_paragraph">
                                        ${item.getDescription()}
                                </p>
                                <p class="mb-1">
                                    <fmt:message key="message.last.updated"/>
                                </p>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </main>
    </div>
</div>
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-2 col-auto col-lg-2 me-2">
        </div>
        <div class="col">
            <nav aria-label="navigation for items">
                <ul class="pagination justify-content-center mt-3 mb-4">
                    <c:if test="${current_page != 1}">
                        <li class="page-item">
                            <a class="page-link"
                               href="${pageContext.request.contextPath}/controller?command=home_page&items_per_page=${items_per_page}&page=${current_page-1}<c:if test="${!empty requestScope.sort_by}">&sort_by=${requestScope.sort_by}</c:if><c:if test="${!empty requestScope.search_query}">&search_query=${requestScope.search_query}</c:if><c:if test="${requestScope.selected_category != 0}">&selected_category=${requestScope.selected_category}</c:if><c:if test="${requestScope.selected_city != 0}">&selected_city=${requestScope.selected_city}</c:if>">
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
                                       href="${pageContext.request.contextPath}/controller?command=home_page&items_per_page=${items_per_page}&page=${i}<c:if test="${!empty requestScope.sort_by}">&sort_by=${requestScope.sort_by}</c:if><c:if test="${!empty requestScope.search_query}">&search_query=${requestScope.search_query}</c:if><c:if test="${requestScope.selected_category != 0}">&selected_category=${requestScope.selected_category}</c:if><c:if test="${requestScope.selected_city != 0}">&selected_city=${requestScope.selected_city}</c:if>">
                                            ${i}
                                    </a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    <c:if test="${current_page lt number_of_pages}">
                        <li class="page-item">
                            <a class="page-link"
                               href="${pageContext.request.contextPath}/controller?command=home_page&items_per_page=${items_per_page}&page=${current_page+1}<c:if test="${!empty requestScope.sort_by}">&sort_by=${requestScope.sort_by}</c:if><c:if test="${!empty requestScope.search_query}">&search_query=${requestScope.search_query}</c:if><c:if test="${requestScope.selected_category != 0}">&selected_category=${requestScope.selected_category}</c:if><c:if test="${requestScope.selected_city != 0}">&selected_city=${requestScope.selected_city}</c:if>">
                                <fmt:message key="label.next"/>
                            </a>
                        </li>
                    </c:if>
                </ul>
            </nav>
        </div>
    </div>
</div>
<!-- ===============MAIN END============ -->
<script src="static/js/home_script.js"></script>
</body>
</html>
