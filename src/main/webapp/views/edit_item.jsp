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
<!-- ===============MAIN START============ -->
<c:if test="${!empty sessionScope.editItemFeedback}">
    <div class="position-fixed top-0 start-0 p-3" style="z-index: 11">
        <div id="editItemFeedbackToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
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
                    <fmt:message key="${sessionScope.editItemFeedback}"/>
                </div>
            </div>
        </div>
    </div>
    <script>
        const toast = new bootstrap.Toast(document.getElementById('editItemFeedbackToast'));
        toast.show()
    </script>
    <c:remove var="editItemFeedback" scope="session"/>
</c:if>
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
                    <h3><fmt:message key="label.edit.item"/></h3>
                </div>
            </div>
            <form id="edit_item_form"
                  method="post"
                  action="${pageContext.request.contextPath}/controller?command=edit_item"
                  enctype='multipart/form-data'>
                <input type="hidden" name="item_id" value="${requestScope.item.getId()}">
                <input type="hidden" name="original_picture" value="${requestScope.item.getPicture()}">
                <div class="row mt-4 mb-3">
                    <div class="col-3">
                        <h4><fmt:message key="label.title"/></h4>
                    </div>
                    <div class="col">
                        <div class="row g-3">
                            <div class="col">
                                <input required type="text" name="title" id="item_title_input" class="form-control"
                                       aria-describedby="input_name" value="${requestScope.item.getTitle()}">
                                <div class="error-hint text-danger custom_hidden"><fmt:message key="message.wrong.item.title"/></div>
                                <c:if test="${sessionScope.editItemValidationFeedback.title != null}">
                                    <div class="text-danger">
                                        <fmt:message key="${sessionScope.editItemValidationFeedback.title}"/>
                                    </div>
                                </c:if>
                            </div>
                            <div class="col-3">
                                <span id="input_name_line" class="form-text"><fmt:message key="message.title.requirements"/></span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row mt-4 mb-3">
                    <div class="col-3">
                        <h4><fmt:message key="label.price"/></h4>
                    </div>
                    <div class="col">
                        <div class="row g-3">
                            <div class="col">
                                <input required type="number" name="price" id="item_price_input" class="form-control"
                                       aria-describedby="input_name" value="${requestScope.item.getPrice()}">
                                <div class="error-hint text-danger custom_hidden"><fmt:message key="message.wrong.price"/></div>
                                <c:if test="${sessionScope.editItemValidationFeedback.price != null}">
                                    <div class="text-danger">
                                        <fmt:message key="${sessionScope.editItemValidationFeedback.price}"/>
                                    </div>
                                </c:if>
                            </div>
                            <div class="col-3">
                                <span id="input_price_list" class="form-text"><fmt:message key="message.price.requirements"/></span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row mt-4 mb-3">
                    <div class="col-3">
                        <h4><fmt:message key="label.description"/></h4>
                    </div>
                    <div class="col">
                        <div class="row g-3">
                            <div class="col">
                                <div class="form-outline">
                                    <textarea required class="form-control" name="description" id="item_description_input"
                                              rows="4">${requestScope.item.getDescription()}</textarea>
                                    <div class="error-hint text-danger custom_hidden"><fmt:message key="message.wrong.description"/></div>
                                    <c:if test="${sessionScope.editItemValidationFeedback.description != null}">
                                        <div class="text-danger">
                                            <fmt:message key="${sessionScope.editItemValidationFeedback.description}"/>
                                        </div>
                                    </c:if>
                                </div>
                            </div>
                            <div class="col-3">
                                <span id="input_description_list" class="form-text"><fmt:message key="message.description.requirements"/></span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row mt-4 mb-3">
                    <div class="col-3">
                        <h4><fmt:message key="label.contact"/></h4>
                    </div>
                    <div class="col">
                        <div class="row g-3">
                            <div class="col">
                                <input required type="text" name="contact" id="item_contact_info_input" class="form-control"
                                       value="${requestScope.item.getContact()}"
                                       aria-describedby="input_name">
                                <div class="error-hint text-danger custom_hidden"><fmt:message key="message.wrong.contact"/></div>
                                <c:if test="${sessionScope.editItemValidationFeedback.contact != null}">
                                    <div class="text-danger">
                                        <fmt:message key="${sessionScope.editItemValidationFeedback.contact}"/>
                                    </div>
                                </c:if>
                            </div>
                            <div class="col-3">
                                <span id="input_contact_info_line" class="form-text"><fmt:message key="message.contact.requirements"/></span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row mt-4 mb-3">
                    <div class="col-3">
                        <h4><fmt:message key="label.city"/></h4>
                    </div>
                    <div class="col">
                        <div class="row g-3">
                            <div class="col">
                                <select name="city" class="form-select" aria-label="Default select example">
                                    <c:set var="selected_city" value="${item.city.id}"/>
                                    <c:forEach items="${cities}" var="city">
                                        <option value="${city.id}"${selected_city == city.id ? ' selected' : ''}>
                                                ${city.getTitle()}
                                        </option>
                                    </c:forEach>
                                </select>
                                <c:if test="${sessionScope.editItemValidationFeedback.city != null}">
                                    <div class="text-danger">
                                        <fmt:message key="${sessionScope.editItemValidationFeedback.city}"/>
                                    </div>
                                </c:if>
                            </div>
                            <div class="col-3">
                                <span id="input_city_line" class="form-text"><fmt:message key="message.please.select.city"/></span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row mt-4 mb-3">
                    <div class="col-3">
                        <h4><fmt:message key="label.category"/></h4>
                    </div>
                    <div class="col">
                        <div class="row g-3">
                            <div class="col">
                                <select name="category" class="form-select" aria-label="Default select example">
                                    <c:set var="selected_category" value="${item.category.id}"/>
                                    <c:forEach items="${categories}" var="category">
                                        <option value="${category.id}"${selected_category == category.id ? ' selected' : ''}>
                                                ${category.getTitle()}
                                        </option>
                                    </c:forEach>
                                </select>
                                <c:if test="${sessionScope.editItemValidationFeedback.category != null}">
                                    <div class="text-danger">
                                        <fmt:message key="${sessionScope.editItemValidationFeedback.category}"/>
                                    </div>
                                </c:if>
                            </div>
                            <div class="col-3">
                                <span id="input_category_line" class="form-text"><fmt:message key="message.please.select.category"/></span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row mt-4 mb-3">
                    <div class="col-3">
                        <h4><fmt:message key="label.picture"/></h4>
                    </div>
                    <div class="col">
                        <div class="row g-3">
                            <div class="col">
                                <div class="mb-3">
                                    <input type="file" class="form-control" name="picture" id="fileInput"
                                           accept="image/png"/>
                                    <c:if test="${sessionScope.editItemValidationFeedback.picture != null}">
                                        <div class="text-danger">
                                            <fmt:message key="${sessionScope.editItemValidationFeedback.picture}"/>
                                        </div>
                                    </c:if>
                                </div>
                            </div>
                            <div class="col-3">
                                <span id="input_picture_line" class="form-text"><fmt:message key="message.picture.requirements"/></span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row mt-5 mb-3 justify-content-center">
                    <div class="col-auto">
                        <button class="btn btn-outline-success me-2" type="submit"><fmt:message key="label.save"/></button>
                    </div>
                </div>
            </form>
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
<script src="static/js/edit_item_script.js"></script>
</body>
</html>
