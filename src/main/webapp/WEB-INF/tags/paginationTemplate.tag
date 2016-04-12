<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="totalPageCount" %>
<%@ attribute name="pageNum" %>
<%@ attribute name="currentPage" %>
<%@ attribute name="baseUrl" %>

<ul class="pagination">
	<li class="disabled"><a href="#">&laquo;</a></li>
		<c:forEach var="pageNum" begin="1" end="${totalPageCount}">
	
			<c:if test="${pageNum == currentPage}">
				<li class="active"><a
					href="${baseUrl}/page/${pageNum}/from/${totalPageCount}">${pageNum}</a>
				</li>
			</c:if>
	
			<c:if test="${pageNum != currentPage}">
				<li><a
					href="${baseUrl}/page/${pageNum}/from/${totalPageCount}">${pageNum}</a>
				</li>
			</c:if>
	
		</c:forEach>
	<li>
	<a href="#">&raquo;</a></li>
</ul>