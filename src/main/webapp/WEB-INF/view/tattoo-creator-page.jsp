<script type="text/javascript" src="../../static/script/js/jquery.validate.js"></script>
<link rel="stylesheet" href="../../static/script/css/mycss.css">
<c:if test="${applicationScope.bd.getLocale() eq 'en_EN'}">
    <script type="text/javascript" src="../../static/script/js/EN_createTattooForm.js"></script>
</c:if>
<c:if test="${applicationScope.bd.getLocale() eq 'ru_RU'}">
    <script type="text/javascript" src="../../static/script/js/RU_createTattooForm.js"></script>
</c:if>
<button class="btn btn-outline-black" onclick="location.href='${pageContext.request.contextPath}/front?command=back&page=WEB-INF/view/admin-page.jsp'" style="margin-top: 1%; margin-bottom: 3%"><h3><fmt:message key="button.back" bundle="${bd}"/></h3></button>
<div class="container">
    <div class="row">
        <div class="col-lg-6 col-md-6 col-sm-12 offset-lg-3 offset-md-3" style="color: white; margin-top: 5%; margin-bottom: 10%">
            <h1 style="color: white; text-align: center"><fmt:message key="text.tattooCreate.title" bundle="${bd}"/></h1>
            <form id="createTattoo" action="front?command=create-tattoo" method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <label for="name"><fmt:message key="text.tattoo.name" bundle="${bd}"/></label>
                    <input type="text"
                           class="form-control"
                           placeholder="<fmt:message key="text.tattoo.name" bundle="${bd}"/>"
                           name="name"
                           id="name"
                    >
                </div>
                <div class="form-group">
                    <label for="titleImage"><fmt:message key="text.tattooCreate.chooseTitleImage.label" bundle="${bd}"/></label>
                    <input type="file"
                           id="titleImage"
                           class="form-control"
                           placeholder="Title image"
                           name="titleImage">
                </div>
                <div class="form-group">
                    <label for="ordinaryImage"><fmt:message key="text.tattooCreate.chooseImages.label" bundle="${bd}"/></label>
                    <input name="ordinalImage"
                           id="ordinaryImage"
                           type="file"
                           placeholder="<fmt:message key="text.tattooCreate.chooseOrdinalImages" bundle="${bd}"/>"
                           multiple="multiple"/>
                </div>
                <div class="form-group">
                    <label for="sketchImage"><fmt:message key="text.tattooCreate.chooseSketch.label" bundle="${bd}"/></label>
                    <input type="file"
                           id="sketchImage"
                           class="form-control"
                           placeholder="<fmt:message key="text.tattooCreate.chooseSketch" bundle="${bd}"/>"
                           name="sketchImage">
                </div>
                <button type="submit" class="btn btn-primary"><fmt:message key="button.tattoo.create" bundle="${bd}"/></button>
            </form>
        </div>
    </div>
</div>