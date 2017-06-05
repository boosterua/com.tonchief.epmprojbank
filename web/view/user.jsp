<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="/view/error.jsp" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="mft" uri="http://java.sun.com/jsp/jstl/fmt" %>


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
      <c:if test="${not empty errormsg}">
        <%--<span class="badge badge-danger">${errormsg}</span>--%>
        <div class="alert alert-danger" role="alert"><fmt:message key="${errormsg}" bundle="${lang}"/></div>
      </c:if>

      ${infomsg_html}
        <c:if test="${not empty infomsg}">
          <span class="badge badge-success"><fmt:message key="${infomsg}" bundle="${lang}"/></span>
        </c:if>



      <c:if test="${sessionScope.isAuthorized==true}">
        <h3>Hello and welcome back, ${sessionScope.client.getName()}!</h3>
      </c:if>






        <%-- ########  AUTHORIZED USER INDEX  ######## --%>

        <c:if test="${(empty action) }">
          <div class="card">
            <div class="card-block">
              <div class="panel-heading">
                <h2 class="panel-title badge indigo">&nbsp; <fmt:message key="YOUR_ACCOUNTS" bundle="${lang}"/> &nbsp;</h2>
              </div>
              <c:if test="${not empty accounts}">






                <table class="table table-striped table-hover table-sm table-info">
                  <thead class="teal darken-3 text-white">
                  <tr class="text-center">
                    <th><fmt:message key="ID" bundle="${lang}"/></th>
                    <th><fmt:message key="ACCT_NUMBER" bundle="${lang}"/></th>
                    <th><fmt:message key="STATE" bundle="${lang}"/></th>
                    <th><fmt:message key="BALANCE" bundle="${lang}"/></th>
                    <th><fmt:message key="ACTIONS" bundle="${lang}"/></th>
                  </tr>
                  </thead>

                  <tbody>
                  <c:forEach var="acct" items="${accounts}">
                    <%--<c:out value="${acct}"/><br>--%>

                    <tr class="text-center">
                      <td>${acct.getId()}</td>
                      <td>${acct.getName()}</td>
                      <td><c:if test="${acct.getBlocked()==true}">
                          <fmt:message key="BLOCKED" bundle="${lang}"/>
                          </c:if>
                      </td>
                      <td>${acct.getBalance()}</td>
                      <td>
                        <a href="?command=account&action=make_payment">pmnt</a> |
                        <a href="?command=account&action=replenish">rpsh</a> |
                        <a href="?command=account&action=block">blck</a>
                      </td>
                    </tr>
                  </c:forEach>
                  </tbody>
                </table>



















              </c:if>

              <div class="panel-body">


              </div>
            </div>
          </div>
        </c:if>
        <%--/Authorized Usr index--%>




      <%-- ########  LOGIN  ######## --%>
      <c:if test="${(action=='login')}">
        <div class="card">
          <div class="card-block">
            <div class="panel-heading">
              <h2 class="panel-title badge indigo">&nbsp; <fmt:message key="Authorization Page" bundle="${lang}"/> &nbsp;</h2>
            </div>

            <div class="panel-body">
              <form class="form-signin" action="?command=authenticate" method="POST">
                <h2 class="form-signin-heading "><fmt:message key="PLEASE_LOG_IN" bundle="${lang}"/></h2>
                <label for="email" class="sr-only">Email</label>
                <input name="email" value="${email}" type="email" id="email" class="form-control" placeholder="Email address" required autofocus>
                <label for="password" class="sr-only"><fmt:message key="Password" bundle="${lang}"/></label>
                <input name="password" type="password" id="password" class="form-control" placeholder='<fmt:message key="Password" bundle="${lang}"/>' required>
                <button class="btn mdb-color darken-3 white-text btn-sm" type="submit"><fmt:message key="SIGN_IN" bundle="${lang}"/></button>
              </form>
            </div>
          </div>
        </div>
      </c:if>
      <%--/Login--%>


        <%-- ########  FEES Tbl  ######## --%>

        <c:if test="${action=='fees_table'}">
        <div class="card-block">
          <br><br>
          <div class="panel-heading">
            <h3 class="panel-title badge indigo">&nbsp; <fmt:message key="Fee_Schedule" bundle="${lang}"/> &nbsp;</h3>
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
                    <td><fmt:formatNumber value="${fee.getNewCardFee()}" type="currency" currencySymbol="_"/></td>
                    <td><fmt:formatNumber type="percent" maxIntegerDigits="2" value="${fee.getApr()}" /></td>
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

