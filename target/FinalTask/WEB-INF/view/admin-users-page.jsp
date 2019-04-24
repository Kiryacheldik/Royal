<button class="btn btn-outline-black" onclick="location.href='${pageContext.request.contextPath}/front?command=back&page=WEB-INF/view/admin-page.jsp'" style="margin-top: 1%; margin-bottom: 3%"><h3><fmt:message key="button.back" bundle="${bd}"/></h3></button>

<div class="container">
    <div class="row">
        <c:choose>
            <c:when test="${empty requestScope.clients}">
                <h1 style="color: white"><fmt:message key="text.hasNoUsers" bundle="${bd}"/></h1>
            </c:when>
            <c:otherwise>
                <c:forEach var="client" items="${requestScope.clients}">
                    <div class="col-lg-12 col-md-12 col-sm-12 mx-auto" style="margin-bottom: 1%">
                        <div class="card">
                            <ul class="list-group list-group-flush">
                                <li class="list-group-item">
                                    <fmt:message key="text.userLogin" bundle="${bd}"/> : <p>${client.getLogin()}</p>
                                </li>
                                <li class="list-group-item">
                                    <fmt:message key="text.userName" bundle="${bd}"/> : <p>${client.getUsername()}</p>
                                </li>
                                <li class="list-group-item">
                                    <fmt:message key="text.rating" bundle="${bd}"/> : <p>${client.getRating()}</p>
                                </li>
                                <c:if test="${not empty client.getCard()}">
                                    <li class="list-group-item">
                                        <h2><fmt:message key="text.discount.userHasDiscount" bundle="${bd}"/></h2>
                                        <fmt:message key="text.user.cardNumber" bundle="${bd}"/> : <h3>${client.getCard().getCardNumber()}</h3>
                                        <fmt:message key="text.user.cardDiscount" bundle="${bd}"/> : <h3>${client.getCard().getDiscount().getDiscount().getDiscount()}</h3>
                                    </li>
                                </c:if>
                                <li class="list-group-item">
                                    <c:choose>
                                        <c:when test="${client.getActiveStatus().isStatus()}">
                                            <c:choose>
                                                <c:when test="${ empty client.getCard()}">
                                                    <button class="btn btn-outline-success" data-toggle="modal" data-target="#giveCard${client.getId()}" style="width: 48%"><fmt:message key="button.admin.giveCard" bundle="${bd}"/></button>
                                                </c:when>
                                                <c:otherwise>
                                                    <button class="btn btn-outline-success" data-toggle="modal" data-target="#updateCard${client.getId()}" style="width: 48%"><fmt:message key="button.discount.update" bundle="${bd}"/></button>
                                                </c:otherwise>
                                            </c:choose>
                                            <button class="btn btn-outline-danger" data-toggle="modal" data-target="#blockUser${client.getId()}" style="width: 48%"><fmt:message key="button.blockUser" bundle="${bd}"/></button>
                                        </c:when>
                                        <c:otherwise>
                                            <h1>User is blocked!</h1>
                                            <button class="btn btn-outline-success" data-toggle="modal" data-target="#blockUser${client.getId()}" onclick="location.href='${pageContext.request.contextPath}/front?command=unBlock-user&userId=${client.getId()}'" style="width: 48%">Unblock user</button>
                                        </c:otherwise>
                                    </c:choose>
                                </li>
                            </ul>
                        </div>

                        <div id="giveCard${client.getId()}" class="modal fade" style="color: rgba(62,60,60,0.79)">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h4 class="modal-title"><fmt:message key="text.popUp.discount.information1" bundle="${bd}"/></h4>
                                        <h4><fmt:message key="text.popUp.discount.information2" bundle="${bd}"/> : ${client.getRating()}</h4>
                                    </div>
                                    <div class="modal-body">
                                        <form class="form-inline" action="front?command=give-card&userId=${client.getId()}" method="post">
                                            <label><fmt:message key="text.popUp.discount.information1" bundle="${bd}"/> :</label>
                                            <select class="form-control" name="discount">
                                                <c:forEach var="discount" items="${requestScope.discounts}">
                                                    <option>${discount}</option>
                                                </c:forEach>
                                            </select>
                                            <button class="btn btn-primary" type="submit" style="margin-left: 1%"><fmt:message key="button.admin.giveCard" bundle="${bd}"/></button>
                                        </form>
                                        <button class="close" type="button" data-dismiss="modal"><fmt:message key="button.popUp.close" bundle="${bd}"/></button>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div id="updateCard${client.getId()}" class="modal fade" style="color: rgba(62,60,60,0.79)">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h4 class="modal-title"><fmt:message key="text.popUp.discount.information3" bundle="${bd}"/></h4>
                                        <h4><fmt:message key="text.popUp.discount.information2" bundle="${bd}"/> : ${client.getRating()}</h4>
                                    </div>
                                    <div class="modal-body">
                                        <form class="form-inline" action="front?command=update-card&cardId=${client.getCard().getId()}" method="post">
                                            <label for="sel${client.getId()}">Select discount:</label>
                                            <select class="form-control" id="sel${client.getId()}" name="discount">
                                                <c:forEach var="discount" items="${requestScope.discounts}">
                                                    <option>${discount}</option>
                                                </c:forEach>
                                            </select>
                                            <button class="btn btn-primary" type="submit"><fmt:message key="button.admin.updateCard" bundle="${bd}"/></button>
                                        </form>
                                        <button class="close" type="button" data-dismiss="modal"><fmt:message key="button.popUp.close" bundle="${bd}"/></button>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div id="blockUser${client.getId()}" class="modal fade" style="color: rgba(62,60,60,0.79)">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h4 class="modal-title">Are you sure you want to block this user?</h4>
                                    </div>
                                    <div class="modal-body">
                                        <button class="btn btn-danger" onclick="location.href='${pageContext.request.contextPath}/front?command=block-user&userId=${client.getId()}'" type="submit">Block</button>
                                        <button class="close" type="button" data-dismiss="modal"><fmt:message key="button.popUp.no" bundle="${bd}"/></button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
</div>
