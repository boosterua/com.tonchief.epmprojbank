<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
  <style type="text/css">
    .menubar { background: #8a1a00  !important; }
  </style>
</head>
<title>Admin Page</title>

<body>
<%@include file="includes/toplogo.jspf" %>

<%--
<div class="badge badge-warning">  <fmt:message key="Test message Wiith Spaces in it" bundle="${lang}"/>.</div><br>
<div class="badge badge-warning">  <fmt:message key="Test message with spaces w/o prop" bundle="${lang}"/>.</div><br>
--%>


<div class="container">
  <div class="row">
    <div class="col-md-2"></div>

    <div class="col-md-8">
      <h1> admin page</h1>

      ${errormsg_html}
      <c:if test="${not empty errormsg}">
        <div class="alert alert-danger" role="alert"><fmt:message key="${errormsg}" bundle="${lang}"/></div>
      </c:if>

      ${infomsg_html}
      <c:if test="${not empty infomsg}">
        <span class="badge badge-success"><fmt:message key="${infomsg}" bundle="${lang}"/></span>
      </c:if>

      <%--<c:if test="${isAuthorized==true}">--%>

        <%-- ######## SHOW CLIENTS BY ROLE #########--%>

        <c:if test="${action==\"showClientsByRole\"}">
          <div class="panel-heading">
            <h3 class="panel-title badge indigo">&nbsp; <fmt:message key="NEW_CLIENTS_APPROVE_LIST" bundle="${lang}"/>&nbsp;</h3>
          </div>
          <c:if test="${clientList.size() > 0}">
            <table class="table table-striped table-hover table-sm   table-info ">
              <thead class="teal darken-3 text-white">
              <tr class="text-center">
                <th><fmt:message key="ID" bundle="${lang}"/></th>
                <th><fmt:message key="NAME" bundle="${lang}"/></th>
                <th><fmt:message key="EMAIL" bundle="${lang}"/></th>
                <th><fmt:message key="ACCOUNT_ID" bundle="${lang}"/></th>
              </tr>
              </thead>

              <tbody>
              <c:forEach var="cl" items="${clientList}">
                <tr class="text-center">
                  <td><a href="<%=request.getContextPath()%>/bank/?command=show_clients&action=getOneClient&id=${cl.getId()}">${cl.getId()}</a></td>
                  <td>${cl.getName()}</td>
                  <td>${cl.getEmail()}</td>
                  <td>${cl.getAccount()}</td>
                </tr>
              </c:forEach>
              </tbody>
            </table>
          </c:if>
          <c:if test="${clientList.size() == 0}">
            <div class="badge badge-warning">  <fmt:message key="NO_CLIENTS_PER_REQUEST" bundle="${lang}"/>.</div><br>
          </c:if>
        </c:if>
      <%--</c:if>--%>


       <%-- ######## showClientToApprove #########--%>

        <c:if test="${action==\"showClientToApprove\"}">
          <div class="panel-heading">
            <h3 class="panel-title badge indigo">&nbsp; <fmt:message key="NEW_CLIENT_TO_APPROVE" bundle="${lang}"/>&nbsp;</h3>
          </div>
          <c:if test="${not empty client}">
            <table class="table table-striped table-hover table-sm table-info ">
              <tbody>
                <tr><td><fmt:message key="ID" bundle="${lang}"/></td><td>${client.getId()}</td>
                <tr><td><fmt:message key="NAME" bundle="${lang}"/></td><td>${client.getName()}</td>
                <tr><td><fmt:message key="EMAIL" bundle="${lang}"/></td><td>${client.getEmail()}</td>
                <tr><td><fmt:message key="FEE_CARD_NAME" bundle="${lang}"/></td><td>${client.getFeeName()}</td>
                <tr><td><fmt:message key="ROLE_CODE" bundle="${lang}"/></td><td>${client.getRole()}</td>
                <tr><td><fmt:message key="APPROVE" bundle="${lang}"/></td><td><input type="checkbox" checked data-toggle="toggle"></td>
              </tbody>
            </table>
          </c:if>
          <a href="/bank/?command=admin&action=approveUser&userId=${client.getId()}">Approve.User</a> |
          <a href="/bank/?command=admin&action=unblockUserAccount&userId=${client.getId()}">unblockUserAccount</a>




        </c:if>



      <%--</c:if>--%>












    </div>

    <div class="col-md-2"></div>
  </div>  <%--/div.row--%>
</div> <!-- /container -->

<%@include file="includes/btmlinks.jspf" %>
<%@include file="includes/everypagefooter.jspf" %>

  </body>
</html>

<%--//TODO:Formatting numbers -= which layer??--%>