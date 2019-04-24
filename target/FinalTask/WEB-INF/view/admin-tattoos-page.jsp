<button class="btn btn-outline-black" onclick="location.href='${pageContext.request.contextPath}/front?command=back&page=WEB-INF/view/admin-page.jsp'" style="margin-top: 1%; margin-bottom: 3%"><h3><fmt:message key="button.back" bundle="${bd}"/></h3></button>

    <c:choose>
        <c:when test="${empty requestScope.tattoos}">
            <div class="col-lg-12 col-md-12 col-sm-12 tattoo mx-auto" style="margin-bottom: 1%">
            <h1 style="color: white; text-align: center"><fmt:message key="text.hasNoTattoos" bundle="${bd}"/></h1>
            </div>
        </c:when>
        <c:otherwise>
            <c:forEach var="tattoo" items="${requestScope.tattoos}">
                <div class="col-lg-12 col-md-12 col-sm-12 tattoo mx-auto" style="margin-bottom: 1%">
                    <div class="card">
                        <div class="card-img-top tattooImage">
                            <div class="fotorama">
                                <c:forEach var="image" items="${tattoo.getImages()}">
                                    <img style="height: 400px;" src="front?command=image&name=${image.getImage()}" alt="">
                                </c:forEach>
                            </div>
                        </div>
                        <div class="card-body" style="padding-top: 0%">
                            <h4 class="card-title" style="text-align: center">${tattoo.getName()}</h4>
                            <button class="btn btn-outline-success" data-toggle="modal" data-target="#update${tattoo.getId()}" style="width: 48%"><fmt:message key="button.update" bundle="${bd}"/>!</button>
                            <button class="btn btn-outline-danger" style="width: 48%"><fmt:message key="button.delete" bundle="${bd}"/>!</button>
                        </div>
                    </div>
                </div>

                <div id="update${tattoo.getId()}" class="modal fade" style="color: rgba(62,60,60,0.79)">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h4 class="modal-title"><fmt:message key="text.popUp.tattoos.update" bundle="${bd}"/></h4>
                                <button class="close" type="button" data-dismiss="modal">Close</button>
                            </div>
                            <div class="modal-body">
                                <form class="form-inline" action="front?command=next-proposal-step&id=${tattoo.getId()}&accepted=true" method="post">
                                    <input class="form-control"
                                           style="margin-right: 1%"
                                           type="search"
                                           placeholder="<fmt:message key="text.tattoo.name" bundle="${bd}"/>"
                                           name="rating"
                                    >
                                    <button class="btn btn-primary" type="submit" ><fmt:message key="button.update" bundle="${bd}"/></button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </c:otherwise>
    </c:choose>