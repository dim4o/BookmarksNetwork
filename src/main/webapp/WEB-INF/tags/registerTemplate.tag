<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<fieldset class="form-horizontal">

	<div class="form-group">
		<label for="inputUsername" class="col-lg-2 control-label">Username</label>
		<div class="col-lg-4">
			<form:input path="username" type="text" class="form-control"
				id="inputUsername" placeholder="Username" />
			<div>
				<form:errors path="username" cssClass="error" />
			</div>
		</div>
	</div>

	<div class="form-group">
		<label for="inputPassword" class="col-lg-2 control-label">Password</label>
		<div class="col-lg-4">
			<form:input path="password" type="password" class="form-control"
				id="inputPassword" placeholder="Password" />
			<div>
				<form:errors path="password" cssClass="error" />
			</div>
		</div>
	</div>

	<div class="form-group">
		<label for="inputPasswordAgain" class="col-lg-2 control-label">Confirm
			password</label>
		<div class="col-lg-4">
			<form:input path="matchingPassword" type="password"
				class="form-control" id="inputPasswordAgain" placeholder="Password" />
			<div>
				<form:errors path="matchingPassword" cssClass="error" />
			</div>
		</div>
	</div>

	<div class="form-group">
		<label for="inputEmail" class="col-lg-2 control-label">Email</label>
		<div class="col-lg-4">
			<form:input path="email" type="text" class="form-control"
				id="inputEmail" placeholder="Email" />
			<div>
				<form:errors path="email" cssClass="error" />
			</div>
		</div>
	</div>

	<div class="form-group">
		<label for="inputFirstName" class="col-lg-2 control-label">First
			Name</label>
		<div class="col-lg-4">
			<form:input path="firstName" type="text" class="form-control"
				id="inputFirstName" placeholder="First Name" />
			<div>
				<form:errors path="firstName" cssClass="error" />
			</div>
		</div>
	</div>

	<div class="form-group">
		<label for="inputLastName" class="col-lg-2 control-label">Last
			Name</label>
		<div class="col-lg-4">
			<form:input path="lastName" type="text" class="form-control"
				id="inputfirstName" placeholder="Last Name" />
			<div>
				<form:errors path="lastName" cssClass="error" />
			</div>
		</div>
	</div>

	<div class="form-group">
		<label for="inputAddress" class="col-lg-2 control-label">Address</label>
		<div class="col-lg-4">
			<form:input path="address" type="text" class="form-control"
				id="inputAddress" placeholder="Address" />
			<div>
				<form:errors path="address" cssClass="error" />
			</div>
		</div>
	</div>
	
	<jsp:doBody />

</fieldset>