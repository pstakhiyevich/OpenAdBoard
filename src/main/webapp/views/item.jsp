<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="com.stakhiyevich.openadboard.model.entity.UserRole" %>
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
    <title><fmt:message key="label.item.page"/></title>
</head>
<body class="d-flex flex-column min-vh-100">
<jsp:include page="header.jsp"/>
<%--add/delete commend feedback toast--%>
<c:if test="${!empty sessionScope.addDeleteCommentFeedback}">
    <div class="position-fixed top-0 start-0 p-3" style="z-index: 11">
        <div id="addDeleteCommentFeedbackToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
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
                    <fmt:message key="${sessionScope.addDeleteCommentFeedback}"/>
                </div>
            </div>
        </div>
    </div>
    <script>
        const toast = new bootstrap.Toast(document.getElementById('addDeleteCommentFeedbackToast'));
        toast.show()
    </script>
    <c:remove var="addDeleteCommentFeedback" scope="session"/>
</c:if>
<%--add/delete bookmark feedback toast--%>
<c:if test="${!empty sessionScope.addDeleteBookmarkFeedback}">
    <div class="position-fixed top-0 start-0 p-3" style="z-index: 11">
        <div id="addDeleteBookmarkFeedbackToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
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
                    <fmt:message key="${sessionScope.addDeleteBookmarkFeedback}"/>
                </div>
            </div>
        </div>
    </div>
    <script>
        const toast = new bootstrap.Toast(document.getElementById('addDeleteBookmarkFeedbackToast'));
        toast.show()
    </script>
    <c:remove var="addDeleteBookmarkFeedback" scope="session"/>
