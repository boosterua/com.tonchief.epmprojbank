<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="/view/error.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


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
  <div class="row">
    <div class="col-md-2"></div>

    <div class="col-md-8">

      ${errormsg_html}
      ${infomsg_html}


      <c:if test="${isAuthorized==true}">
        <h2>Hello and welcome back!</h2>
        <h3 class="badge indigo">TODO:Replace this with real auth!</h3>
        8-2-8
      </c:if>

      <%-- ########  LOGIN  ######## --%>

      <c:if test="${action=='login'}">
        <div class="card">
          <div class="card-block">
            <br><br>
            <div class="panel-heading">
              <h2 class="panel-title badge indigo">&nbsp; Authorization Page &nbsp;</h2>
            </div>

            <div class="panel-body">
              <form class="form-signin" action="?command=authenticate" method="POST">
                <h2 class="form-signin-heading "><fmt:message key="Please Log In" bundle="${lang}"/></h2>
                <label for="email" class="sr-only">Email</label>
                <input name="email" value="${email}" type="email" id="email" class="form-control" placeholder="Email address" required autofocus>
                <label for="password" class="sr-only"><fmt:message key="Password" bundle="${lang}"/></label>
                <input name="password" type="password" id="password" class="form-control" placeholder="Password" required>
                <button class="btn mdb-color darken-3 white-text btn-sm" type="submit">Sign in</button>
              </form>
            </div>
          </div>
        </div>
      </c:if>
      <%--/Login--%>

      <c:if test="${action=='fees_table'}">
        <div class="card-block">
          <br><br>
          <div class="panel-heading">
            <h3 class="panel-title badge indigo">&nbsp; <fmt:message key="Fee Schedule" bundle="${lang}"/> &nbsp;</h3>
          </div>

          <div class="panel-body">
            <c:if test="${not empty feeList}">
              <c:if test="not empty ${tableName}">
                <h4 class="badge indigo"><fmt:message key="${tableName}" bundle="${lang}"/></h4>
              </c:if>

              <table class="table table-striped table-hover table-sm   table-info ">
                <thead class="teal darken-3 text-white">
                <tr class="text-center"><c:forEach var="th" items="${tableHeadersArr}">
                  <th><fmt:message key="${th}" bundle="${lang}"/></th>
                </c:forEach>
                </tr>
                </thead>

                <tbody>
                <c:forEach var="fee" items="${feeList}">
                  <tr class="text-center">
                    <td>${fee.getId()}</td>
                    <td class="text-left">${fee.getName()}</td>
                    <td>${fee.getTransferFee()}</td>
                    <td>${fee.getNewCardFee()}</td>
                    <td>${fee.getApr()}%</td>
                  </tr>
                </c:forEach>
                </tbody>
              </table>
            </c:if>
          </div>
        </div>
      </c:if>












    </div>

    <div class="col-md-2"></div>
  </div>  <%--/div.row--%>
</div> <!-- /container -->

<%@include file="includes/btmlinks.jspf" %>
<%@include file="includes/everypagefooter.jspf" %>



<%--<h3 class="message"><%=service.User.getMessage()%></h3>--%>

