<%@ taglib prefix="ct" uri="http://jwd.bg/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<ct:pageTemplate title="Home">
	<jsp:body>
	<div class="jumbotron">
  		<h1>Welcome to the Bookmarks Network!</h1>
  		<p>Join to our community</p>
  		<p>
			<a href="${contextPath}/register" class="btn btn-primary btn-lg">Join</a>
		</p>
	</div>
	</jsp:body>
</ct:pageTemplate>