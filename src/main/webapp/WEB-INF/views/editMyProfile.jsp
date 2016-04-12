<%@ taglib prefix="ct" uri="http://jwd.bg/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<ct:pageTemplate title="Edit Profile">
<jsp:body>
	<form:form action="${contextPath}/user/editProfile" method="POST" 
		modelAttribute="editProfile" class="form-horizontal" > <!-- modelAttribute="RegisterFormDto" -->
	<fieldset>
		<div class="form-group">
			<label for="usernameInput" class="col-lg-2 control-label" >Username:</label> 
			<div class="col-lg-4">
				<form:input type="text" path="username" value="${user.username}" class="form-control" id="usernameInput"/>
				<div><form:errors path="username" cssClass="error"/></div>
			</div>
		</div>
		
		<div class="form-group">
			<label for="passInput" class="col-lg-2 control-label" >Password:</label> 
			<div class="col-lg-4">
				<form:input type="password" path="password" placeholder="Password" class="form-control" id="passInput" />
				<div><form:errors path="password" cssClass="error"/></div>
			</div>
		</div>
		
		<div class="form-group">
			<label for="idInput" class="col-lg-2 control-label" >Confirm password:</label> 
			<div class="col-lg-4">
				<form:input type="password" path="matchingPassword" placeholder="Confirmation" class="form-control" />
				<div><form:errors path="matchingPassword" cssClass="error"/></div>
			</div>
		</div>

		<div class="form-group">
			<label for="emailInput" class="col-lg-2 control-label" >Email:</label> 
			<div class="col-lg-4">
				<form:input type="text" path="email" value="${user.email}" class="form-control" id="emailInput"/>
				<div><form:errors path="email" cssClass="error"/></div>
			</div>
		</div>
		
		<div class="form-group">
			<label for="idInput" class="col-lg-2 control-label" >First Name:</label> 
			<div class="col-lg-4">
				<form:input type="text" path="firstName" placeholder="First Name" value="${user.firstName}" class="form-control" />
			</div>
		</div>
		
		<div class="form-group">
			<label for="idInput" class="col-lg-2 control-label" >Last Name:</label> 
			<div class="col-lg-4">
				<form:input type="text" path="lastName" placeholder="Last Name" value="${user.lastName}" class="form-control" />
			</div>
		</div>
		
		<div class="form-group">
			<label for="idInput" class="col-lg-2 control-label" >Address:</label> 
			<div class="col-lg-4">
				<form:input type="text" path="address" placeholder="Address" value="${user.address}" class="form-control" />
			</div>
		</div>
		
		<div><form:errors cssClass="error"/><span class="error">${error}</span></div>
		
		<!-- <div class="form-group">
			<button type="submit" value="editProfile">Edit Profile</button>
		</div> -->
		<div class="form-group">
	      <div class="col-lg-4 col-lg-offset-2">
	        <button type="submit" class="btn btn-primary">Edit Profile</button>
	      </div>
	    </div>
	</fieldset>
	</form:form>
</jsp:body>
</ct:pageTemplate>