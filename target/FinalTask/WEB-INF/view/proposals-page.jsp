<button class="btn btn-outline-black" onclick="location.href='${pageContext.request.contextPath}/front?command=back&page=WEB-INF/view/user-page.jsp'" style="margin-top: 1%; margin-bottom: 3%"><h3><fmt:message key="button.back" bundle="${bd}"/></h3></button>
<div class="container">
    <div class="row">

        <div class="col-lg-12 col-md-12 col-sm-12 col-12">
            <h1 style="color: white; text-align: center; margin-bottom: 3%"><fmt:message key="text.userProposalsList" bundle="${bd}"/></h1>
        </div>

        <c:choose>
            <c:when test="${empty requestScope.proposals}">
                <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                    <h1 style="color: white; margin-bottom: 3%"><fmt:message key="text.userHasNoProposals" bundle="${bd}"/></h1>
                </div>
            </c:when>
            <c:otherwise>
                <c:forEach var="proposal" items="${requestScope.proposals}">

                    <c:if test="${proposal.getState().getStatus().getStatusName() eq 'submitted'}">
                        <div class="col-lg-12 col-md-12 col-sm-12 mx-auto" style="margin-bottom: 1%">
                            <div class="card">
                                <div class="card-header">
                                    <h1><fmt:message key="text.state" bundle="${bd}"/> : <fmt:message key="text.state1" bundle="${bd}"/></h1>
                                </div>
                                <div class="card-img mx-auto">
                                    <img style="width: 400px; height: 400px" src="front?command=image&name=${proposal.getImage().getImage()}" alt="">
                                </div>
                                <ul class="list-group list-group-flush">
                                    <li class="list-group-item">
                                        <fmt:message key="text.date" bundle="${bd}"/> : <p>${proposal.getDate()}</p>
                                    </li>
                                    <li class="list-group-item">
                                        <p><fmt:message key="text.proposal.userFirstState" bundle="${bd}"/></p>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </c:if>

                    <c:if test="${proposal.getState().getStatus().getStatusName() eq 'accepted'}">
                        <div class="col-lg-12 col-md-12 col-sm-12 mx-auto" style="margin-bottom: 1%">
                            <div class="card">
                                <div class="card-header">
                                    <h1><fmt:message key="text.state" bundle="${bd}"/> : <fmt:message key="text.state7" bundle="${bd}"/></h1>
                                </div>
                                <div class="card-img">
                                    <img style="width: 400px; height: 400px" src="front?command=image&name=${proposal.getImage().getImage()}" alt="">
                                </div>
                                <ul class="list-group list-group-flush">
                                    <li class="list-group-item">
                                        <fmt:message key="text.date" bundle="${bd}"/> : <p>${proposal.getDate()}</p>
                                    </li>
                                    <li class="list-group-item">
                                        <p><fmt:message key="text.proposal.userAcceptState" bundle="${bd}"/> ${proposal.getRating()}!</p>
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