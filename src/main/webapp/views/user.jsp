<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="util" uri="customtags" %>

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
    <title><fmt:message key="label.user.page"/></title>
</head>
<body>
<jsp:include page="header.jsp"/>
<!-- ===============MAIN START============ -->
<main class="flex-shrink-0">
    <div class="container-fluid mt-3 mb-1">
        <div class="row">
            <div class="col-sm-3 ">
            </div>
            <div class="col ms-2 border-bottom">
                <div class="row justify-content-between">
                    <div class="col-auto d-flex align-items-end">
                        <h4 class="text-muted fw-normal">${requestScope.requested_user.getName()} <fmt:message
                                key="label.items"/></h4>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-2 col-md-2 m-1 border-end">
                <div class="card border-0">
                    <div class="card-body text-center">
                        <img class="card-img-top rounded-circle w-50"
                             src="${pageContext.request.contextPath}/upload?upload_file_path=${requestScope.requested_user.getAvatar()}"
                             alt="Card image cap">
                    </div>
                    <div class="card-body">
                        <h5 class="card-title">${requestScope.requested_user.getName()}</h5>
                        <p class="card-text">${requestScope.requested_user.getRole()}</p>
                    </div>
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item">
                            <fmt:message key="message.member.for"/>
                            <util:Time userTime="${requestScope.requested_user.getRegistrationDate()}"/>
                        <li class="list-group-item"><fmt:message key="label.number.of.lots"/>: ${requestScope.numberOfItems}</li>
                    </ul>
                    <c:if test="${sessionScope.user.id == requestScope.requested_user.getId()}">
                        <div class="card-body text-center">
                            <a class="btn btn-outline-success me-2"
                               href="${pageContext.request.contextPath}/controller?command=edit_user_page&user_id=${requestScope.requested_user.getId()}"><fmt:message
                                    key="message.edit.profile"/></a>
                        </div>
                    </c:if>
                </div>
            </div>
            <div class="col m-1 mt-1">
                <div class="row row-cols-auto justify-content-center">
                    <c:if test="${!empty items}">
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
                                            <fmt:message key="message.last.updated"/> <util:Time
                                                itemTime="${item.getUpdateTime()}"/>
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </c:if>

                    <c:if test="${empty items}">
                        <h3 class="text-muted mt-3">
                            <fmt:message key="message.empty"/>
                        </h3>
                    </c:if>

                </div>
            </div>
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
                                   href="${pageContext.request.contextPath}/controller?command=user_page&user_id=${requestScope.requested_user.getId()}&items_per_page=${items_per_page}&page=${page-1}"><fmt:message
                                        key="label.previous"/></a>
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
                                           href="${pageContext.request.contextPath}/controller?command=user_page&user_id=${requestScope.requested_user.getId()}&items_per_page=${items_per_page}&page=${i}">${i}</a>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                        <c:if test="${current_page lt number_of_pages}">
                            <li class="page-item">
                                <a class="page-link"
                                   href="${pageContext.request.contextPath}/controller?command=user_page&user_id=${requestScope.requested_user.getId()}&items_per_page=${items_per_page}&page=${current_page+1}"><fmt:message
                                        key="label.next"/></a>
                            </li>
                        </c:if>
                    </ul>
                </nav>
            </div>
        </div>
    </div>
</main>
<jsp:include page="footer.jsp"/>
</body>
</html>
