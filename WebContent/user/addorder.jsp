<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="org.apache.jasper.tagplugins.jstl.ForEach"%>
<%@page import="com.lemanov.internetshop.domain.Service"%>
<%@page import="com.lemanov.internetshop.domain.Goods" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script type="text/javascript" src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
<script type="text/javascript" src="http://netdna.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<link href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<link href="http://pingendo.github.io/pingendo-bootstrap/themes/default/bootstrap.css" rel="stylesheet" type="text/css">
<title>Add order</title>
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
				<a class="navbar-brand" href="./index.jsp"> <span>OnlineShop</span><br>
				</a>
			</div>
			<div class="collapse navbar-collapse">
				<ul class="nav navbar-nav navbar-right">
					<li><a><b>${sessionScope.userName}'s cabinet</b></a></li>
					<li><a href="${pageContext.request.contextPath}/catalogPrepare">Catalog</a></li>
					<li class="active"><a>Orders</a></li>
					<li><a href="${pageContext.request.contextPath}/basketPrepare">Basket</a></li>
					<li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
				</ul>
			</div>
		</div>
	</div>
	
	<div class="section">
		<div class="container">
			<div class="row">
				<div class="col-md-4"></div>
				<div class="col-md-4">
					<form role="form" action="${pageContext.request.contextPath}/confirmOrder" method="post">
						<div class="form-group">
							<label class="control-label" for="address">Delivery address:</label>
							<input class="form-control" id="address" name="address"
								placeholder="Delivery address" type="text" required>
						</div>
						<div class="form-group">
							<label class="control-label" for="shippingType">Shipping type:</label> 
							<select class="form-control" id="shippingType"
								name="shippingType">
								<option value="self">Self</option>
								<option value="courier">Courier</option>
								<option value="land">Land</option>
								<option value="ship">Ship</option>
								<option value="air">Air</option>
							</select> 
						</div>
						<button type="submit" class="btn btn-primary form-control">Confirm order</button>
					</form>
					<a href="${pageContext.request.contextPath}/basketPrepare"><button type="submit"
							class="btn btn-default btn-block">Cancel</button></a>
				</div>
				<div class="col-md-4"></div>
			</div>
		</div>
	</div>
	</div>
</body>
</html>