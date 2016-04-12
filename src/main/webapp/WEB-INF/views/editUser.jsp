<%@ taglib prefix="ct" uri="http://jwd.bg/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />


<ct:pageTemplate title="Admin Panel - Edit User">
	<jsp:body>
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
		
		<dl class="dl-horizontal">
		  <dt>Status:</dt>
		  <dd>
		  	<c:if test="${user.enabled}">Enabled</c:if>
		  	<c:if test="${not user.enabled}">Disabled</c:if>
		  </dd>
		</dl>
	</div>
	
	<div class="col-lg-2">
		<c:if test="${user.enabled}">
			<div class="col-lg-4">
				<a href="${contextPath}/admin/users/edit/disable/${user.userId}" class="btn btn-primary">Disable</a>
			</div>
		</c:if>
		<c:if test="${not user.enabled}">
			<div class="col-lg-4">
				<a href="${contextPath}/admin/users/edit/enable/${user.userId}" class="btn btn-primary">Enable</a>
			</div>
		</c:if>
	</div>
	
	<div class="col-lg-4">
	<c:if test="${not empty user.roles}">
		<table class="table table-striped table-hover">
			<thead>
				<tr>
					<td>Role Name</td>
					<td>Action</td>
				</tr>
			</thead>

			<tbody>
				<c:forEach var="role" items="${user.roles}">
					<tr>
						<td>${role.roleName}</td>
						<td>
							<c:if test="${role.roleName != 'user'}">
								<a href="${contextPath}/admin/users/edit/deleteRole/${user.userId}/${role.roleName}" 
									class="btn btn-primary btn-sm">Delete</a>
							</c:if> 
							<c:if test="${role.roleName == 'user'}">
								no action
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
	<c:if test="${not empty roles}">
		<form action="<c:url value="/admin/users/edit/addRole/${user.userId}" />" method="POST" class="form-horizontal" >
			<fieldset>
				<select name="role" class="form-control">
					<c:forEach var="role" items="${roles}">
						<option value="${role}">${role}</option>
					</c:forEach>
				</select>
			</fieldset>

			<button type="submit" value="AddRole" class="btn btn-primary btn-sm">Add Role</button>

		</form>
	</c:if>
	</div>
	<div class="clearfix"></div>
	
	<div class="col-lg-4">
		<a href="${contextPath}/admin/users" class="btn btn-primary btn-lg">&lt; Back</a>
	</div>
	
	</jsp:body>
</ct:pageTemplate>