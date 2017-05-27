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

<h3 class="message">REGISTRATION (new usr+new card)</h3>

<form action="?command=register" method="POST">
  name:    <input type="text" name="name" value="" id="nam"><br><br>
  e-mail:  <input type="text" name="email" value="" id="eml"><br><br>
  password:<input type="password" name="password" id="pwd"><br><br>
  <input type="submit" value="Proceed">
</form>


<%@include file="includes/btmlinks.jsp" %>
<%@include file="includes/everypagefooter.jsp" %>
</body>
</html>