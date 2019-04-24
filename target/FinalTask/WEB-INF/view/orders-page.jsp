<button class="btn btn-outline-black" onclick="location.href='${pageContext.request.contextPath}/front?command=back&page=WEB-INF/view/user-page.jsp'" style="margin-top: 1%"><h3><fmt:message key="button.back" bundle="${bd}"/></h3></button>
<div class="container" style="margin-top: 4%">
    <div class="row">
        <div class="col-lg-12 col-md-12 col-sm-12 col-12">
            <h1 style="color: white; text-align: center; margin-bottom: 3%"><fmt:message key="text.userOrdersList" bundle="${bd}"/></h1>
        </div>

        <c:choose>
        <c:when test="${ empty requestScope.orders}">
            <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                <h1 style="color: white; margin-bottom: 3%"><fmt:message key="text.hasNoOrders" bundle="${bd}"/></h1>
            </div>
        </c:when>
        <c:otherwise>
        <c:forEach var="order" items="${requestScope.orders}">

        <c:if test="${order.getState().getStatus().getStatusName() eq 'submitted'}">
        <div class="col-lg-12 col-md-12 col-sm-12 mx-auto" style="margin-bottom: 1%">
            <div class="card">
                <div class="card-header">
                    <h1><fmt:message key="text.state" bundle="${bd}"/> : <fmt:message key="text.state1" bundle="${bd}"/></h1>
                </div>
                <ul class="list-group list-group-flush">
                    <li class="list-group-item">
                        <fmt:message key="text.tattoo.name" bundle="${bd}"/> : <p>${order.getTattoo().getName()}</p>
                    </li>

                    <li class="list-group-item">
                        <fmt:message key="text.date" bundle="${bd}"/> : <p>${order.getDate()}</p>
                    </li>

                    <li class="list-group-item">
                        <p><fmt:message key="text.order.userFirstState" bundle="${bd}"/></p>
                    </li>
                </ul>
            </div>
        </div>
        </c:if>

        <c:if test="${order.getState().getStatus().getStatusName() eq 'accepted'}">
        <div class="col-lg-12 col-md-12 col-sm-12 mx-auto" style="margin-bottom: 1%">
            <div class="card">
                <div class="card-header">
                    <h1><fmt:message key="text.state" bundle="${bd}"/> : <fmt:message key="text.state7" bundle="${bd}"/></h1>
                </div>
                <ul class="list-group list-group-flush">
                    <li class="list-group-item">
                        <fmt:message key="text.tattoo.name" bundle="${bd}"/> : <p>${order.getTattoo().getName()}</p>
                    </li>

                    <li class="list-group-item">
                        <fmt:message key="text.date" bundle="${bd}"/> : <p>${order.getDate()}</p>
                    </li>

                    <li class="list-group-item">
                        <p><fmt:message key="text.order.acceptState" bundle="${bd}"/></p>
                    </li>

                    <li class="list-group-item">
                        <button class="btn btn-outline-success" onclick="location.href='${pageContext.request.contextPath}/front?command=next-order-step&id=${order.getId()}&accepted=true'" style="width: 48%"><fmt:message key="button.accept" bundle="${bd}"/>!</button>
                        <button class="btn btn-outline-danger" onclick="location.href='${pageContext.request.contextPath}/front?command=next-order-step&id=${order.getId()}&accepted=false'" style="width: 48%"><fmt:message key="button.cancel" bundle="${bd}"/>!</button>
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
                        <fmt:message key="text.tattoo.name" bundle="${bd}"/> : <p>${order.getTattoo().getName()}</p>
                    </li>

                    <li class="list-group-item">
                        <fmt:message key="text.date" bundle="${bd}"/> : <p>${order.getDate()}</p>
                    </li>

                    <li class="list-group-item">
                        <p><fmt:message key="text.order.userCancelState" bundle="${bd}"/></p>
                    </li>
                </ul>
            </div>
        </div>
        </c:if>

        <c:if test="${order.getState().getStatus().getStatusName() eq 'awaitingFeedBack'}">
        <div class="col-lg-12 col-md-12 col-sm-12 mx-auto" style="margin-bottom: 1%">
            <div class="card">
                <div class="card-header">
                    <h1><fmt:message key="text.state" bundle="${bd}"/> : <fmt:message key="text.state4" bundle="${bd}"/></h1>
                </div>
                <ul class="list-group list-group-flush">
                    <li class="list-group-item">
                        <fmt:message key="text.tattoo.name" bundle="${bd}"/> : <p>${order.getTattoo().getName()}</p>
                    </li>

                    <li class="list-group-item">
                        <fmt:message key="text.date" bundle="${bd}"/> : <p>${order.getDate()}</p>
                    </li>

                    <li class="list-group-item">
                        <p><fmt:message key="text.order.userFirstState" bundle="${bd}"/></p>
                    </li>

                    <li class="list-group-item">
                        <button class="btn btn-outline-danger" style="width: 48%" onclick="location.href='${pageContext.request.contextPath}/front?command=next-order-step&id=${order.getId()}'"><fmt:message key="button.order.omit" bundle="${bd}"/>!</button>
                        <ul class="rating clearfix">
                            <li><span class="star1" onclick="location.href='front?command=next-order-step&id=${order.getId()}&rating=1'"></span></li>
                            <li><span class="star2" onclick="location.href='front?command=next-order-step&id=${order.getId()}&rating=2'"></span></li>
                            <li><span class="star3" onclick="location.href='front?command=next-order-step&id=${order.getId()}&rating=3'"></span></li>
                            <li><span class="star4" onclick="location.href='front?command=next-order-step&id=${order.getId()}&rating=4'"></span></li>
                            <li><span class="star5" onclick="location.href='front?command=next-order-step&id=${order.getId()}&rating=5'"></span></li>
                        </ul>
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
                        <fmt:message key="text.tattoo.name" bundle="${bd}"/> : <p>${order.getTattoo().getName()}</p>
                    </li>

                    <li class="list-group-item">
                        <fmt:message key="text.date" bundle="${bd}"/> : <p>${order.getDate()}</p>
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