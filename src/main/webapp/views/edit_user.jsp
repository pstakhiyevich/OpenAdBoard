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
    <link rel="stylesheet" href="static/css/style.css"/>
    <title><fmt:message key="label.home_page"/></title>
</head>
<body>
<jsp:include page="header.jsp"/>
<%--edit user information feedback toast--%>
<c:if test="${!empty sessionScope.editUserInformationFeedback}">
    <div class="position-fixed top-0 start-0 p-3" style="z-index: 11">
        <div id="editUserInformationFeedbackToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
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
                    <fmt:message key="${sessionScope.editUserInformationFeedback}"/>
                </div>
            </div>
        </div>
    </div>
    <script>
        const toast = new bootstrap.Toast(document.getElementById('editUserInformationFeedbackToast'));
        toast.show()
    </script>
    <c:remove var="editUserInformationFeedback" scope="session"/>
</c:if>
<%--edit user information feedback toast--%>
<c:if test="${!empty sessionScope.changePasswordFeedback}">
    <div class="position-fixed top-0 start-0 p-3" style="z-index: 11">
        <div id="changePasswordFeedbackToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
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
                    <fmt:message key="${sessionScope.changePasswordFeedback}"/>
                </div>
            </div>
        </div>
    </div>
    <script>
        const toast = new bootstrap.Toast(document.getElementById('changePasswordFeedbackToast'));
        toast.show()
    </script>
    <c:remove var="changePasswordFeedback" scope="session"/>
