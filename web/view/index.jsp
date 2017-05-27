<%--
Make sure that in your servlet-context.xml you have as follows:
<resources mapping="/resources/**" location="/resources/" />
Create a folder if does not already exist under webapps called resources
Place your css folder along with css files there
Reference my css file as follows:
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/960.css"/>
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <%--<link rel='stylesheet' href='webjars/bootstrap/3.3.6/css/bootstrap.min.css'>--%>
  <meta charset="utf-8">
  <%@ include file="includes/everypageheader.jsp" %>

</head>
<title>---EPMPROJBANK---</title>
<body>

<div style="height: 100vh">
  <div class="flex-center flex-column">
    <h1 class="animated fadeInUp mb-2" style="color:#0b51c5">the.BANK</h1>

    <h5 class="animated fadeInDownBig mb-1 red-text">your money is safe with us</h5>

    <hr>

<%--
    <h3 class="message">ADM: <%=service.Admin.getMessage()%></h3>
--%>

    <a href="/bank/?command=login">login</a><br>
    <a href="/bank/?command=register">register</a><br>

    <div style="text-align: right">
      <p class="animated fadeIn text-muted" >epm.proj.bank by Ton Chief</p>
    </div>

  </div>
</div>


<%@include file="includes/btmlinks.jsp" %>
<%@include file="includes/everypagefooter.jsp" %>

</body>

</html>


<%--
My root pages template:


<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<%@ include file="includes/everypageheader.jsp" %>

</head>
<title>

</title>
<body>
<%@include file="includes/toplogo.jsp" %>





<%@include file="includes/btmlinks.jsp" %>
<%@include file="includes/everypagefooter.jsp" %>
 </body>
</html>

--%>