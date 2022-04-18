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
<c:if test="${sessionScope.addItemFeedback != null}">
    <div class="modal fade" id="myModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
         aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="staticBackdropLabel"><fmt:message key="massage.congratulations"/></h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <fmt:message key="${sessionScope.addItemFeedback}"/>
                </div>
                <div class="modal-footer">
                    <a class="btn btn-outline-primary"
                       href="${pageContext.request.contextPath}/controller?command=home_page"><fmt:message
                            key="label.home_page"/></a>
                    <a class="btn btn-outline-primary"
                       href="${pageContext.request.contextPath}/controller?command=add_item_page"><fmt:message
                            key="label.add.item"/></a>
                </div>
            </div>
        </div>
    </div>
    <script>
        const myModal = new bootstrap.Modal(document.getElementById('myModal'));
        myModal.show()
    </script>
    <c:remove var="addItemFeedback" scope="session"/>
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
                    <h3><fmt:message key="label.add.new.item.form"/></h3>
                </div>
            </div>
            <form id="add_item_form"
                  method="post"
                  action="${pageContext.request.contextPath}/controller?command=add_item"
                  enctype='multipart/form-data'>
                <div class="row mt-4 mb-3">
                    <div class="col-3">
                        <h4><fmt:message key="label.title"/></h4>
                    </div>
                    <div class="col">
                        <div class="row g-3 align-items-center">
                            <div class="col">
                                <input required type="text" name="title" id="item_title_input" class="form-control"
                                       aria-describedby="input_name">
                                <div class="error-hint text-danger custom_hidden"><fmt:message
                                        key="label.wrong.title"/></div>
                                <c:if test="${sessionScope.validationFeedback.title != null}">
                                    <div class="text-danger">
                                        <fmt:message key="${sessionScope.validationFeedback.title}"/>
                                    </div>
                                </c:if>
                            </div>
                            <div class="col-3">
                                <span id="input_name_line" class="form-text"><fmt:message
                                        key="message.title.requirements"/></span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row mt-4 mb-3">
                    <div class="col-3">
                        <h4><fmt:message key="label.price"/></h4>
                    </div>
                    <div class="col">
                        <div class="row g-3 align-items-center">
                            <div class="col">
                                <input required type="number" name="price" id="item_price_input" class="form-control"
                                       aria-describedby="input_name">
                                <div class="error-hint text-danger custom_hidden"><fmt:message
                                        key="message.wrong.price"/></div>
                                <c:if test="${sessionScope.validationFeedback.price != null}">
                                    <div class="text-danger">
                                        <fmt:message key="${sessionScope.validationFeedback.price}"/>
                                    </div>
                                </c:if>
                            </div>
                            <div class="col-3">
                                <span id="input_price_list" class="form-text"><fmt:message
                                        key="message.price.requirements"/></span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row mt-4 mb-3">
                    <div class="col-3">
                        <h4><fmt:message key="label.description"/></h4>
                    </div>
                    <div class="col">
                        <div class="row g-3 align-items-center">
                            <div class="col">
                                <div class="form-outline">
                                    <textarea required class="form-control" name="description"
                                              id="item_description_input"
                                              rows="4"></textarea>
                                    <div class="error-hint text-danger custom_hidden"><fmt:message
                                            key="message.wrong.description"/></div>
                                    <c:if test="${sessionScope.validationFeedback.description != null}">
                                        <div class="text-danger">
                                            <fmt:message key="${sessionScope.validationFeedback.description}"/>
                                        </div>
                                    </c:if>
                                </div>
                            </div>
                            <div class="col-3">
                                <span id="input_description_list" class="form-text"><fmt:message
                                        key="message.description.requirements"/></span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row mt-4 mb-3">
                    <div class="col-3">
                        <h4><fmt:message key="label.contact"/></h4>
                    </div>
                    <div class="col">
                        <div class="row g-3 align-items-center">
                            <div class="col">
                                <input required type="text" name="contact" id="item_contact_info_input"
                                       class="form-control"
                                       aria-describedby="input_name">
                                <div class="error-hint text-danger custom_hidden"><fmt:message
                                        key="message.wrong.contact"/></div>
                                <c:if test="${sessionScope.validationFeedback.contact != null}">
                                    <div class="text-danger">
                                        <fmt:message key="${sessionScope.validationFeedback.contact}"/>
                                    </div>
                                </c:if>
                            </div>
                            <div class="col-3">
                                <span id="input_contact_info_line" class="form-text"><fmt:message
                                        key="message.contact.requirements"/></span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row mt-4 mb-3">
                    <div class="col-3">
                        <h4><fmt:message key="label.city"/></h4>
                    </div>
                    <div class="col">
                        <div class="row g-3 align-items-center">
                            <div class="col">
                                <select name="city" class="form-select" aria-label="Default select example">
                                    <c:forEach items="${cities}" var="city">
                                        <option name="others" value=${city.getId()}>
                                                ${city.getTitle()}
                                        </option>
                                    </c:forEach>
                                </select>
                                <c:if test="${sessionScope.validationFeedback.city != null}">
                                    <div class="text-danger">
                                        <fmt:message key="${sessionScope.validationFeedback.city}"/>
                                    </div>
                                </c:if>
                            </div>
                            <div class="col-3">
                                <span id="input_city_line" class="form-text"><fmt:message
                                        key="message.please.select.city"/></span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row mt-4 mb-3">
                    <div class="col-3">
                        <h4><fmt:message key="label.category"/></h4>
                    </div>
                    <div class="col">
                        <div class="row g-3 align-items-center">
                            <div class="col">
                                <select name="category" class="form-select" aria-label="Default select example">
                                    <c:forEach items="${categories}" var="category">
                                        <option name=${category.getTitle()} value=${category.getId()}>
                                                ${category.getTitle()}
                                        </option>
                                    </c:forEach>
                                </select>
                                <c:if test="${sessionScope.validationFeedback.category != null}">
                                    <div class="text-danger">
                                        <fmt:message key="${sessionScope.validationFeedback.category}"/>
                                    </div>
                                </c:if>
                            </div>
                            <div class="col-3">
                                <span id="input_category_line" class="form-text"><fmt:message
                                        key="message.please.select.category"/></span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row mt-4 mb-3">
                    <div class="col-3">
                        <h4><fmt:message key="label.price"/></h4>
                    </div>
                    <div class="col">
                        <div class="row g-3 align-items-center">
                            <div class="col">
                                <div class="mb-3">
                                    <input required type="file" class="form-control" name="picture" id="file_input"
                                           accept="image/png"/>
                                    <div class="error-hint text-danger custom_hidden"><fmt:message
                                            key="message.wrong.picture"/></div>
                                    <c:if test="${sessionScope.validationFeedback.picture != null}">
                                        <div class="text-danger">
                                            <fmt:message key="${sessionScope.validationFeedback.picture}"/>
                                        </div>
                                    </c:if>
                                </div>
                            </div>
                            <div class="col-3">
                                <span id="input_picture_line" class="form-text"><fmt:message
                                        key="message.picture.requirements"/></span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row mt-5 mb-3 justify-content-center">
                    <div class="col-auto">
                        <button class="btn btn-outline-success me-2" type="submit"><fmt:message
                                key="label.submit"/></button>
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
<script src="static/js/add_item_script.js"></script>
</body>
</html>