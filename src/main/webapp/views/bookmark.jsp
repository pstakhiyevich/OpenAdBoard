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
<body class="d-flex flex-column min-vh-100">
<jsp:include page="header.jsp"/>
<!-- ===============MAIN START============ -->
<c:choose>
    <c:when test="${!empty requestScope.bookmarks}">
        <div class="container-fluid mt-3 mb-1">
            <div class="row">
                <div class="col ms-2 border-bottom">
                    <div class="row justify-content-between">
                        <div class="col-auto ms-2 me-2">
                            <h3><fmt:message key="label.my.bookmarks"/></h3>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="container-fluid">
            <div class="row">
                <main class="col m-1 mt-1">
                    <div class="row row-cols-auto justify-content-center">
                        <c:forEach items="${bookmarks}" var="bookmark">
                            <div class="col mb-1">
                                <div class="card mx-auto " style="width: 18rem; height: 35rem;">
                                    <div class="bg-image hover-overlay ripple" data-mdb-ripple-color="light">
                                        <a href="${pageContext.request.contextPath}/controller?command=item_page&item_id=${bookmark.getItemId()}">
                                            <img src="${pageContext.request.contextPath}/upload?upload_file_path=${bookmark.getItemPicture()}"
                                                 class="img-fluid w-100">
                                        </a>
                                    </div>
                                    <div class="card-body">
                                        <h5 class="card-title font-weight-bold"><a>${bookmark.getItemTitle()}</a></h5>
                                        <ul class="list-unstyled list-inline mb-0">
                                            <li class="list-inline-item">
                                                <p class="text-danger">$ ${bookmark.getItemPrice()}</p>
                                            </li>
                                        </ul>
                                        <p class="mb-1">${bookmark.getItemCity()}</p>
                                        <hr class="my-3"/>
                                        <p class="text test_paragraph">
                                                ${bookmark.getItemDescription()}
                                        </p>
                                            <%--<p class="mb-1">${item.getUpdateTime()}</p>--%>
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
                <div class="col">
                    <nav aria-label="navigation for items">
                        <ul class="pagination justify-content-center mt-3 mb-4">
                            <c:if test="${current_page != 1}">
                                <li class="page-item">
                                    <a class="page-link"
                                       href="${pageContext.request.contextPath}/controller?command=bookmark_page&items_per_page=${items_per_page}&page=${current_page-1}">Previous</a>
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
                                               href="${pageContext.request.contextPath}/controller?command=bookmark_page&items_per_page=${items_per_page}&page=${i}">${i}</a>
                                        </li>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                            <c:if test="${current_page lt number_of_pages}">
                                <li class="page-item">
                                    <a class="page-link"
                                       href="${pageContext.request.contextPath}/controller?command=bookmark_page&items_per_page=${items_per_page}&page=${current_page+1}">Next</a>
                                </li>
                            </c:if>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <div class="row mb-4 mt-4">
            <div class="col ms-4">
                <h1 class="text-muted"><fmt:message key="message.no.bookmarks"/></h1>
            </div>
        </div>
        <div class="row mb-4 justify-content-center">
            <div class="col-auto ms-5">
                <a class="btn btn-outline-success"
                   href="${pageContext.request.contextPath}/controller?command=home_page"><fmt:message key="label.home_page"/></a>
            </div>
        </div>
    </c:otherwise>
</c:choose>
<!-- ===============MAIN END============ -->
<jsp:include page="footer.jsp"/>
</body>
</html>
