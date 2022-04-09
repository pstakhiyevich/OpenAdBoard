<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${sessionScope.localization}" scope="session"/>
<fmt:setBundle basename="text"/>

<footer class="footer mt-auto py-3 border-top">
    <div class="container">
        <div class="row d-flex justify-content-center mt-1">
            <div class="col-auto border-top">
                <span>© 2022 Copyright: 严肃熊猫交易市场</span>
            </div>
        </div>
    </div>
</footer>