</c:if>
<!-- ===============MAIN START============ -->
<div class="container-fluid mt-3 mb-1">
    <div class="row">
        <div class="col-sm-3">
        </div>
        <div class="col ms-2">
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
            <div class="row">
                <a class="text-decoration-none display-6 ms-4"
                   href="${pageContext.request.contextPath}/controller?${sessionScope.previous_command}">
                    <svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" fill="grey" class="bi bi-arrow-left"
                         viewBox="0 0 16 16">
                        <path fill-rule="evenodd"
                              d="M15 8a.5.5 0 0 0-.5-.5H2.707l3.147-3.146a.5.5 0 1 0-.708-.708l-4 4a.5.5 0 0 0 0 .708l4 4a.5.5 0 0 0 .708-.708L2.707 8.5H14.5A.5.5 0 0 0 15 8z"/>
                    </svg>
                </a>
            </div>
        </aside>
        <main class="col mt-1">
            <div class="row justify-content-between">
                <div class="col-auto">
                    <h2><a>${requestScope.item.getTitle()}</a></h2>
                </div>
                <div class="col-auto">
                    <c:if test="${sessionScope.user.getId() == requestScope.item.getUser().getId()}">
                        <a href="${pageContext.request.contextPath}/controller?command=edit_item_page&item_id=${item.id}"
                           class="btn btn-outline-primary">Edit</a>
                    </c:if>
                    <c:if test="${sessionScope.user.getId() == requestScope.item.getUser().getId() || sessionScope.user.role == UserRole.ADMIN || sessionScope.user.role == UserRole.MODER}">
                        <a href="${pageContext.request.contextPath}/controller?command=delete_item&item_id=${item.id}"
                           class="btn btn-outline-danger">Delete</a>
                    </c:if>
                </div>
            </div>
            <div class="row ">
                <div class="col">
                    <p>${requestScope.item.getCategory().getTitle()}</p>
                    <p><fmt:message key="message.last.updated"/> <util:Time itemTime="${item.getUpdateTime()}"/></p>
                </div>
            </div>
            <div class="row">
                <div class="col border-top">
                    <div class="row border-bottom mt-2 mb-3">
                        <div class="col d-flex justify-content-center mb-3">
                            <img class="img-thumbnail w-75"
                                 src="${pageContext.request.contextPath}/upload?upload_file_path=${requestScope.item.picture}">
                        </div>
                    </div>
                    <div class="row border-bottom mt-3">
                        <h6>${requestScope.item.getCity().getTitle()}</h6>
                    </div>
                    <div class="row border-bottom mt-3 mb-3">
                        <p>${requestScope.item.getDescription()}</p>
                    </div>
                    <div class="row mb-3">
                        <div class="container">
                            <div class="row d-flex justify-content-center">
                                <div class="col-md-12 col-lg-12">
                                    <c:forEach items="${comments}" var="comment">
                                        <div class="card text-dark mb-3">
                                            <div class="card-body ">
                                                <div class="d-flex justify-content-start">
                                                    <img class="rounded-circle shadow-1-strong me-3"
                                                         src="${pageContext.request.contextPath}/upload?upload_file_path=${comment.getUserAvatar()}"
                                                         alt="avatar" width="60" height="60"/>
                                                    <div class="flex-grow-1">
                                                        <h6 class="fw-bold mb-1">${comment.getUserName()} <span
                                                                class="badge bg-primary">${comment.getUserRole()}</span>
                                                        </h6>
                                                        <div class="d-flex align-items-center mb-3">
                                                            <p class="mb-0">
                                                                <util:Time itemTime="${comment.getCreateTime()}"/>
                                                            </p>
                                                        </div>
                                                    </div>
                                                    <c:if test="${sessionScope.user.getId() == comment.getUserId() || sessionScope.user.role == UserRole.ADMIN || sessionScope.user.role == UserRole.MODER}">
                                                        <div class="">
                                                            <a class="text-decoration-none display-6 ms-4"
                                                               href="${pageContext.request.contextPath}/controller?command=delete_comment&comment_id=${comment.getCommentId()}&item_id=${comment.getItemId()}">
                                                                <svg xmlns="http://www.w3.org/2000/svg" width="16"
                                                                     height="16" fill="red" class="bi bi-trash"
                                                                     viewBox="0 0 16 16">
                                                                    <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z"/>
                                                                    <path fill-rule="evenodd"
                                                                          d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"/>
                                                                </svg>
                                                            </a>
                                                        </div>
                                                    </c:if>
                                                </div>
                                                <p class="mt-3 mb-4 pb-2">${comment.getText()}</p>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                    </div>
                    <c:if test="${sessionScope.user != null}">
                        <div class="row">
                            <div class="mb-3 mt-3">
                                <div class="collapse show" id="comments-collapse">
                                    <form id="comment_form" method="post"
                                          action="${pageContext.request.contextPath}/controller?command=add_comment">
                                        <input type="hidden" name="item_id" value="${requestScope.item.getId()}">
                                        <div class="form-outline">
                                            <textarea required placeholder="post a comment here" class="form-control" name="comment_text" id="user_comment_input" rows="4"></textarea>
                                            <div class="error-hint text-danger custom_hidden">wrong comment</div>
                                            <c:if test="${sessionScope.commentValidationFeedback.commentText != null}">
                                                <div class="text-danger">
                                                    <fmt:message key="${sessionScope.commentValidationFeedback.commentText}"/>
                                                </div>
                                            </c:if>
                                        </div>
                                        <div class="mt-2 pt-1 text-end">
                                            <button class="btn btn-outline-success me-2" type="submit"><fmt:message key="label.post.comment"/></button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </c:if>
                </div>
                <div class="col-4">
                    <div class="row mb-3">
                        <h2>$ ${requestScope.item.getPrice()}</h2>
                    </div>
                    <c:if test="${sessionScope.user != null}">
                        <div class="row">
                            <div class="col mt-3 mb-3">
                                <button class="btn btn-outline-success me-2 w-100 h-100 mb-3"
                                        data-bs-target="#contactInformationModal" data-bs-toggle="modal" type="button">
                                    <fmt:message key="label.show.contact.information"/>
                                </button>
                                <!-- Modal -->
                                <div class="modal fade" id="contactInformationModal" tabindex="-1"
                                     aria-labelledby="contactInformationModalLabel"
                                     aria-hidden="true">
                                    <div class="modal-dialog modal-dialog-centered">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h4 class="modal-title" id="contactInformationModalLabel"><fmt:message key="label.contact.information"/></h4>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                        aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body">
                                                <h4>${requestScope.item.getContact()}</h4>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <c:choose>
                            <c:when test="${requestScope.is_bookmarked == true}">
                                <div class="row">
                                    <div class="col mt-1 mb-3">
                                        <form id="delete_bookmark" method="post"
                                              action="${pageContext.request.contextPath}/controller?command=delete_bookmark&item_id=${requestScope.item.getId()}">
                                            <button class="btn btn-outline-danger me-2 w-100 h-100 mb-3" type="submit">
                                                <fmt:message key="label.delete.from.bookmarks"/>
                                            </button>
                                        </form>
                                    </div>
                                </div>
                            </c:when>
                            <c:when test="${requestScope.is_bookmarked == false}">
                                <div class="row">
                                    <div class="col mt-1 mb-3">
                                        <form id="add_bookmark" method="post"
                                              action="${pageContext.request.contextPath}/controller?command=add_bookmark&item_id=${requestScope.item.getId()}">
                                            <button class="btn btn-outline-primary me-2 w-100 h-100 mb-3" type="submit">
                                                <fmt:message key="label.add.bookmarks"/>
                                            </button>
                                        </form>
                                    </div>
                                </div>
                            </c:when>
                        </c:choose>
                    </c:if>
                    <div class="row mt-4 mb-3">
                        <div class="col">
                            <div class="row mb-2">
                                <div class="col">
                                    <h8><fmt:message key="label.user.information"/></h8>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col">
                                    <h4>${requestScope.item.getUser().getName()}</h4>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col">
                                    <fmt:message key="message.member.for"/>
                                    <util:Time userTime="${requestScope.item.getUser().getRegistrationDate()}"/>
                                </div>
                            </div>
                            <c:if test="${sessionScope.user != null}">
                                <div class="row">
                                    <div class="col mt-3 mb-3">
                                        <a class="btn btn-outline-primary me-2 w-100 mb-3"
                                           href="${pageContext.request.contextPath}/controller?command=user_page&user_id=${requestScope.item.getUser().getId()}"><fmt:message key="label.check.out.all.user.items"/></a>
                                    </div>
                                </div>
                            </c:if>
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
<jsp:include page="footer.jsp"/>
<script src="static/js/item_script.js"></script>
</body>
</html>
