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
    <title>
        <fmt:message key="label.activation.page"/>
    </title>
</head>
<body>
<c:if test="${!empty sessionScope.activationFeedback}">
    <div class="modal fade" id="activation_modal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
         aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="staticBackdropLabel"><fmt:message key="label.notification"/></h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <fmt:message key="${sessionScope.activationFeedback}"/>
                </div>
                <div class="modal-footer">
                    <a class="btn btn-outline-primary"
                       href="${pageContext.request.contextPath}/controller?command=home_page"><fmt:message
                            key="label.home_page"/></a>
                </div>
            </div>
        </div>
    </div>
    <script>
        const activationModal = new bootstrap.Modal(document.getElementById('activation_modal'));
        activationModal.show()
    </script>
    <c:remove var="activationFeedback" scope="session"/>
</c:if>
<div class="d-flex align-items-center justify-content-center vh-100">
    <div class="text-center">
        <h3 class="display-1 fw-bold"><fmt:message key="label.activation.page"/></h3>
        <a class="btn btn-outline-primary mt-3"
           href="${pageContext.request.contextPath}/controller?command=home_page"><fmt:message
                key="label.home_page"/></a>
    </div>
</div>
</body>
</html>
