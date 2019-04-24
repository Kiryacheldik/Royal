<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="../../static/script/js/jquery.validate.js"></script>
<c:if test="${applicationScope.bd.getLocale() eq 'en_EN'}">
    <script type="text/javascript" src="../../static/script/js/EN_RegistrationFormValidate.js"></script>
</c:if>
<c:if test="${applicationScope.bd.getLocale() eq 'ru_RU'}">
    <script type="text/javascript" src="../../static/script/js/RU_RegistrationFormValidate.js"></script>
</c:if>
<div class="container">
    <div class="row">
        <div class="col-lg-6 col-md-6 col-sm-6 mx-auto registrationForm">
            <h1><fmt:message key="text.registration" bundle="${bd}"/></h1>
            <c:if test="${requestScope.loginError}">
                <div class="text-center mb-4 p-3 alert alert-danger" style="text-align: center">
                    <p class="alert-danger"><fmt:message key="message.registration" bundle="${bd}"/>!</p>
                </div>
            </c:if>
            <form id="registrationForm" action="front?command=register-user" method="post" style="color: white">
                <div class="form-group email">
                    <label><fmt:message key="text.email" bundle="${bd}"/></label>
                    <input type="email"
                           name="email"
                           id="email"
                           class="form-control"
                           placeholder="<fmt:message key="text.email" bundle="${bd}"/>"
                    >
                </div>
                <div class="form-group login">
                    <label><fmt:message key="text.login" bundle="${bd}"/></label>
                    <input type="text"
                           name="login"
                           id="login"
                           class="form-control"
                           placeholder="<fmt:message key="text.login" bundle="${bd}"/>"
                    >
                </div>
                <div class="form-group password">
                    <label><fmt:message key="text.password" bundle="${bd}"/></label>
                    <%--<label *ngIf="password.invalid && password.touched" style="color: #c70f22; margin-left: 1%;">Empty field</label>--%>
                    <input type="password"
                           name="password"
                           id="password"
                           class="form-control"
                           placeholder="<fmt:message key="text.password" bundle="${bd}"/>"
                    >
                </div>
                <div class="form-group confirmPassword">
                    <label><fmt:message key="text.confirmPassword" bundle="${bd}"/></label>
                    <%--<label *ngIf="password.invalid && password.touched" style="color: #c70f22; margin-left: 1%;">Empty field</label>--%>
                    <input type="password"
                           name="confirmPassword"
                           id="confirmPassword"
                           class="form-control"
                           placeholder="<fmt:message key="text.placeHolder.confirmPassword" bundle="${bd}"/>"
                    >
                </div>
                <div class="form-group username">
                    <label><fmt:message key="text.userName" bundle="${bd}"/></label>
                    <input type="text"
                           name="username"
                           id="username"
                           class="form-control"
                           placeholder="<fmt:message key="text.userName" bundle="${bd}"/>"
                    >
                </div>
                <div class="form-row text-center">
                    <div class="col-12">
                        <button type="submit" class="btn btn-primary" style="width: 35%"><fmt:message key="button.registration" bundle="${bd}"/></button>
                    </div>
                    <div class="col-12" style="margin-top: 2%">
                        <p><fmt:message key="text.registration.haveAccount" bundle="${bd}"/> <a class="link" href="${pageContext.request.contextPath}/front?command=log-in-page"><u><fmt:message key="button.header.login" bundle="${bd}"/></u></a>!</p>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>