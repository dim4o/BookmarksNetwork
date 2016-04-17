<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ct" uri="http://jwd.bg/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<%@ page pageEncoding="UTF-8" %>

<ct:pageTemplate title="My Bookmarks">
	<jsp:body>
	<div class="row">
		<form:form action="${contextPath}/user/search/" method="GET" modelAttribute="searchForm" class="navbar-form navbar-left" role="search">
		
	        <div class="form-group">
	          <input name="searchTerm" type="text" class="form-control" placeholder="Search">
	        </div>
	        
	        <button type="submit" class="btn btn-default">Search Bookmarks</button>
      
	        <div class="form-group">
	        
	      		<div class="checkbox">
		          <label>
		            <input name="globalSearch" type="checkbox"> Global
		          </label>
       			</div>
       			
       			<div class="checkbox">
		          <label>
		            <input name="titleSearch" type="checkbox" checked> Title
		          </label>
       			</div>
		        
		        <div class="checkbox">
		          <label>
		            <input name="keywordSearch" type="checkbox"> Keyword
		          </label>
       			</div>
		        
		        <div class="checkbox">
		          <label>
		            <input name="tagSearch" type="checkbox"> Tag
		          </label>
       			</div>
       			
       			<div class="checkbox">
		          <label>
		            <input name="descriptionSearch" type="checkbox"> Description
		          </label>
       			</div>
		    </div>
      	</form:form>

		<a href="${contextPath}/user/bookmarks/add" type="button" class="btn btn-primary">Add Bookmark</a>
	</div>

<%-- <c:if test="${not empty searchResult}"> --%>

		<div class="row">
			<%-- <div class="col-lg-2 col-md-2">
				<ul class="list-group">
					<c:forEach var="tag" items="${userTags}">
						<li class="list-group-item">
							<span class="badge">${tag.bookmarksCount}</span>
						  	<a href="${contextPath}/user/bookmarks/tag/${tag.tagName}">${tag.tagName}</a>
						</li>
					</c:forEach>
				</ul>
			</div> --%>
			
			<div class="col-lg-2 col-md-2">
				<div class="list-group">
			  		<a href="#" class="list-group-item active">Tags</a>
			 		<c:forEach var="tag" items="${userTags}">
			 			<a href="#" class="list-group-item">Dapibus ac facilisis in</a>
			 		</c:forEach>
				</div>
			</div>
			
			
			<div class="col-lg-10 col-md-10">
			<table class="table table-striped table-hover">
			<thead>
				<tr>
					<td>Title</td>
					<td class="partialText">Url</td>
					
					<td>Description</td>
					<td>Visibility</td>
					<td>Date Created</td>
					<td>Actions</td>
				</tr>
			</thead>
		
			<tbody>
				<c:forEach var="bookmark" items="${bookmarks}">
					<tr>
						<td>${bookmark.title}</td>
						<td class="partialText"><a href="${bookmark.url.link}">${bookmark.url.link}</a></td>
						<td>${bookmark.description}</td>
						<td>${bookmark.visibility}</td>
						<td>${bookmark.creationDate}</td>
						<td><a href="${contextPath}/user/bookmarks/edit/${bookmark.bookmarkId}" class="btn btn-primary btn-sm">Edit</a>
							<a href="${contextPath}/user/bookmarks/delete/${bookmark.bookmarkId}" class="btn btn-primary btn-sm">Delete</a>
						</td>
					</tr>
				</c:forEach>		
			</tbody>
			</table>
			</div>
			</div>
			<ct:pagination 
					totalPageCount="${totalPageCount}"
					pageNum="${pageNum}"
					currentPage="${currentPage}"
					baseUrl="${contextPath}/user/bookmarks">
			</ct:pagination>
		
	</jsp:body>
</ct:pageTemplate>
















