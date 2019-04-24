<%--<script type="text/javascript" src="../../static/script/js/pagination.js"></script>--%>
<%--<link rel="stylesheet" href="../../static/script/css/pagination.css">--%>
<script type="text/javascript" src="../../static/script/js/tryTattoo.js"></script>
<div class="container" style="margin-top: 4%">
    <div class="row">

        <div class="col-lg-12 col-md-12 col-sm-12 mx-auto mb-4">
            <form class="form-inline justify-content-end" action="front?command=search-tattoo" method="post">
                <input class="form-control"
                       style="margin-right: 1%"
                       type="search"
                       placeholder="<fmt:message key="text.tattoo.searchInput" bundle="${bd}"/>"
                       aria-label="Search"
                       name="name"
                >
                <button class="btn btn-outline-black" type="submit" ><fmt:message key="button.search.submit" bundle="${bd}"/></button>
            </form>
        </div>
        <c:choose>
            <c:when test="${empty sessionScope.tattoos}">
                <div class="col-lg-12 col-md-12 col-sm-12 tattoo">
                    <h1 style="color: white"><fmt:message key="text.hasNoTattoos" bundle="${bd}"/></h1>
                </div>
            </c:when>
            <c:otherwise>
                <c:forEach var="tattoo" items="${sessionScope.tattoos}">
                    <div class="col-lg-4 col-md-6 col-sm-12 tattoo" style="margin-bottom: 1%">
                        <div class="card">
                            <div class="card-img-top tattooImage">
                                <div class="fotorama">
                                    <c:forEach var="image" items="${tattoo.getImages()}">
                                        <c:if test="${image.getType().getType() != 'sketch'}">
                                            <img style="height: 400px;" src="front?command=image&name=${image.getImage()}" alt="">
                                        </c:if>
                                    </c:forEach>
                                </div>
                            </div>
                            <div class="card-body" style="padding-top:0">
                                <ul class="rating clearfix">
                                    <li class="current" style="width: ${tattoo.getRating()}%"><span class="star1" onclick="location.href='front?command=leave-rating&rating=1&tattooId=${tattoo.getId()}'"></span></li>
                                    <li><span class="star2" onclick="location.href='front?command=leave-rating&rating=2&tattooId=${tattoo.getId()}'"></span></li>
                                    <li><span class="star3" onclick="location.href='front?command=leave-rating&rating=3&tattooId=${tattoo.getId()}'"></span></li>
                                    <li><span class="star4" onclick="location.href='front?command=leave-rating&rating=4&tattooId=${tattoo.getId()}'"></span></li>
                                    <li><span class="star5" onclick="location.href='front?command=leave-rating&rating=5&tattooId=${tattoo.getId()}'"></span></li>
                                </ul>
                                <h4 class="card-title" style="text-align: center">${tattoo.getName()}</h4>

                                <button class="btn btn-outline-dark" data-toggle="modal" data-target="#buyTattoo${tattoo.getId()}" style="width: 48%"><fmt:message key="button.tattoo.buy" bundle="${bd}"/>!</button>
                                <div id="buyTattoo${tattoo.getId()}" class="modal fade" style="color: rgba(62,60,60,0.79)">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h4 class="modal-title"><fmt:message key="text.popUp.information" bundle="${bd}"/></h4>
                                                <h4><fmt:message key="text.popUp.payment" bundle="${bd}"/></h4>
                                            </div>
                                            <div class="modal-body">
                                                <button class="btn btn-primary" type="button" onclick="location.href='front?command=order-tattoo&tattooId=${tattoo.getId()}'"><fmt:message key="button.popUp.yes" bundle="${bd}"/></button>
                                                <button class="close" type="button" data-dismiss="modal"><fmt:message key="button.popUp.no" bundle="${bd}"/></button>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <button class="btn btn-outline-dark" data-toggle="modal" data-target="#tryTattoo${tattoo.getId()}" onclick="playVideo(${tattoo.getId()})" style="width: 48%"><fmt:message key="button.tattoo.try" bundle="${bd}"/>!</button>

                                <div id="tryTattoo${tattoo.getId()}" class="modal fade" style="color: rgba(62,60,60,0.79)">
                                    <div class="modal-dialog" style="width: 600px">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h4 class="modal-title"><fmt:message key="text.popUp.information" bundle="${bd}"/></h4>
                                                <button class="close" type="button" data-dismiss="modal" onclick="closeVideo()"><fmt:message key="button.popUp.close" bundle="${bd}"/></button>
                                            </div>
                                            <div class="modal-body">
                                                <div style="height: 500px; width: 100%; position:relative;">
                                                    <video height="100%" width="100%" id="video${tattoo.getId()}"></video>
                                                    <c:forEach var="image" items="${tattoo.getImages()}">
                                                    <c:if test="${image.getType().getType() eq 'sketch'}">
                                                        <div style="position: absolute; width: 200px; top: 35%; left : 30%">
                                                            <img style="width: 200px" src="front?command=image&name=${image.getImage()}" alt="">
                                                        </div>
                                                    </c:if>
                                                </c:forEach>
                                                </div>
                                            </div>
                                        </div>
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