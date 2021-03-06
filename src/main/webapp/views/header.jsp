<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.stakhiyevich.openadboard.model.entity.UserRole" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${sessionScope.localization}" scope="session"/>
<fmt:setBundle basename="text"/>

<header>
    <nav class="navbar navbar-expand-md navbar-light border-bottom mt-2">
        <div class="container-fluid">
            <div class="navbar-collapse w-50 collapse order-0 order-md-0 dual-collapse2">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item active">
                        <a class="nav-link"
                           href="${pageContext.request.contextPath}/controller?command=home_page">
                            <fmt:message key="label.home_page"/>
                        </a>
                    </li>
                </ul>
            </div>
            <div class="mx-auto w-75 order-1">
                <form class="" id="search_form" action="${pageContext.request.contextPath}/controller" method="get">
                    <input type="hidden" name="command" value="home_page">
                    <c:if test="${requestScope.selected_category != 0}">
                        <input type="hidden" name="selected_category" value="${requestScope.selected_category}">
                    </c:if>
                    <c:if test="${requestScope.selected_city != 0}">
                        <input type="hidden" name="selected_city" value="${requestScope.selected_city}">
                    </c:if>
                    <input required type="search" maxlength="50" class="form-control" id="search_input"
                           name="search_query" placeholder=<fmt:message
                            key="label.search"/>>
                    <div class="error-hint text-danger custom_hidden"><fmt:message
                            key="message.wrong.search.query"/></div>
                    <c:if test="${requestScope.validationFeedback.search_query != null}">
                        <div class="text-danger">
                            <fmt:message key="${requestScope.validationFeedback.search_query}"/>
                        </div>
                    </c:if>
                </form>
            </div>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target=".dual-collapse2">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="navbar-collapse collapse w-50 order-3 dual-collapse2">
                <ul class="navbar-nav ms-auto">
                    <c:if test="${sessionScope.user != null}">
                        <li class="nav-item">
                            <a class="btn btn-outline-success" type="button"
                               href="${pageContext.request.contextPath}/controller?command=add_item_page"><fmt:message
                                    key="label.create.an.add"/></a>
                        </li>
                    </c:if>
                    <c:if test="${sessionScope.user == null}">
                        <li class="nav-item active">
                            <a class="btn me-2 ms-2"
                               href="${pageContext.request.contextPath}/controller?command=sign_up_page"><fmt:message
                                    key="label.sign_up"/></a>
                        </li>
                        <li class="nav-item active">
                            <button class="btn btn-outline-success me-2 ms-2" data-bs-toggle="modal"
                                    data-bs-target="#sign_in_modal" type="button"><fmt:message key="label.sign_in"/>
                            </button>
                        </li>
                        <div class="modal fade" id="sign_in_modal" tabindex="-1" aria-labelledby="exampleModalLabel"
                             aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h3 class="modal-title" id="exampleModalLabel"><fmt:message
                                                key="label.sign_in"/></h3>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <c:if test="${sessionScope.sign_in_feedback.fail != null}">
                                            <div class="text-danger text-center">
                                                <fmt:message key="${sessionScope.sign_in_feedback.fail}"/>
                                            </div>
                                        </c:if>
                                        <form class="text-end" id="sign_in_form" method="post"
                                              action="${pageContext.request.contextPath}/controller?command=sign_in">
                                            <div class="form-outline mb-4">
                                                <input required type="email" maxlength="50" name="email"
                                                       id="user_email_input"
                                                       class="form-control form-control-lg"
                                                       placeholder=
                                                           <fmt:message key="label.email"/>
                                                />
                                                <div class="error-hint text-danger custom_hidden"><fmt:message
                                                        key="message.wrong.email"/></div>
                                                <c:if test="${sessionScope.sign_in_feedback.email != null}">
                                                    <div class="text-danger text-start">
                                                        <fmt:message key="${sessionScope.sign_in_feedback.email}"/>
                                                    </div>
                                                </c:if>
                                            </div>
                                            <div class="form-outline mb-4">
                                                <input required type="password" maxlength="50" name="password"
                                                       id="user_password_input"
                                                       class="form-control form-control-lg"
                                                       placeholder=
                                                           <fmt:message key="label.password"/>
                                                />
                                                <div class="error-hint text-danger custom_hidden"><fmt:message
                                                        key="message.wrong.password"/></div>
                                                <c:if test="${sessionScope.sign_in_feedback.password != null}">
                                                    <div class="text-danger text-start">
                                                        <fmt:message key="${sessionScope.sign_in_feedback.password}"/>
                                                    </div>
                                                </c:if>
                                            </div>
                                            <button type="submit"
                                                    class="btn btn-outline-primary btn-lg btn-block mt-4 w-50">
                                                <fmt:message key="label.sign_in"/>
                                            </button>
                                            <p class="small fw-bold mt-2 pt-1 mb-4"><fmt:message
                                                    key="message.don't.have.an.account"/><a
                                                    href="${pageContext.request.contextPath}/controller?command=sign_up_page"
                                                    class="link-success"><fmt:message key="message.register"/></a>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <c:if test="${!empty sessionScope.sign_in_feedback}">
                            <script>
                                const myModal = new bootstrap.Modal(document.getElementById('sign_in_modal'));
                                myModal.show()
                            </script>
                            <c:remove var="sign_in_feedback" scope="session"/>
                        </c:if>
                    </c:if>
                    <li class="nav-item dropdown">
                        <a class="ms-1 nav-link link-dark" href="#" id="navbarDropdownLanguage" role="button"
                           data-bs-toggle="dropdown" aria-expanded="false">
                            <img src="static/graphics/language_black_24dp.svg">
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdownLanguage">
                            <li><a class="dropdown-item"
                                   href="${pageContext.request.contextPath}/controller?command=change_language&selected_language=en">English</a>
                            </li>
                            <li><a class="dropdown-item"
                                   href="${pageContext.request.contextPath}/controller?command=change_language&selected_language=zh">??????</a>
                            </li>
                            <li><a class="dropdown-item"
                                   href="${pageContext.request.contextPath}/controller?command=change_language&selected_language=ru">??????????????</a>
                            </li>
                        </ul>
                    </li>
                </ul>
                <c:if test="${!empty sessionScope.user}">
                    <ul class="navbar-nav">
                        <li class="nav-item">
                            <a href="${pageContext.request.contextPath}/controller?command=bookmark_page"
                               class="nav-link link-dark">
                                <img src="static/graphics/bookmark_border_black_18dp.svg">
                            </a>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link link-dark" href="#" id="navbarDropdownAccount" role="button"
                               data-bs-toggle="dropdown" aria-expanded="false">
                                <img src="static/graphics/account_circle_black_36dp.svg">
                            </a>
                            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdownAccount">
                                <li><a class="dropdown-item"
                                       href="${pageContext.request.contextPath}/controller?command=user_page&user_id=${sessionScope.user.id}"><fmt:message
                                        key="label.my.profile"/></a></li>
                                <hr class="dropdown-divider">
                                <c:if test="${sessionScope.user.role == UserRole.ADMIN}">
                                    <li><a class="dropdown-item"
                                           href="${pageContext.request.contextPath}/controller?command=user_management_page"><fmt:message
                                            key="label.user.management"/></a></li>
                                </c:if>
                                <c:if test="${sessionScope.user.role == UserRole.ADMIN || sessionScope.user.role == UserRole.MODER}">
                                    <li><a class="dropdown-item"
                                           href="${pageContext.request.contextPath}/controller?command=category_management_page"><fmt:message
                                            key="label.category.management"/></a></li>
                                    <li><a class="dropdown-item"
                                           href="${pageContext.request.contextPath}/controller?command=city_management_page"><fmt:message
                                            key="label.city.management"/></a></li>
                                    <hr class="dropdown-divider">
                                </c:if>
                                <li><a class="dropdown-item"
                                       href="${pageContext.request.contextPath}/controller?command=log_out"><fmt:message
                                        key="label.log.out"/></a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </c:if>
            </div>
        </div>
    </nav>
    <c:remove var="validationFeedback" scope="session"/>
</header>
<script src="static/js/header_script.js"></script>