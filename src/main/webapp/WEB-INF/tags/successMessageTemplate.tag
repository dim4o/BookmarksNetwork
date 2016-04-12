<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="hidden" value="${(empty successMessage) ? 'hidden' : ''}" />

<div ${hidden}>
	<div class="alert alert-dismissible alert-success">
	  <button type="button" class="close" data-dismiss="alert">&times;</button>
	  <strong>Success!</strong> ${successMessage}
	</div>
</div>