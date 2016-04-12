<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ attribute name="title"%>
<%@ attribute name="currentUserUsername"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="utf-8">
<!-- <meta http-equiv="Content-Type" content="text/html; charset=windows-1251"> -->
<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"> -->
<!-- <link rel="stylesheet" href="/css/bootstrap.min.css"> -->
<link href="<c:url value="/resources/css/bootstrap.min.css" />"
	rel="stylesheet">
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<title>${title}</title>
<style>
.error {
	color: red;
	font-weight: bold;
}

body {
	padding-bottom: 100px;
	/* padding-top: 50px; */
}

/* Overflow for table */
.table {
	table-layout: fixed;
}

.table td:nth-child(2) {
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
}

</style>
</head>
<body>
	<!-- Navbar -->
	<nav class="navbar navbar-default">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">Bookmarks Network</a>
			</div>

			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">

					<sec:authorize access="hasRole('ROLE_ANONYMOUS')">
						<li class="${loginActive}"><a href="${contextPath}/login">Login</a></li>
						<li class="${registerActive}"><a href="${contextPath}/register">Register</a></li>
					</sec:authorize>

					<sec:authorize access="hasRole('ROLE_USER')">
						<li class="${bookmarksActive}"><a href="${contextPath}/user/bookmarks">My Bookmarks 
							<span class="sr-only">(current)</span></a>
						</li>
						<li class="${importActive}"><a href="${contextPath}/user/bookmarks/import">Import Bookmarks</a></li>
						<li class="dropdown ${profileActive}"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-expanded="false">Me | ${currentUserUsername}
							<span class="caret"></span></a>
							<ul class="dropdown-menu" role="menu">
								<li><a href="${contextPath}/user/profile/${currentUserUsername}">My Profile</a></li>
								<li><a href="${contextPath}/user/editProfile">Edit Profile</a></li>
							</ul>
						</li>
						<li class="${followersActive}"><a href="${contextPath}/user/followers">Followers</a></li>
						<li class="${followingsActive}"><a href="${contextPath}/user/followings">Followings</a></li>
					</sec:authorize>
					<sec:authorize access="hasRole('ROLE_ADMIN')">
						<li class="${adminActive}"><a href="${contextPath}/admin/users">All Users</a></li>
					</sec:authorize>
					<!-- <li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-expanded="false">Dropdown
							<span class="caret"></span>
					</a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#">Action</a></li>
							<li><a href="#">Another action</a></li>
							<li><a href="#">Something else here</a></li>
							<li class="divider"></li>
							<li><a href="#">Separated link</a></li>
							<li class="divider"></li>
							<li><a href="#">One more separated link</a></li>
						</ul>
					</li> -->
				</ul>

				<form action="${contextPath}/search/" method="GET"
					class="navbar-form navbar-left" role="search">
					<div class="form-group">
						<input name="searchTerm" type="text" class="form-control"
							placeholder="Search">
					</div>
					<button type="submit" class="btn btn-default">Global
						Search</button>
				</form>

				<ul class="nav navbar-nav navbar-right">
					<sec:authorize access="hasRole('ROLE_USER')">
						<li><a href="${contextPath}/logout">Logout</a></li>
					</sec:authorize>
				</ul>

			</div>
		</div>
	</nav>
	<!-- Navbar -->
	<div class="container">
		<h1>${title}</h1>

		<jsp:doBody />
	</div>

	<footer class="navbar navbar-inverse navbar-fixed-bottom"
		role="navigation">
		<div class="container">
			<p class="text-muted">System version 0.0.1</p>
		</div>
	</footer>

</body>
</html>
