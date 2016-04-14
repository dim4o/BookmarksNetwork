<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ct" uri="http://jwd.bg/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<ct:pageTemplate title="Admin Panel - All Users">
	<jsp:body>
	
	<form:form action="${contextPath}/admin/users/search" modelAttribute="userSearch" method="POST" 
					role="search" class="navbar-form navbar-left" >
		<div class="form-group">
			<input name="searchTerm" type="text" class="form-control" placeholder="Search">
		</div>
		<button type="submit" class="btn btn-default">User Search</button>
		
		<div class="form-group">
	        
   		<div class="checkbox">
          <label>
            <input name="usernameSearch" type="checkbox" checked="checked"> Username
          </label>
		</div>
     			
		<div class="checkbox">
          <label>
            <input name="emailSearch" type="checkbox"> Email
          </label>
		</div>
        
        <div class="checkbox">
          <label>
            <input name="firstNameSearch" type="checkbox"> First Name
          </label>
		</div>
        
        <div class="checkbox">
          <label>
            <input name="lastNameSearch" type="checkbox"> Last Name
          </label>
		</div>
     			
    	</div>
	</form:form>
	
	<a href="${contextPath}/admin/users/add" type="button" class="btn btn-primary">Add User</a>
	
	<table class="table table-striped table-hover">
		<thead>
			<tr>
				<td>Username</td>
				<td>Email</td>
				<td>CreationDate</td>
				<td>First Name</td>
				<td>Last Name</td>
				<td>Address</td>
				<td>Status</td>
				<td>Roles</td>
			</tr>
		</thead>

		<c:if test="${not empty users}">
			<tbody>
				<c:forEach var="user" items="${users}">
					<tr>
						<td><a href="${contextPath}/user/profile/${user.username}">${user.username}</a></td>
						<td>${user.email}</td>
						<td>${user.creationDate}</td>
						<td>${user.firstName}</td>
						<td>${user.lastName}</td>
						<td>${user.address}</td>
						<td>${user.enabled ? 'enabled' : 'disabled'}</td>
						<td>${user.roles}</td>
						<td>
							<a href="${contextPath}/admin/users/edit/${user.userId}" class="btn btn-primary btn-sm">
								Edit
							</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</c:if>
		</table>	
		
	</jsp:body>
</ct:pageTemplate>



