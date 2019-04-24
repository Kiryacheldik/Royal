<button class="btn btn-outline-black" onclick="location.href='${pageContext.request.contextPath}/front?command=back&page=WEB-INF/view/admin-page.jsp'" style="margin-top: 1%; margin-bottom: 3%"><h3><fmt:message key="button.back" bundle="${bd}"/></h3></button>
<div class="container">
    <div class="row">

        <c:choose>
            <c:when test="${ empty requestScope.orders}">
                <h1 style="color: white"><fmt:message key="text.hasNoOrders" bundle="${bd}"/></h1>
            </c:when>
            <c:otherwise>
                <c:forEach var="order" items="${requestScope.orders}">

                    <c:if test="${order.getState().getStatus().getStatusName() eq 'submitted'}">
                        <div class="col-lg-12 col-md-12 col-sm-12 mx-auto" style="margin-bottom: 1%">
                            <div class="card">
                                <div class="card-header">
                                    <h1><fmt:message key="text.state" bundle="${bd}"/> : <fmt:message key="text.state2" bundle="${bd}"/></h1>
                                </div>
                                <ul class="list-group list-group-flush">
                                    <li class="list-group-item">
                                        <fmt:message key="text.userLogin" bundle="${bd}"/> : <p>${order.getUser().getLogin()}</p>
                                    </li>
                                    <li class="list-group-item">
                                        <fmt:message key="text.tattoo.name" bundle="${bd}"/> : <p>${order.getTattoo().getName()}</p>
                                    </li>

                                    <li class="list-group-item">
                                        <fmt:message key="text.date" bundle="${bd}"/> : <p>${order.getDate()}</p>
                                    </li>

                                    <li class="list-group-item">
                                        <p><fmt:message key="text.order.adminFirstState" bundle="${bd}"/></p>
                                    </li>
                                    <li class="list-group-item">
                                        <button class="btn btn-outline-success" style="width: 48%" onclick="location.href='${pageContext.request.contextPath}/front?command=next-order-step&id=${order.getId()}&accepted=true'"><fmt:message key="button.accept" bundle="${bd}"/>!</button>
                                        <button class="btn btn-outline-danger" style="width: 48%" onclick="location.href='${pageContext.request.contextPath}/front?command=next-order-step&id=${order.getId()}&accepted=false'"><fmt:message key="button.cancel" bundle="${bd}"/>!</button>
                                    </li>

                                </ul>
                            </div>
                        </div>
                    </c:if>

                    <c:if test="${order.getState().getStatus().getStatusName() eq 'accepted'}">
                        <div class="col-lg-12 col-md-12 col-sm-12 mx-auto" style="margin-bottom: 1%">
                            <div class="card">
                                <div class="card-header">
                                    <h1><fmt:message key="text.state" bundle="${bd}"/> : <fmt:message key="text.state3" bundle="${bd}"/></h1>
                                </div>
                                <ul class="list-group list-group-flush">
                                    <li class="list-group-item">
                                        <fmt:message key="text.userLogin" bundle="${bd}"/> : <p>${order.getUser().getLogin()}</p>
                                    </li>
                                    <li class="list-group-item">
                                        <fmt:message key="text.tattoo.name" bundle="${bd}"/> : <p>${order.getTattoo().getName()}</p>
                                    </li>

                                    <li class="list-group-item">
                                        <fmt:message key="text.date" bundle="${bd}"/> : <p>${order.getDate()}</p>
                                    </li>

                                    <li class="list-group-item">
                                        <p><fmt:message key="text.order.adminWaitingState" bundle="${bd}"/></p>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </c:if>

                    <c:if test="${order.getState().getStatus().getStatusName() eq 'awaitingFeedBack'}">
                        <div class="col-lg-12 col-md-12 col-sm-12 mx-auto" style="margin-bottom: 1%">
                            <div class="card">
                                <div class="card-header">
                                    <h1><fmt:message key="text.state" bundle="${bd}"/> : <fmt:message key="text.state3" bundle="${bd}"/></h1>
                                </div>
                                <ul class="list-group list-group-flush">
                                    <li class="list-group-item">
                                        <fmt:message key="text.userLogin" bundle="${bd}"/> : <p>${order.getUser().getLogin()}</p>
                                    </li>
                                    <li class="list-group-item">
                                        <fmt:message key="text.tattoo.name" bundle="${bd}"/> : <p>${order.getTattoo().getName()}</p>
                                    </li>

                                    <li class="list-group-item">
                                        <fmt:message key="text.date" bundle="${bd}"/> : <p>${order.getDate()}</p>
                                    </li>

                                    <li class="list-group-item">
                                        <p><fmt:message key="text.order.waitingState" bundle="${bd}"/></p>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </c:if>

                    <c:if test="${order.getState().getStatus().getStatusName() eq 'cancelled'}">
                        <div class="col-lg-12 col-md-12 col-sm-12 mx-auto" style="margin-bottom: 1%">
                            <div class="card">
                                <div class="card-header">
                                    <h1><fmt:message key="text.state" bundle="${bd}"/> : <fmt:message key="text.state5" bundle="${bd}"/></h1>
                                </div>
                                <ul class="list-group list-group-flush">
                                    <li class="list-group-item">
                                        <fmt:message key="text.userLogin" bundle="${bd}"/> : <p>${order.getUser().getLogin()}</p>
                                    </li>
                                    <li class="list-group-item">
                                        <fmt:message key="text.tattoo.name" bundle="${bd}"/> : <p>${order.getTattoo().getName()}</p>
                                    </li>

                                    <li class="list-group-item">
                                        <fmt:message key="text.date" bundle="${bd}"/> : <p>${order.getDate()}</p>
                                    </li>

                                    <li class="list-group-item">
                                        <p><fmt:message key="text.order.adminCancelState" bundle="${bd}"/></p>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </c:if>

                    <c:if test="${order.getState().getStatus().getStatusName() eq 'closed'}">
                        <div class="col-lg-12 col-md-12 col-sm-12 mx-auto" style="margin-bottom: 1%">
                            <div class="card">
                                <div class="card-header">
                                    <h1><fmt:message key="text.state" bundle="${bd}"/> : <fmt:message key="text.state6" bundle="${bd}"/></h1>
                                </div>
                                <ul class="list-group list-group-flush">
                                    <li class="list-group-item">
                                        <fmt:message key="text.userLogin" bundle="${bd}"/> : <p>${order.getUser().getLogin()}</p>
                                    </li>
                                    <li class="list-group-item">
                                        <fmt:message key="text.tattoo.name" bundle="${bd}"/> : <p>${order.getTattoo().getName()}</p>
                                    </li>

                                    <li class="list-group-item">
                                        <fmt:message key="text.date" bundle="${bd}"/> : <p>${order.getDate()}</p>
                                    </li>

                                    <li class="list-group-item">
                                        <c:choose>
                                            <c:when test="${order.getRating() eq -1}">
                                                <p><fmt:message key="text.order.skippedRating" bundle="${bd}"/></p>
                                            </c:when>
                                            <c:otherwise>
                                                <fmt:message key="text.rating" bundle="${bd}"/> : <p>${order.getRating()}</p>
                                            </c:otherwise>
                                        </c:choose>
                                    </li>

                                    <li class="list-group-item">
                                        <p><fmt:message key="text.order.closedState" bundle="${bd}"/></p>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </c:if>

                </c:forEach>
            </c:otherwise>
        </c:choose>

    </div>
</div>

