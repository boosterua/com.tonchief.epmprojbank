<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
Make sure that in your servlet-context.xml you have as follows:
<resources mapping="/resources/**" location="/resources/" />
Create a folder if does not already exist under webapps called resources
Place your css folder along with css files there
Reference my css file as follows:
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/960.css"/>

variables used on pages:

infoMsg
errorMsg
errorCode
dateTime
returnPage
feeNames : HashMap
feeList : List<Fee>
isAdmin : bool
isAuthorized : bool

tableName
tableHeadersArr
tableDataArr

title
body
includeJSP

-sectionID : String
+action: String

--%>
<c:set var="URI" value="<%=request.getContextPath()%>"/>
<c:choose>

  <c:when test="${sessionScope.isAdmin==true}">
    <c:redirect url="${URI}/bank/?command=admin"/>
  </c:when>
  <c:when test="${sessionScope.isAuthorized==true}">
    <c:redirect url="${URI}/bank/?command=client"/>
  </c:when>
</c:choose>


<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <%@ include file="includes/everypageheader.jspf" %>
<style>

</style>
</head>
<title>[EPMPROJBANK]</title>
<body>

<div style="height: 100vh">
  <div class="flex-center flex-column">
    <h1 class="animated fadeInUp mb-2 mdb-color darken-3 deep-orange-text z-depth-3 display-4" style="color:#0b51c5;">
        &nbsp; <b class="text-white">the.</b>BANK &nbsp;
    </h1>

    <h5 class="animated fadeInDownBig mb-1 red-text">your money is safe with us</h5>

    <hr>

    <a href="<%=request.getContextPath()%>/bank/?command=login">login</a><br>
    <a href="<%=request.getContextPath()%>/bank/?command=register">register</a><br>

    <div style="text-align: right">
        <span class="badge badge-default animated fadeIn"><i class="fa fa-btc" aria-hidden="true"></i> epm.proj.bank by Ton Chief</span>
    </div>

  </div>
</div>

<%@include file="includes/btmlinks.jspf" %>
<%@include file="includes/everypagefooter.jspf" %>

</body>
</html>