<div class="container">
    <div class="row">
        <div class="col-lg-6 col-md-6 col-sm-6" style="margin-top: 3%">
            <c:choose>
                <c:when test="${sessionScope.user.getActiveStatus().isStatus()}">
                <h1 style="color: white; text-align: center"><fmt:message key="text.user.information" bundle="${bd}"/></h1>
                <ul class="list-group">
                    <li class="list-group-item list-inline">
                        <h1><fmt:message key="text.user.name" bundle="${bd}"/> : </h1>
                        <span>${sessionScope.user.getUsername()}</span>
                    </li>
                    <li class="list-group-item list-inline">
                        <h1><fmt:message key="text.email" bundle="${bd}"/> : </h1>
                        <span>${sessionScope.user.getEmail()}</span>
                    </li>
                    <li class="list-group-item list-inline">
                        <h1><fmt:message key="text.rating" bundle="${bd}"/> : </h1>
                        <span>${sessionScope.user.getRating()}</span>
                    </li>
                </ul>
        </div>
        <div class="col-lg-4 col-md-4 col-sm-4 offset-1" style="margin-top: 3%">
            <c:choose>
                <c:when test="${sessionScope.user.getCard() == null}">
                    <h1 style="text-align: center; color: white; margin-top: 34%; margin-bottom: 26%"><fmt:message key="text.user.nonDiscount" bundle="${bd}"/></h1>
                </c:when>
                <c:otherwise>
                    <h1 style="text-align: center; color: white"><fmt:message key="text.user.card" bundle="${bd}"/></h1>
                    <div class="user-card">
                        <div style="height: 50%; margin: 4%; margin-bottom: 0"><h1 style="text-align: left"><fmt:message key="text.user.cardNumber" bundle="${bd}"/> : ${sessionScope.user.getCard().getCardNumber()}</h1></div>
                        <div style="height: 50%; margin: 4%; margin-top: 0; margin-bottom: 0"><h1 style="text-align: left"><fmt:message key="text.user.cardDiscount" bundle="${bd}"/> : ${sessionScope.user.getCard().getDiscount().getDiscount().getDiscount()}%</h1></div>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="col-lg-12 col-md-12 col-sm-12" style="text-align: center; color: white">
            <div class="user-action">
                <h1 style="color: white; text-align: center"><fmt:message key="text.users.action" bundle="${bd}"/></h1>
                <ul class="list-inline">
                    <li class="list-inline-item" style="width:20%"><button style="width: 100%" type="button" onclick="location.href='${pageContext.request.contextPath}/front?command=user-orders'" class="btn btn-outline-black"><fmt:message key="button.user.orders" bundle="${bd}"/></button></li>
                    <li class="list-inline-item" style="width:20%"><button style="width: 100%" type="button" onclick="location.href='${pageContext.request.contextPath}/front?command=user-proposals'" class="btn btn-outline-black"><fmt:message key="button.user.proposals" bundle="${bd}"/></button></li>
                    <li class="list-inline-item" style="width:20%"><button style="width: 100%" type="button" onclick="location.href='${pageContext.request.contextPath}/front?command=propose-image-page'" class="btn btn-outline-black"><fmt:message key="button.user.proposeImage" bundle="${bd}"/></button></li>
                </ul>
            </div>
        </div>
        <div class="col-lg-12 col-md-12 col-sm-12" style="text-align: center; color: white">
            <div class="user-edit">
                <h1 style="color: white; text-align: center"><fmt:message key="text.edit" bundle="${bd}"/></h1>
                <ul class="list-inline">
                    <li class="list-inline-item" style="width:20%"><button style="width: 100%" type="button" onclick="location.href='${pageContext.request.contextPath}/front?command=change-password-page'" class="btn btn-outline-black"><fmt:message key="button.user.changePassword" bundle="${bd}"/></button></li>
                    <li class="list-inline-item" style="width:20%"><button style="width: 100%" type="button" data-toggle="modal" data-target="#changeUsername${sessionScope.user.getId()}" class="btn btn-outline-black"><fmt:message key="button.user.changeUserName" bundle="${bd}"/></button></li>
                </ul>
            </div>

            <div id="changeUsername${sessionScope.user.getId()}" class="modal fade" style="color: rgba(62,60,60,0.79)">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">Change your user name</h4>
                        </div>
                        <div class="modal-body">
                            <form class="form-inline" action="front?command=change-username&userId=${sessionScope.user.getId()}" method="post">
                                <label>new User name</label>
                                <input type="text"
                                       name="username"
                                       id="username"
                                       class="form-control"
                                       placeholder="<fmt:message key="text.userName" bundle="${bd}"/>"
                                >
                                <button class="btn btn-primary" type="submit" style="margin-left: 1%">change username</button>
                            </form>
                            <button class="close" type="button" data-dismiss="modal"><fmt:message key="button.popUp.close" bundle="${bd}"/></button>
                        </div>
                    </div>
                </div>
            </div>
                </c:when>
            <c:otherwise>
                <h1 style="color: white; text-align: center">Sorry, but you are blocked!</h1>
            </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
