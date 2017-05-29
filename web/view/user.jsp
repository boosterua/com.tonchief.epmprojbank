<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <%@ include file="includes/everypageheader.jspf" %>

  <!-- Custom styles for this template -->
  <link href="<%=request.getContextPath()%>/resources/css/signin.css" rel="stylesheet">

</head>
<title>User Page</title>

<body>
<%@include file="includes/toplogo.jspf" %>


<div class="container">
  <h2>Hello and welcome back!</h2>
  <h3 class="badge indigo">TODO:Replace this with real auth!</h3>
  <%--<h3 class="message"><%=service.User.getMessage()%></h3>--%>


</div> <!-- /container -->



<%@include file="includes/btmlinks.jspf" %>
<%@include file="includes/everypagefooter.jspf" %>


