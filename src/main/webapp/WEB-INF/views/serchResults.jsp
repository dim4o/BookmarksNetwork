<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ct" uri="http://jwd.bg/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<%-- <%@ page pageEncoding="UTF-8" %> --%>

<ct:pageTemplate title="Search results for '${term}'">
<jsp:body>
		<%-- <c:if test="${not empty searchResult}"> --%>
<table class="table table-striped table-hover">
	<thead>
		<tr>
			<td>Title</td>
			<td>Url</td>
			<td>Author</td>
			<td>Date Created</td>
			<td>Description</td>
			<sec:authorize access="hasRole('ROLE_USER')">
				<td>Visibility</td>
				<td>Raiting</td>
			</sec:authorize>
		</tr>
	</thead>

	<tbody>
		<c:forEach var="bookmark" items="${bookmarks}">
			<tr>
				<td>${bookmark.title}</td>
				<td><a href="${bookmark.url.link}" target="_blank">${bookmark.url.link}</a></td>
				<sec:authorize access="hasRole('ROLE_ANONYMOUS')">
					<td>${bookmark.author.username}</td>
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_USER')">
					<td><a href="${contextPath}/user/profile/${bookmark.author.username}">${bookmark.author.username}</a></td>
				</sec:authorize>
				<td>${bookmark.creationDate}</td>
				<td>${bookmark.description}</td>
				<td>${bookmark.visibility}</td>
				<td>${bookmark.raiting}</td>
				<sec:authorize access="hasRole('ROLE_USER')">
					<c:if test="${bookmark.author.username != currentUserUsername}">
						<td><a href="${contextPath}/voteUp/${bookmark.bookmarkId}"
									class="btn btn-primary btn-sm">+ vote</a></td>
						<td><a href="${contextPath}/voteDown/${bookmark.bookmarkId}"
									class="btn btn-primary btn-sm">- vote</a></td>
					</c:if>
				</sec:authorize>
			</tr>
		</c:forEach>
	</tbody>
</table>
</jsp:body>
</ct:pageTemplate>














