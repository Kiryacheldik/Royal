<button class="btn btn-outline-black" onclick="location.href='${pageContext.request.contextPath}/front?command=back&page=WEB-INF/view/admin-page.jsp'" style="margin-top: 1%; margin-bottom: 3%"><h3><fmt:message key="button.back" bundle="${bd}"/></h3></button>

<c:choose>
    <c:when test="${empty requestScope.images}">
        <h1 style="color: white; text-align: center"><fmt:message key="text.hasNoImages" bundle="${bd}"/></h1>
    </c:when>
    <c:otherwise>
        <div class="col-lg-12 col-md-12 col-sm-12 mx-auto" style="margin-bottom: 1%">
            <h1 style="color: white; text-align: center"><fmt:message key="text.imageList" bundle="${bd}"/></h1>
        </div>
        <c:forEach var="image" items="${requestScope.images}">
            <div class="col-lg-12 col-md-12 col-sm-12 mx-auto" style="margin-bottom: 1%">
                <div class="card">
                    <div class="card-header">
                        <h1><fmt:message key="text.image.name" bundle="${bd}"/> : ${image.getImage()}</h1>
                    </div>
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item">
                            <img src="front?command=image&name=${image.getImage()}" alt="">
                        </li>
                    </ul>
                </div>
            </div>
        </c:forEach>
    </c:otherwise>
</c:choose>