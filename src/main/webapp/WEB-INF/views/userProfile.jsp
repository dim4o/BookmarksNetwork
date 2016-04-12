<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ct" uri="http://jwd.bg/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<ct:pageTemplate title="User profile - ${user.username}">
	<jsp:body>
	<ct:successMessage />
	<div class="col-lg-4">
		<dl class="dl-horizontal">
		  <dt>Username:</dt>
		  <dd>${user.username}</dd>
		</dl>
	
		<dl class="dl-horizontal">
		  <dt>Email:</dt>
		  <dd>${user.email}</dd>
		</dl>
		
		<dl class="dl-horizontal">
		  <dt>First Name:</dt>
		  <dd>${user.firstName}</dd>
		</dl>
		
		<dl class="dl-horizontal">
		  <dt>Last Name:</dt>
		  <dd>${user.lastName}</dd>
		</dl>
		
		<dl class="dl-horizontal">
		  <dt>Address:</dt>
		  <dd>${user.address}</dd>
		</dl>
		
		<dl class="dl-horizontal">
		  <dt>Creation Date:</dt>
		  <dd>${user.creationDate}</dd>
		</dl>
	</div>
	<c:if test="${user.username != currentUserUsername}">
		<c:if test="${not user.isFollow}">
			<a href="${contextPath}/user/follow/${user.userId}"
				class="btn btn-primary btn-lg"> Follow </a>
		</c:if>
		<c:if test="${user.isFollow}">
			<a href="#${user.userId}"
				class="btn btn-primary btn-lg disabled"> Following </a>
								 	
			<a href="${contextPath}/user/unfollow/${user.userId}"
				class="btn btn-primary btn-lg"> Unfollow </a>					
		</c:if>
	</c:if>
	
	<!-- <a href="#" class="btn btn-primary btn-lg">Follow</a>
	<a href="#" class="btn btn-primary btn-lg">Unfollow</a>
	<a href="#" class="btn btn-primary btn-lg">Following</a> -->

	<table class="table table-striped table-hover">
		<thead>
			<tr>
				<td>Title</td>
				<td>Url</td>
				<td>Author</td>
				<td>Date Created</td>
				<td>Description</td>
				<td>Raiting</td>
				<td>Vote</td>
				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<td>Actions</td>
				</sec:authorize>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="bookmark" items="${bookmarks}">
				<tr>
					<td>${bookmark.title}</td>
					<td><a href="${bookmark.url.link}" target="_blank">${bookmark.url.link}</a></td>
					<td>${bookmark.author.username}</td>
					<td>${bookmark.creationDate}</td>
					<td>${bookmark.description}</td>
					<td>${bookmark.raiting}</td>
					<sec:authorize access="hasRole('ROLE_USER')">
						<td>
							<c:if test="${bookmark.author.username != currentUserUsername}">
							
								<a href="${contextPath}/user/voteUp/${bookmark.bookmarkId}"
										class="btn btn-primary btn-sm">+ vote</a>
										
								<a href="${contextPath}/user/voteDown/${bookmark.bookmarkId}"
										class="btn btn-primary btn-sm">- vote</a>
									<c:if test="${not bookmark.saved}">
										<a href="${contextPath}/user/save/${bookmark.bookmarkId}"
											class="btn btn-primary btn-sm">Save</a>
									</c:if>	
									<c:if test="${bookmark.saved}"><a href="#" class="btn btn-primary btn-sm disabled">Saved</a>
									</c:if>
							</c:if>
						</td>
					</sec:authorize>
					<sec:authorize access="hasRole('ROLE_ADMIN')">
						<td>
							<a href="${contextPath}/user/bookmarks/edit/${bookmark.bookmarkId}"
											class="btn btn-primary btn-sm">Edit</a>
							<a href="${contextPath}/user/bookmarks/delete/${bookmark.bookmarkId}"
											class="btn btn-primary btn-sm">Delete</a>
						</td>
					</sec:authorize>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<ct:pagination 
			totalPageCount="${totalPageCount}"
			pageNum="${pageNum}"
			currentPage="${currentPage}"
			baseUrl="${contextPath}/user/profile/${user.username}">
	</ct:pagination>
</jsp:body>
</ct:pageTemplate>