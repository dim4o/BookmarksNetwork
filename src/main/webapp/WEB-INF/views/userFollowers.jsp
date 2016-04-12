<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ct" uri="http://jwd.bg/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<ct:pageTemplate title="Your Followers">
<jsp:body>
	<table class="table table-striped table-hover">
	<thead>
		<tr>
			<td>Username</td>
			<td>Email</td>
			<td>CreationDate</td>
			<td>First Name</td>
			<td>Last Name</td>
			<td>Address</td>
		</tr>
	</thead>

	<c:if test="${not empty followers}">
		<tbody>
			<c:forEach var="user" items="${followers}">
				<tr>
					<td><a href="${contextPath}/user/profile/${user.username}">${user.username}</a></td>
					<td>${user.email}</td>
					<td>${user.creationDate}</td>
					<td>${user.firstName}</td>
					<td>${user.lastName}</td>
					<td>${user.address}</td>

					<td>
						<c:if test="${not user.isFollow}">
							<a href="${contextPath}/user/follow/${user.userId}"
								class="btn btn-primary btn-sm"> Follow </a>
						</c:if>
						<c:if test="${user.isFollow}">
							<a href="#" class="btn btn-primary btn-sm disabled"> Following </a>
							 	
							<a href="${contextPath}/user/unfollow/${user.userId}"
								class="btn btn-primary btn-sm"> Unfollow </a>
								
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</c:if>
</table>
</jsp:body>
</ct:pageTemplate>


