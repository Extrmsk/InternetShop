<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.lemanov.internetshop.domain.GoodsManager"%>
<%@page import="org.apache.jasper.tagplugins.jstl.ForEach"%>
<%@page import="com.lemanov.internetshop.domain.ShopManagerHandler"%>
<%@page import="com.lemanov.internetshop.domain.ShopManager"%>
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
<title>Edit goods</title>
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
					<li><a href="catalogAdmin">Catalog</a></li>
					<li class="active"><a>Edit Goods</a></li>
					<li><a href="editgroup.jsp">Add Group</a></li>
					<li><a href="logout">Logout</a></li>
				</ul>
			</div>
		</div>
	</div>
	
	<div class="section">
		<div class="container">
			<div class="row">
				<div class="col-md-4">Catalog choise place</div>
				<div class="col-md-4">
					<form role="form" action="editGoodsSubmit" method="get">
						<input type="hidden" name="editID" value="${param.editID}">
						<div class="form-group">
							<label class="control-label" for="goodsname">Goods name:</label>
							<input class="form-control" id="goodsname" name="goodsname"
								placeholder="Goods name" type="text" value="${param.name}">
						</div>
						<div class="form-group">
							<label class="control-label" for="price">Price:</label>
							<input class="form-control" id="price" name="price" 
								placeholder="Price" value="${param.price}">
						</div>
						<div class="form-group">
							<label class="control-label" for="amount">Amount:</label> <input
								class="form-control" id="amount" name="amount"
								placeholder="Amount" type="text" value="${param.amount}">
						</div>
						<div class="form-group">
							<label class="control-label" for="groupID">Group ID:</label>
							<input class="form-control" id="groupID" name="groupID"
								placeholder="GroupID" type="text" value="${param.groupID}">
						</div>
						<button type="submit" class="btn btn-primary form-control">Save</button>
					</form>
				</div>
				<div class="col-md-4"></div>
			</div>
		</div>
	</div>
	</div>
</body>
</html>