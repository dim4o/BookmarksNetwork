<%@ taglib prefix="ct" uri="http://jwd.bg/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<ct:pageTemplate title="Import Bookmarks from File">
<jsp:body>
	<div class="container">
		<div class="well bs-component col-lg-6">
			<form method="POST" action="${contextPath}${bookmarkController}/import" enctype="multipart/form-data"
						class="form-horizontal">
				<fieldset>
		
					<div class="form-group">
						<label class="col-lg-2 control-label">File to upload</label>
						<div class="col-lg-10">
							<input type="file" name="file" class="form-control">
						</div>
					</div>
		
					<div class="form-group">
						<label for="select" class="col-lg-2 control-label">Selects
							visibility</label>
						<div class="col-lg-10">
							<select name="visibility" class="form-control" id="select">
								<option>Public</option>
								<option>Private</option>
							</select>
						</div>
					</div>
		
					<div class="form-group">
						<div class="col-lg-10 col-lg-offset-2">
							<button type="submit" value="Upload" class="btn btn-primary">Import
								Bookmarks</button>
						</div>
					</div>
		
				</fieldset>
			</form>
		</div>
	</div>
</jsp:body>
</ct:pageTemplate>