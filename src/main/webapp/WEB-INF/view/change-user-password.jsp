<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="../../static/script/js/jquery.validate.js"></script>
<link rel="stylesheet" href="../../static/script/css/mycss.css">
<c:if test="${applicationScope.bd.getLocale() eq 'en_EN'}">
    <script type="text/javascript" src="../../static/script/js/EN_changePasswordForm.js"></script>
</c:if>
<c:if test="${applicationScope.bd.getLocale() eq 'ru_RU'}">
    <script type="text/javascript" src="../../static/script/js/RU_changePasswordForm.js"></script>
</c:if>
<c:if test="${sessionScope.user.getRole().getRole() eq 'admin'}">
    <button class="btn btn-outline-black" onclick="location.href='${pageContext.request.contextPath}/front?command=back&page=WEB-INF/view/admin-page.jsp'" style="margin-top: 1%; margin-bottom: 3%"><h3><fmt:message key="button.back" bundle="${bd}"/></h3></button>
</c:if>
<c:if test="${sessionScope.user.getRole().getRole() eq 'client'}">
    <button class="btn btn-outline-black" onclick="location.href='${pageContext.request.contextPath}/front?command=back&page=WEB-INF/view/user-page.jsp'" style="margin-top: 1%; margin-bottom: 3%"><h3><fmt:message key="button.back" bundle="${bd}"/></h3></button>
</c:if>Z
<div class="container">
    <div class="row">
        <div class="col-lg-6 col-md-6 col-sm-Z6 mx-auto registrationForm">
            <h1>Change your password</h1>Z
            <c:if test="${ not empty requestScope.incorrectPassword}">
                <c:choose>
                    <c:when test="${requestScope.incorrectPassword}">
                        <div class="text-center mb-4 p-3 alert alert-danger" style="text-align: center">
                            <p class="alert-danger">Incorrect password!</p>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="text-center mb-4 p-3 alert alert-success" style="text-align: center">
                            <p class="alert-success">Password changed!</p>
                        </div>
                    </c:otherwise>
                </c:choose>
            </c:if>
            <form id="changePassword" action="front?command=change-password" method="post" style="color: white">
                <div class="form-group oldPassword">
                    <label>Enter your old password</label>
                    <input type="password"
                           name="oldPassword"
                           id="oldPassword"
                           class="form-control"
                           placeholder="old password"
                    >
                </div>
                <div class="form-group password">
                    <label><fmt:message key="text.password" bundle="${bd}"/></label>
                    <input type="password"
                           name="newPassword"
                           id="newPassword"
                           class="form-control"
                           placeholder="<fmt:message key="text.password" bundle="${bd}"/>"
                    >
                </div>
                <div class="form-group confirmPassword">
                    <label><fmt:message key="text.confirmPassword" bundle="${bd}"/></label>
                    <input type="password"
                           name="confirmPassword"
                           id="confirmPassword"
                           class="form-control"
                           placeholder="<fmt:message key="text.placeHolder.confirmPassword" bundle="${bd}"/>"
                    >
                </div>
                <div class="form-row text-center">
                    <div class="col-12">
                        <button class="btn btn-primary" type="submit" style="margin-left: 1%">Update password</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>