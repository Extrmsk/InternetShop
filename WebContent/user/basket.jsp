<%@page import="com.lemanov.internetshop.domain.GoodsManager"%>
<%@page import="org.apache.jasper.tagplugins.jstl.ForEach"%>
<%@page import="com.lemanov.internetshop.domain.ShopManager"%>
<%@page import="com.lemanov.internetshop.domain.Goods" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
<title>Basket user page</title>
</head>
<body>

	<div class="navbar navbar-default navbar-static-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="./index.jsp"> <span>OnlineShop</span><br>
				</a>
			</div>
			<div class="collapse navbar-collapse">
				<ul class="nav navbar-nav navbar-right">
					<li><a><b>${sessionScope.userName}'s cabinet</b></a></li>
					<li><a href="${pageContext.request.contextPath}/catalogPrepare">Catalog</a></li>
					<li><a href="${pageContext.request.contextPath}/ordersPrepare">Orders</a></li>
					<li class="active"><a>Basket</a></li>
					<li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
				</ul>
			</div>
		</div>
	</div>

	<div class="section">
		<div class="container">
			<div class="row">
				<div class="col-md-3">Catalog choise place</div>
				<div class="col-md-7">
					<table class="table">
						<thead>
							<tr>
								<th>Goods name</th>
								<th>Amount</th>
								<th>Price</th>
								<th>Action</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${requestScope.orderLines}" var="orderLine">
								<tr>
									<td>${orderLine.goodsName}</td>
									<td>${orderLine.amount}</td>
									<td>${orderLine.price}</td>
									<td><a
										href="./delOrderLineFromBasket?deleteID=${orderLine.goodsID}">
											<img src="./img/delete.png" height="50" />
									</a></td>

								</tr>
							</c:forEach>
						</tbody>
					</table>
					<p></p>
					<table class="table">
						<thead>
							<tr><td>Total price</td>
							</tr>
						</thead>
						<tbody>
							<tr><td>
								<p>${requestScope.basketPrice}</p></td>
							</tr>
						</tbody>
					</table>
					<c:if test="${!requestScope.orderLines.isEmpty()}">
						<a href="./basketPrepare?action=clear"><button type="submit"
								class="btn btn-default btn-block">Clear basket</button></a>
						<a href="./user/addorder.jsp"><button type="submit"
								class="btn btn-primary btn-block">Create order</button></a>
					</c:if>
				</div>
				<div class="col-md-1"></div>
			</div>
		</div>
	</div>

</body>
</html>