<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>
</style>


<div class="img-hover">
    <div id="name">
        <i><img src="static/image/2019-04-05%2000.07.45.jpeg" style="width: 100%; height: auto" alt="" ></i>
        <h1><span style="color: white; text-align: center; font-size: 3vw;"><fmt:message key="text.tagLine" bundle="${bd}"/></span></h1>
        <c:if test="${empty sessionScope.user}">
            <ul class="list-inline mx-auto" style="width: 80%; margin-top: 5%">
                <li class="list-inline-item" style="width: 36%"><button type="button" onclick="location.href='${pageContext.request.contextPath}/front?command=registration-page'" class="btn btn-outline-black btn-block"><fmt:message key="button.header.registration" bundle="${bd}"/></button></li>
                <li class="list-inline-item"><img src="static/image/emblem.jpeg" alt="emblem"></li>
                <li class="list-inline-item" style="width: 36%"><button type="button" onclick="location.href='${pageContext.request.contextPath}/front?command=log-in-page'" class="btn btn-outline-black btn-block"><fmt:message key="button.header.login" bundle="${bd}"/></button></li>
            </ul>
        </c:if>
    </div>
    <img src="static/image/home-page.jpeg" alt="" id="phone">
</div>





<%--<div class="container">--%>
        <%--<div class="col-lg-12">--%>
        <%--</div>--%>


    <%--<div class="row">--%>
        <%--<c:forEach var="tattoo" items="${tattoos}">--%>
            <%--<div class="card col-lg-3" style="margin:4%; border-radius: 8%">--%>
                <%--<img class="card-img-top" src="images/tattoo.jpeg" alt="Card image">--%>
                <%--<div class="card-body">--%>
                    <%--<ul class="rating clearfix">--%>
                        <%--<li class="current" style="width: ${tattoo.getRating()}%"><span class="star1" onclick="location.href='RatingController?name=1&tattoo=${tattoo.getId()}'"></span></li>--%>
                        <%--<li><span class="star2" onclick="location.href='RatingController?name=2&tattoo=${tattoo.getId()}'"></span></li>--%>
                        <%--<li><span class="star3" onclick="location.href='RatingController?name=3&tattoo=${tattoo.getId()}'"></span></li>--%>
                        <%--<li><span class="star4" onclick="location.href='RatingController?name=4&tattoo=${tattoo.getId()}'"></span></li>--%>
                        <%--<li><span class="star5" onclick="location.href='RatingController?name=5&tattoo=${tattoo.getId()}'"></span></li>--%>
                    <%--</ul>--%>
                    <%--<h4 class="card-title" style="text-align: center">${tattoo.getName()}</h4>--%>
                    <%--<p class="card-text" style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">${tattoo.getContent()}</p>--%>
                    <%--<button class="btn btn-outline-primary" style="width: 49%;">See tattoo</button>--%>
                    <%--<button class="btn btn-outline-success" style="width: 49%;">Buy tattoo</button>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</c:forEach>--%>
    <%--</div>--%>



             <%--card--%>
        <%--<div class="row">--%>
            <%--<div class="col-lg-4 offset-8" style="color: white;">--%>
                <%--<p style="text-align: center">My Card</p>--%>
            <%--</div>--%>
            <%--<div class="col-lg-3 offset-9" style="border: solid purple 1px; width: 50%; background-color: purple; border-radius: 10px; height: 100px">--%>
                <%--<p style="text-align: center">Kirill Cheldishkin</p>--%>
                <%--<p style="text-align: center">2937 3726 3726 9375</p>--%>
                <%--<p>DiscountType : 20%</p>--%>
                <%--<p></p>--%>
            <%--</div>--%>
        <%--</div>--%>

        <%--<div id="media-container" style="position: absolute; z-index: -101; background: none;">--%>
            <%--<div class="backstretch" style="left: 0px; top: 0px; overflow: hidden; margin: 0px; padding: 0px; height: 265px; width: 1287px; z-index: -999998; position: absolute;">--%>
                <%--<img style="position: absolute; margin: 0px; padding: 0px; border: none; width: 1287px; height: 964.331px; max-width: none; z-index: -999999; left: 0px; top: -349.665px; right: auto; bottom: auto;" src="images/home-page.jpeg" alt="">--%>
            <%--</div>--%>
        <%--</div>--%>