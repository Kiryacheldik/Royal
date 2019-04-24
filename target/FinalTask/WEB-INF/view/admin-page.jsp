<div class="container" style="margin-bottom: 5%; margin-top: 5%">
    <div class="row">
        <div class="col-lg-12 col-md-12 col-sm-12" style="color: white; text-align: center">
            <h1><fmt:message key="text.admin.welcome" bundle="${bd}"/></h1>
            <div class="admin-action">
                <h1 style="color: white; text-align: center"><fmt:message key="text.users.action" bundle="${bd}"/></h1>
                <ul class="list-inline">
                    <li class="list-inline-item"><button type="button" onclick="location.href='${pageContext.request.contextPath}/front?command=all-users'" class="btn btn-outline-black"><fmt:message key="button.admin.users" bundle="${bd}"/></button></li>
                    <li class="list-inline-item"><button type="button" onclick="location.href='${pageContext.request.contextPath}/front?command=all-orders'" class="btn btn-outline-black"><fmt:message key="button.admin.orders" bundle="${bd}"/></button></li>
                    <li class="list-inline-item"><button type="button" onclick="location.href='${pageContext.request.contextPath}/front?command=all-proposals'" class="btn btn-outline-black"><fmt:message key="button.admin.proposals" bundle="${bd}"/></button></li>
                    <li class="list-inline-item"><button type="button" onclick="location.href='${pageContext.request.contextPath}/front?command=all-tattoos'" class="btn btn-outline-black"><fmt:message key="button.admin.tattoos" bundle="${bd}"/></button></li>
                    <li class="list-inline-item"><button type="button" onclick="location.href='${pageContext.request.contextPath}/front?command=all-images'" class="btn btn-outline-black"><fmt:message key="button.admin.images" bundle="${bd}"/></button></li>
                    <li class="list-inline-item"><button type="button" onclick="location.href='${pageContext.request.contextPath}/front?command=tattoo-creator'" class="btn btn-outline-black"><fmt:message key="button.admin.createTattoo" bundle="${bd}"/></button></li>
                </ul>
            </div>

            <div class="admin-action">
                <h1 style="color: white; text-align: center"><fmt:message key="text.edit" bundle="${bd}"/></h1>
                <ul class="list-inline">
                    <li class="list-inline-item"><button type="button" onclick="location.href='${pageContext.request.contextPath}/front?command=change-password-page'" class="btn btn-outline-black"><fmt:message key="button.user.changePassword" bundle="${bd}"/></button></li>
                    <li class="list-inline-item"><button type="button" data-toggle="modal" data-target="#changeUsername${sessionScope.user.getId()}" class="btn btn-outline-black"><fmt:message key="button.user.changeUserName" bundle="${bd}"/></button></li>
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
        </div>
    </div>
</div>