</c:if>
<!-- ===============MAIN START============ -->
<div class="container-fluid mt-3 mb-1">
    <div class="row">
        <div class="col-sm-3">
        </div>
        <div class="col ms-2 border-bottom ">
            <div class="row justify-content-end">
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
        <aside class="col-2">
        </aside>
        <main class="col mt-1 mb-3">
            <div class="row border-bottom mb-4">
                <div class="col text-start me-1">
                    <h3><fmt:message key="label.edit.profile"/></h3>
                </div>
            </div>
            <nav>
                <div class="nav nav-tabs" id="nav-tab" role="tablist">
                    <button class="nav-link text-black active" id="nav-edit-tab" data-bs-toggle="tab"
                            data-bs-target="#nav-home"
                            type="button" role="tab" aria-controls="nav-home" aria-selected="true"><fmt:message key="label.edit.user.information"/>
                    </button>
                    <button class="nav-link text-black" id="nav-password-tab" data-bs-toggle="tab"
                            data-bs-target="#nav-profile"
                            type="button" role="tab" aria-controls="nav-profile" aria-selected="false"><fmt:message key="label.change.password"/>
                    </button>
                </div>
            </nav>
            <div class="tab-content" id="nav-tabContent">
                <div class="tab-pane fade show active" id="nav-home" role="tabpanel" aria-labelledby="nav-home-tab">
                    <div class="row justify-content-center">
                        <div class="col-md-7 col-lg-6 col-xl-6">
                            <form id="edit_user_form"
                                  method="post"
                                  action="${pageContext.request.contextPath}/controller?command=edit_user_profile"
                                  enctype='multipart/form-data'>
                                <input type="hidden" name="user_id" value="${requestScope.user.getId()}">
                                <input type="hidden" name="original_avatar" value="${requestScope.user.getAvatar()}">

                                <div class="form-outline mb-4 mt-4">
                                    <input required
                                           name="name"
                                           type="text"
                                           id="user_name_input"
                                           value="${requestScope.user.getName()}"
                                           class="form-control form-control-lg"
                                           placeholder=<fmt:message key="label.name"/>/>
                                    <div class="error-hint text-danger custom_hidden"><fmt:message key="message.wrong.user.name"/></div>
                                    <c:if test="${sessionScope.editUserInformationValidationFeedback.name != null}">
                                        <div class="text-danger">
                                            <fmt:message key="${sessionScope.editUserInformationValidationFeedback.name}"/>
                                        </div>
                                    </c:if>
                                </div>
                                <div class="form-outline mb-4 mt-4">
                                    <input required
                                           name="email"
                                           type="email"
                                           id="user_email_input"
                                           value="${requestScope.user.getEmail()}"
                                           class="form-control form-control-lg"
                                           placeholder=<fmt:message key="label.email"/>/>
                                    <div class="error-hint text-danger custom_hidden"><fmt:message key="message.wrong.email"/></div>
                                    <c:if test="${sessionScope.editUserInformationValidationFeedback.email != null}">
                                        <div class="text-danger">
                                            <fmt:message key="${sessionScope.editUserInformationValidationFeedback.email}"/>
                                        </div>
                                    </c:if>
                                </div>
                                <div class="form-outline mb-4 mt-4">
                                    <input type="file" class="form-control" name="avatar" id="fileInput"
                                           accept="image/png"/>
                                    <c:if test="${sessionScope.editUserInformationValidationFeedback.picture != null}">
                                        <div class="text-danger">
                                            <fmt:message key="${sessionScope.editUserInformationValidationFeedback.picture}"/>
                                        </div>
                                    </c:if>
                                </div>
                                <div class="row mt-3 mb-3 justify-content-center">
                                    <div class="col-auto">
                                        <button type="submit"
                                                class="btn btn-outline-success btn-lg btn-block mt-4 mb-4">
                                            <fmt:message key="label.save"/>
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="tab-pane fade" id="nav-profile" role="tabpanel" aria-labelledby="nav-profile-tab">
                    <div class="row justify-content-center">
                        <div class="col-md-7 col-lg-6 col-xl-6">
                            <form id="change_password_form" method="post"
                                  action="${pageContext.request.contextPath}/controller?command=change_password">
                                <div class="form-outline mb-4 mt-4">
                                    <input required
                                           name="old_password"
                                           type="password"
                                           id="old_password_input"
                                           class="form-control form-control-lg"
                                           placeholder=<fmt:message key="label.old.password"/>/>
                                    <div class="error-hint text-danger custom_hidden"><fmt:message key="message.wrong.old.password"/></div>
                                    <c:if test="${sessionScope.changePasswordValidationFeedback.old_password != null}">
                                        <div class="text-danger">
                                            <fmt:message key="${sessionScope.changePasswordValidationFeedback.old_password}"/>
                                        </div>
                                    </c:if>
                                </div>
                                <div class="form-outline mb-4">
                                    <input required
                                           name="new_password"
                                           type="password"
                                           id="new_password_input"
                                           class="form-control form-control-lg"
                                           placeholder=<fmt:message key="label.new.password"/>/>
                                    <div class="error-hint text-danger custom_hidden"><fmt:message key="message.wrong.new.password"/></div>
                                    <c:if test="${sessionScope.changePasswordValidationFeedback.new_password != null}">
                                        <div class="text-danger">
                                            <fmt:message key="${sessionScope.changePasswordValidationFeedback.new_password}"/>
                                        </div>
                                    </c:if>
                                </div>
                                <div class="form-outline mb-4">
                                    <input name="repeat_new_password"
                                           type="password"
                                           id="repeat_new_password_input"
                                           class="form-control form-control-lg"
                                           placeholder=<fmt:message key="label.repeat.new.password"/>/>
                                    <div class="error-hint text-danger custom_hidden"><fmt:message key="message.wrong.new.repeated.password"/></div>
                                    <c:if test="${sessionScope.changePasswordValidationFeedback.repeat_new_password != null}">
                                        <div class="text-danger">
                                            <fmt:message key="${sessionScope.changePasswordValidationFeedback.repeat_new_password}"/>
                                        </div>
                                    </c:if>
                                </div>
                                <div class="row mt-3 mb-3 justify-content-center">
                                    <div class="col-auto">
                                        <button type="submit"
                                                class="btn btn-outline-success btn-lg btn-block mt-4 mb-4">
                                            <fmt:message key="label.save"/>
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </main>
        <div class="col-2">
        </div>
    </div>
</div>
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-2 col-auto col-lg-2 me-2">
        </div>
        <div class="col">
        </div>
    </div>
</div>
<!-- ===============MAIN END============ -->
<script src="static/js/edit_user_script.js"></script>
<c:remove var="changePasswordValidationFeedback" scope="session"/>
<c:remove var="editUserInformationValidationFeedback" scope="session"/>
</body>
</html>
