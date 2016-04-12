<%@ taglib prefix="ct" uri="http://jwd.bg/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<ct:pageTemplate title="Add Bookmark">
<jsp:body>
	<form:form action="${contextPath}${bookmarkController}${addAction}" method="POST"
		modelAttribute="addBookmarkForm" class="form-horizontal">
		<fieldset>
			<div class="form-group">
				<label for="idInput" class="col-lg-2 control-label">Title:</label>
				<div class="col-lg-4">
					<form:input type="text" path="title" placeholder="Title" class="form-control" />
					<div><form:errors path="title" cssClass="error" /></div>
				</div>
			</div>
				
			<div class="form-group">
				<label for="idInput" class="col-lg-2 control-label">Url</label>
				<div class="col-lg-4">
					<form:input type="text" path="url" placeholder="Url" class="form-control" />
					<div><form:errors path="url" cssClass="error" /></div>
				</div>
			</div>
			
			<div class="form-group">
			<label for="idInput" class="col-lg-2 control-label">Description</label>
				<div class="col-lg-4">
					<form:input type="text" path="description" placeholder="Description" class="form-control" />
					<div><form:errors path="description" cssClass="error" /></div>
				</div>
			</div>
			
			<div class="form-group">
				<label for="keywordsInput" class="col-lg-2 control-label">Keywords <br>(comma separated)</label>
				<div class="col-lg-4">
					<form:input type="text" path="keywords" placeholder="Keywords" class="form-control" id="keywordsInput"/>
				</div>
			</div>
			
			<div class="form-group">
				<label for="tagsInput" class="col-lg-2 control-label">Keywords <br>(comma separated)</label>
				<div class="col-lg-4">
					<form:input type="text" path="tags" placeholder="Tags" class="form-control" id="tagsInput"/>
				</div>
			</div>

			<div class="form-group">
				<label for="visibility" class="col-lg-2 control-label">Select visibility</label>
				<div class="col-lg-4">
					<form:select path="visibility" class="form-control" id="visibility">
						<option>Public</option>
						<option>Private</option>
					</form:select>
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-lg-4 col-lg-offset-2">
					<button type="submit" class="btn btn-primary" >Add Bookmark</button>
				</div>
			</div>
		</fieldset>
	</form:form>
</jsp:body>
</ct:pageTemplate>
