<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="/view/error.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
  <meta charset="utf-8">
  <%@ include file="includes/everypageheader.jspf" %>

  <!-- Custom styles for this template -->
  <link href="<%=request.getContextPath()%>/resources/css/signin.css" rel="stylesheet">

</head>
<title>User Login Page</title>

<body>
<%--<jsp:param name="myVar" value="${instanceVar}"/>--%>

<%@include file="includes/toplogo.jspf" %>
<%--<jsp:include page="includes/toplogo.jspf" />--%>



<%--${condition ? "some text when true" : "some text when false"}--%>
<%--<% String ERR=(String)request.getAttribute("errormsg"); %>

<c:if test="${ERR != null}"><div class="alert alert-danger" role="alert">${errormsg}</div></c:if>
<c:if test="${errorcode=='1'}"><div class="alert alert-danger" role="alert">${errormsg}</div></c:if>
  <c:out value="${ERR}" default="*****************************" >-----------------------</c:out>
  <div class="alert alert-danger" role="alert">${errormsg}</div>
  --%>


<div class="container">

  <div class="card">
    <div class="card-block">
        <br><br>
        <div class="panel-heading">
          <h2 class="panel-title badge indigo">&nbsp; Authorization Page &nbsp;</h2>

        </div>

        ${errormsg_html}
        ${infomsg_html}

        <div class="panel-body">
          <form class="form-signin" action="?command=authenticate" method="POST">
            <h2 class="form-signin-heading ">Please Log In</h2>
            <label for="email" class="sr-only">Email address</label>
            <input name="email" value="${email}" type="email" id="email" class="form-control" placeholder="Email address" required autofocus>
            <label for="password" class="sr-only">Password</label>
            <input name="password" type="password" id="password" class="form-control" placeholder="Password" required>

            <button class="btn mdb-color darken-3 white-text btn-sm" type="submit">Sign in</button>

          </form>
        </div>
      </div>
  </div>
<%--


    <form class="form-signin" action="?command=authenticate" method="POST">
      <h2 class="form-signin-heading">Please Log In</h2>
      <label for="email" class="sr-only">Email address</label>
      <input name="email" type="email" id="email" class="form-control" placeholder="Email address" required autofocus>
      <label for="password" class="sr-only">Password</label>
      <input name="password" type="password" id="password" class="form-control" placeholder="Password" required>

      <button class="btn btn-primary" type="submit">Sign in</button>

    </form>

--%>









</div> <!-- /container -->


<%--
<h3 class="message"><%=service.Login.getMessage()%></h3>
--%>
<%--

<div class="container">

<form class="form-signin" action="?command=authenticate" method="POST">
  <label for="email" class="sr-only">Email address</label>
    <input type="email" id="email" class="form-control" placeholder="Email address" required autofocus>

  <label for="password" class="sr-only">Password</label>
    <input type="password" id="password" class="form-control" placeholder="Password" required>

  <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>

</form>
</div>
--%>

<%@include file="includes/btmlinks.jspf" %>

<%@include file="includes/everypagefooter.jspf" %>








</body>
</html>