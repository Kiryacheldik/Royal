<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="../../static/script/js/jquery.validate.js"></script>
<c:if test="${applicationScope.bd.getLocale() eq 'en_EN'}">
    <script type="text/javascript" src="../../static/script/js/EN_LoginFormvalidate.js"></script>
</c:if>
<c:if test="${applicationScope.bd.getLocale() eq 'ru_RU'}">
    <script type="text/javascript" src="../../static/script/js/RU_LoginFormValidate.js"></script>
</c:if>

<div class="container">
     <div class="row">
         <div class="col-lg-6 col-md-6 col-sm-6 mx-auto loginForm" style="margin-bottom: 10%; margin-top: 6%; color: white">
             <h1><fmt:message key="text.login.title" bundle="${bd}"/></h1>
             <c:if test="${requestScope.loginError}">
                 <div class="text-center mb-4 p-3 alert alert-danger" style="text-align: center">
                     <p class="alert-danger"><fmt:message key="message.login" bundle="${bd}"/></p>
                 </div>
             </c:if>
             <form action="front?command=login-user" method="post" id="loginForm">
                 <div class="form-group">
                     <label><fmt:message key="text.login" bundle="${bd}"/></label>
                     <input class="form-control"
                            type="text"
                            name="login"
                            id="login"
                            placeholder="<fmt:message key="text.login" bundle="${bd}"/>"
                     >
                 </div>
                 <div class="form-group">
                     <label><fmt:message key="text.password" bundle="${bd}"/></label>
                     <input class="form-control"
                            type="password"
                            name="password"
                            id="password"
                            placeholder="<fmt:message key="text.password" bundle="${bd}"/>"
                     >
                 </div>
                 <div class="form-group">
                     <label><fmt:message key="text.confirmPassword" bundle="${bd}"/></label>
                     <input class="form-control"
                            type="password"
                            name="confirmPassword"
                            id="confirmPassword"
                            placeholder="<fmt:message key="text.placeHolder.confirmPassword" bundle="${bd}"/>"
                     >
                 </div>
                 <div class="form-check">
                     <input type="checkbox" class="form-check-input" id="exampleCheck1">
                     <label class="form-check-label" for="exampleCheck1"><fmt:message key="checkBox.remember" bundle="${bd}"/></label>
                 </div>
                 <div class="form-row text-center">
                     <div class="col-12">
                         <button type="submit" class="btn btn-primary" style="width: 35%"><fmt:message key="button.header.login" bundle="${bd}"/></button>
                     </div>
                     <div class="col-12" style="margin-top: 2%">
                         <a style="margin-top: 1%" class="link" href="${pageContext.request.contextPath}/front?command=log-in-page"><u><fmt:message key="text.forgotPassword" bundle="${bd}"/></u></a>
                         <h4 style="margin-top: 1%; margin-bottom: 1%"><fmt:message key="text.or" bundle="${bd}"/></h4>
                         <p><fmt:message key="text.newToSalon" bundle="${bd}"/></p>
                         <button type="button" class="btn btn-primary" style="width: 20%" onclick="location.href='${pageContext.request.contextPath}/front?command=registration-page'"><fmt:message key="button.login.signIn" bundle="${bd}"/></button>
                     </div>
                 </div>
             </form>
         </div>
     </div>
 </div>