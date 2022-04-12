<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
    <link rel="stylesheet" href="static/css/style.css"/>
    <title><fmt:message key="label.registration.page"/></title>
</head>
<body>
<c:if test="${!empty sessionScope.validationFeedback.success}">
    <div class="modal fade" id="sign_up_modal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
         aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="staticBackdropLabel"><fmt:message key="message.congratulations"/></h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <p><fmt:message key="message.you.have.successfully.registered"/></p>
                    <p><fmt:message key="message.please.check.your.email"/></p>
                </div>
                <div class="modal-footer">
                    <a class="btn btn-outline-primary"
                       href="${pageContext.request.contextPath}/controller?command=home_page"><fmt:message key="label.home_page"/></a>
                </div>
            </div>
        </div>
    </div>
    <script>
        const signUpModal = new bootstrap.Modal(document.getElementById('sign_up_modal'));
        signUpModal.show()
    </script>
</c:if>
<section class="vh-100">
    <div class="container py-5 h-100">
        <div class="row ">
            <a class="text-decoration-none display-6"
               href="${pageContext.request.contextPath}/controller?command=home_page">
                <svg xmlns="http://www.w3.org/2000/svg" width="60" height="60" fill="green"
                     class="bi bi-arrow-left-square" viewBox="0 0 16 16">
                    <path fill-rule="evenodd"
                          d="M15 2a1 1 0 0 0-1-1H2a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1V2zM0 2a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V2zm11.5 5.5a.5.5 0 0 1 0 1H5.707l2.147 2.146a.5.5 0 0 1-.708.708l-3-3a.5.5 0 0 1 0-.708l3-3a.5.5 0 1 1 .708.708L5.707 7.5H11.5z"/>
                </svg>
            </a>
        </div>
        <div class="row d-flex align-items-center justify-content-center h-75">
            <div class="col-md-8 col-lg-7 col-xl-6">
                <img class="img-fluid" alt="sign up image" src="static/graphics/sign_up_image.png">
            </div>
            <div class="col-md-7 col-lg-5 col-xl-5 offset-xl-1 shadow rounded-3">
                <div class="mb-5 mt-3">
                    <h2><p><fmt:message key="label.registration.form"/></h2>
                </div>
                <form id="registration_form" method="post" class="form-control"
                      action="${pageContext.request.contextPath}/controller?command=sign_up">
                    <div class="form-outline mb-4">
                        <input name="name"
                               maxlength="30"
                               minlength="2"
                               type="text"
                               id="user_name_input"
                               class="form-control form-control-lg"
                               placeholder="name"
                               required
                        />
                        <div class="error-hint text-danger custom_hidden"><fmt:message key="message.wrong.user.name"/></div>
                        <c:if test="${sessionScope.validationFeedback.name != null}">
                            <div class="text-danger">
                                <fmt:message key="${sessionScope.validationFeedback.name}"/>
                            </div>
                        </c:if>
                    </div>
                    <div class="form-outline mb-4">
                        <input type="email"
                               maxlength="50"
                               name="email"
                               id="user_email_input"
                               class="form-control form-control-lg"
                               placeholder="email"
                               required
                        />
                        <div class="error-hint text-danger custom_hidden"><fmt:message key="message.wrong.email"/></div>
                        <c:if test="${sessionScope.validationFeedback.email != null}">
                            <div class="text-danger">
                                <fmt:message key="${sessionScope.validationFeedback.email}"/>
                            </div>
                        </c:if>
                    </div>
                    <div class="form-outline mb-4">
                        <input name="password"
                               type="password"
                               maxlength="50"
                               id="user_password_input"
                               class="form-control form-control-lg"
                               placeholder="password"
                               required
                        />
                        <div class="error-hint text-danger custom_hidden"><fmt:message key="message.wrong.password"/></div>
                        <c:if test="${sessionScope.validationFeedback.password != null}">
                            <div class="text-danger">
                                <fmt:message key="${sessionScope.validationFeedback.password}"/>
                            </div>
                        </c:if>
                    </div>
                    <div class="form-outline mb-4">
                        <input name="repeated_password"
                               type="password"
                               maxlength="50"
                               id="user_repeated_password_input"
                               class="form-control form-control-lg"
                               placeholder="repeat password"
                               required
                        />
                        <div class="error-hint text-danger custom_hidden"><fmt:message key="message.wrong.password"/></div>
                        <c:if test="${sessionScope.validationFeedback.repeated_password != null}">
                            <div class="text-danger">
                                <fmt:message key="${sessionScope.validationFeedback.repeated_password}"/>
                            </div>
                        </c:if>
                    </div>
                    <button type="submit" class="btn btn-outline-primary btn-lg btn-block mt-4 mb-4"><fmt:message key="label.register"/></button>
                </form>
            </div>
        </div>
    </div>
</section>
<script src="static/js/sign_up_script.js"></script>
<c:remove var="validationFeedback" scope="session"/>
</body>
</html>