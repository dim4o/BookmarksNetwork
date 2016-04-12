<%@ taglib prefix="ct" uri="http://jwd.bg/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<ct:pageTemplate title="Login">
	<jsp:body>
		<form:form action="${contextPath}/login" commandName="loginForm" class="form-horizontal">
		  <fieldset>

		    <div class="form-group">
		      <label for="inputUsername" class="col-lg-2 control-label">Username</label>
		      <div class="col-lg-4">
		        <form:input path="username" type="text" class="form-control" id="inputUsername" placeholder="Username" />
		        <div><form:errors path="username" cssClass="error"/></div>
		      </div>
		    </div>
		    
		    <div class="form-group">
		      <label for="inputPassword" class="col-lg-2 control-label">Password</label>
		      <div class="col-lg-4">
		        <form:input path="password" type="password" class="form-control" id="inputPassword" placeholder="Password" />
		        <div><form:errors path="password" cssClass="error"/></div>
		      </div>
		    </div>
		    
		    <div><form:errors cssClass="error"/></div>

		    <div class="form-group">
		      <div class="col-lg-10 col-lg-offset-2">
		        <button type="submit" class="btn btn-primary">Submit</button>
		      </div>
		    </div>
		    
		  </fieldset>
		</form:form>
		
		<ct:successMessage />
		
	</jsp:body>
</ct:pageTemplate>