<%@ taglib prefix="ct" uri="http://jwd.bg/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<ct:pageTemplate title="Edit Bookmark">
<jsp:body>
	<form:form action="${contextPath}${bookmarkController}/edit/${bookmark.bookmarkId}" method="POST" 
		modelAttribute="addBookmarkForm" class="form-horizontal" > <!-- modelAttribute="addBookmarkForm" -->
		<fieldset>
			<div class="form-group">
				<label for="idInput" class="col-lg-2 control-label">Title:</label> 
				<div class="col-lg-4">
					<input type="text" name="title" value="${bookmark.title}" class="form-control" />
					<%-- <div><form:errors path="title" cssClass="error"/></div> --%>
				</div>
			</div>
			
			<div class="form-group">
				<label for="idInput" class="col-lg-2 control-label">Url</label> 
				<div class="col-lg-4">
					<input type="text" name="url" value="${bookmark.url.link}" class="form-control" />
					<%-- <div><form:errors path="url" cssClass="error"/></div> --%>
				</div>
			</div>
			
			<div class="form-group">
				<label for="idInput" class="col-lg-2 control-label">Description</label> 
				<div class="col-lg-4">
					<input type="text" name="description" value="${bookmark.description}" class="form-control" />
					<%-- <div><form:errors path="description" cssClass="error"/></div> --%>
				</div>
			</div>
			
			<div class="form-group">
				<label for="idInput" class="col-lg-2 control-label">Keywords</label> 
				<div class="col-lg-4">
					<input type="text" name="keywords" value="${bookmark.keywordsString}" class="form-control" />
					<div><form:errors path="description" cssClass="error"/></div>
				</div>
			</div>
			
			<div class="form-group">
				<label for="idInput" class="col-lg-2 control-label">Tags</label> 
				<div class="col-lg-4">
					<input type="text" name="tags" value="${bookmark.tagsString}" class="form-control" />
					<%-- <div><form:errors path="description" cssClass="error"/></div> --%>
				</div>
			</div>
			
			<div class="form-group">
				<label for="visibility" class="col-lg-2 control-label">Visibility</label>
				<div class="col-lg-4">
					<select name="visibility" class="form-control" id="visibility">
						<c:if test="${bookmark.visibility == 'Public'}">
							<option selected="selected">Public</option>
							<option>Private</option>
						</c:if>
						<c:if test="${bookmark.visibility == 'Private'}">
							<option>Public</option>
							<option selected="selected">Private</option>
						</c:if>
					</select>
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-lg-4 col-lg-offset-2">
					<button type="submit" class="btn btn-primary">Edit Bookmark</button>
				</div>
			</div>
		</fieldset>
	</form:form>
</jsp:body>
</ct:pageTemplate>