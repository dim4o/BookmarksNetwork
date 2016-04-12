<%@ taglib prefix="ct" uri="http://jwd.bg/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<ct:pageTemplate title="Register">
	<jsp:body>
		<form:form action="${contextPath}/admin/users/add" method="post" modelAttribute="addUser">
		
		<ct:registerTemplate>
			<jsp:body>
			
			<div class="form-group">
		      <label for="select" class="col-lg-2 control-label">Role</label>
		      <div class="col-lg-4">
		        <form:select path="roles" multiple="true" class="form-control">
		        	<c:forEach var="role" items="${roles}">
		        		<c:if test="${role.roleName == 'user'}">
		        			<option value="${role.roleName}" disabled="disabled" selected="selected">${role.roleName}</option>
		        		</c:if>
		        		<c:if test="${role.roleName != 'user'}">
		        			<option value="${role.roleName}">${role.roleName}</option>
		        		</c:if>
		        		
		        	</c:forEach>
		        	<!-- <option value="1">Administrador</option>
       				<option value="2">Usuario avanzado</option> -->
       				<%-- <form:options items="${roles}" itemLabel="name" itemValue="id" /> --%>
		          <!-- <option disabled="disabled">User</option>
		          <option>Admin</option> -->
		        </form:select>
		      </div>
		    </div>
		    
			
        	<div class="form-group">
		      <label class="col-lg-2 control-label">Status</label>
		      <div class="col-lg-10">
		        <div class="radio">
		          <label>
		            <input type="radio" name="status" id="optionsRadios1" value="true" />
		            Enabled
		          </label>
		        </div>
		        <div class="radio">
		          <label>
		            <input type="radio" name="status" id="optionsRadios2" value="false" checked/>
		            Disabled
		          </label>
		        </div>
		      </div>
		    </div>
		    
		    <div class="form-group">
		      <div class="col-lg-10 col-lg-offset-2">
		        <button type="submit" class="btn btn-primary">Submit</button>
		      </div>
		    </div>
			
			</jsp:body>
		</ct:registerTemplate>
		</form:form>
		
	</jsp:body>
</ct:pageTemplate>
