<%@page import="com.lemanov.internetshop.dao.TestConnectionPool"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script type="text/javascript"
	src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
<script type="text/javascript"
	src="http://netdna.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<link
	href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.3.0/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">
<link
	href="http://pingendo.github.io/pingendo-bootstrap/themes/default/bootstrap.css"
	rel="stylesheet" type="text/css">
<title>Login page</title>
</head>
<body>
	<div class="navbar navbar-default navbar-static-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="index.jsp"> <span>OnlineShop</span><br>
				</a>
			</div>
			<div class="collapse navbar-collapse">
				<ul class="nav navbar-nav navbar-right">
					<c:choose>
						<c:when test="${sessionScope.userName != null}">
							<li><a><b>${sessionScope.userName}'s cabinet</b></a></li>
							<li><a href="catalogPrepare">Catalog</a></li>
						</c:when>
						<c:otherwise>
							<li><a href="register.jsp">Register</a></li>
						</c:otherwise>
					</c:choose>
				</ul>
			</div>
		</div>
	</div>
	<div class="section">
		<div class="container">
			<div class="row">
				<div class="col-md-4"></div>
				<div class="col-md-4">
					<!-- TODO: add red alert message when user input wrong fields! -->
					<form role="form" action="login" method="POST">
						<div class="form-group">
							<label class="control-label" for="loginInput">Login</label> <input
								class="form-control" id="loginInput" name="login"
								placeholder="Enter login" type="text" required>
						</div>
						<div class="form-group">
							<label class="control-label" for="passwordInput">Password</label>
							<input class="form-control" id="passwordInput" name="passwd"
								placeholder="Password" type="password" required>
						</div>
						<button type="submit" class="btn btn-default">Submit</button>
					</form>
				</div>
				<div class="col-md-4"></div>
			</div>
		</div>
	</div>
	
</body>
</html>