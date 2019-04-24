<button class="btn btn-outline-black" onclick="location.href='${pageContext.request.contextPath}/front?command=back&page=WEB-INF/view/user-page.jsp'" style="margin-top: 1%; margin-bottom: 3%"><h3><fmt:message key="button.back" bundle="${bd}"/></h3></button>
<div class="container">
    <div class="row">
        <div class="col-lg-6 col-md-6 col-sm-6 mx-auto" style="margin-bottom: 15%; margin-top: 10%">
            <form action="front?command=propose-image" method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <label style="color: white"><fmt:message key="text.image.name" bundle="${bd}"/></label>
                    <input class="form-control" type="file" name="data">
                </div>
                <button class="btn btn-primary" type="submit"><fmt:message key="button.propose.image" bundle="${bd}"/></button>
            </form>
        </div>
    </div>
</div>