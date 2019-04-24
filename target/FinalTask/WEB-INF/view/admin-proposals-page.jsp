<button class="btn btn-outline-black" onclick="location.href='${pageContext.request.contextPath}/front?command=back&page=WEB-INF/view/admin-page.jsp'" style="margin-top: 1%; margin-bottom: 3%"><h3><fmt:message key="button.back" bundle="${bd}"/></h3></button>
<div class="container">
    <div class="row">

        <c:choose>
            <c:when test="${empty requestScope.proposals}">
            <div class="col-lg-12 col-md-12 col-sm-12 mx-auto" style="margin-bottom: 1%">
                <h1 style="color: white; text-align: center"><fmt:message key="text.hasNoProposals" bundle="${bd}"/></h1>
            </div>
            </c:when>
            <c:otherwise>
                <c:forEach var="proposal" items="${requestScope.proposals}">
                    <c:if test="${proposal.getState().getStatus().getStatusName() eq 'submitted'}">
                        <div class="col-lg-12 col-md-12 col-sm-12 mx-auto" style="margin-bottom: 1%">
                            <div class="card">
                                <div class="card-header">
                                    <h1><fmt:message key="text.state" bundle="${bd}"/> : <fmt:message key="text.state2" bundle="${bd}"/></h1>
                                </div>
                                <div class="card-img mx-auto">
                                    <img style="width: 400px; height: 400px" src="front?command=image&name=${proposal.getImage().getImage()}" alt="">
                                </div>
                                <ul class="list-group list-group-flush">
                                    <li class="list-group-item">
                                        <fmt:message key="text.userName" bundle="${bd}"/> : <p>${proposal.getUser().getUsername()}</p>
                                    </li>
                                    <li class="list-group-item">
                                        <fmt:message key="text.date" bundle="${bd}"/> : <p>${proposal.getDate()}</p>
                                    </li>
                                    <li class="list-group-item">
                                        <p><fmt:message key="text.proposal.adminFirstState" bundle="${bd}"/></p>
                                    </li>
                                    <li class="list-group-item">
                                        <p><fmt:message key="text.proposal.question" bundle="${bd}"/></p>
                                        <button class="btn btn-outline-success" style="width: 48%" data-toggle="modal" data-target="#buyModel${proposal.getId()}"><fmt:message key="button.accept" bundle="${bd}"/>!</button>
                                        <button class="btn btn-outline-danger" style="width: 48%" onclick="location.href='${pageContext.request.contextPath}/front?command=next-proposal-step&id=${proposal.getId()}&accepted=false'"><fmt:message key="button.cancel" bundle="${bd}"/>!</button>
                                    </li>
                                </ul>
                            </div>
                        </div>

                        <div id="buyModel${proposal.getId()}" class="modal fade" style="color: rgba(62,60,60,0.79)">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h4 class="modal-title"><fmt:message key="text.proposal.accept.question" bundle="${bd}"/></h4>
                                        <button class="close" type="button" data-dismiss="modal">Close</button>
                                    </div>
                                    <div class="modal-body">
                                        <form class="form-inline" action="front?command=next-proposal-step&id=${proposal.getId()}&accepted=true" method="post">
                                            <input class="form-control"
                                                   style="margin-right: 1%"
                                                   type="search"
                                                   placeholder="<fmt:message key="text.rating" bundle="${bd}"/>"
                                                   name="rating"
                                            >
                                            <button class="btn btn-primary" type="submit" ><fmt:message key="button.upRating" bundle="${bd}"/></button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:if>

                    <c:if test="${proposal.getState().getStatus().getStatusName() eq 'accepted'}">
                        <div class="col-lg-12 col-md-12 col-sm-12 mx-auto" style="margin-bottom: 1%">
                            <div class="card">
                                <div class="card-header">
                                    <h1><fmt:message key="text.state" bundle="${bd}"/> : <fmt:message key="text.state7" bundle="${bd}"/></h1>
                                </div>
                                <div class="card-img justify-content-center" style="width: 100%">
                                    <img style="width: 400px; height: 400px" src="front?command=image&name=${proposal.getImage().getImage()}" alt="">
                                </div>
                                <ul class="list-group list-group-flush">
                                    <li class="list-group-item">
                                        <fmt:message key="text.userName" bundle="${bd}"/> : <p>${proposal.getUser().getUsername()}</p>
                                    </li>
                                    <li class="list-group-item">
                                        <fmt:message key="text.date" bundle="${bd}"/> : <p>${proposal.getDate()}</p>
                                    </li>
                                    <li class="list-group-item">
                                        <p><fmt:message key="text.proposal.adminAcceptState" bundle="${bd}"/> ${proposal.getRating()}!</p>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </c:if>

                    <c:if test="${proposal.getState().getStatus().getStatusName() eq 'cancelled'}">
                        <div class="col-lg-12 col-md-12 col-sm-12 mx-auto" style="margin-bottom: 1%">
                            <div class="card">
                                <div class="card-header">
                                    <h1><fmt:message key="text.state" bundle="${bd}"/> : <fmt:message key="text.state5" bundle="${bd}"/></h1>
                                </div>
                                <div class="card-img">
                                    <img style="width: 400px; height: 400px" src="front?command=image&name=${proposal.getImage().getImage()}" alt="">
                                </div>
                                <ul class="list-group list-group-flush">
                                    <li class="list-group-item">
                                        <fmt:message key="text.userName" bundle="${bd}"/> : <p>${proposal.getUser().getUsername()}</p>
                                    </li>
                                    <li class="list-group-item">
                                        <fmt:message key="text.date" bundle="${bd}"/> : <p>${proposal.getDate()}</p>
                                    </li>
                                    <li class="list-group-item">
                                        <p><fmt:message key="text.proposal.cancelState" bundle="${bd}"/></p>
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