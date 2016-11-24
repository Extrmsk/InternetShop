<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<ul>
	<c:set var="level" value="${level+1}" scope="request"/>
	<c:set var="curPath" value="${path}" scope="page"/>
	<c:forEach var="node" items="${node.childrens}">
	<li>
		<c:set var="path" value="${(empty curPath)? node.id : (curPath += ',') += node.id}" scope="request" />
		<a href="?path=${path}"><span style="font-weight:${(path == paramsPath)? 'bold' : 'normal'}"><c:out value="${node.name}" /></span></a>
		<c:set var="node" value="${node}" scope="request" />
		<c:if test="${fn:length(pathItems) > level && pathItems[level] == node.id}">
		<jsp:include page="node.jsp" />
		</c:if>
	</li>
	</c:forEach>
	<c:set var="path" value="${curPath}" scope="request"/>
	<c:set var="level" value="${level-1}" scope="request"/>
</ul>