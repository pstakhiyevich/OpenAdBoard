<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="util" uri="customtags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${sessionScope.localization}" scope="session"/>
<fmt:setBundle basename="text"/>

<footer class="footer mt-auto py-3 border-top">
    <div class="container">
        <div class="row d-flex mb-2 justify-content-center">
            <div class="col-auto">
                <span><util:Greeting/></span>
            </div>
        </div>
        <div class="row d-flex justify-content-center mt-1">
            <div class="col-auto border-top">
                <img src="static/graphics/logo.svg">
                <span>© 2022 Copyright</span>
            </div>
        </div>
    </div>
</footer>