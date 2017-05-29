<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<html>
<head>
  <meta charset="utf-8">
  <%@ include file="includes/everypageheader.jspf" %>
</head>
<title>${title}</title>

<body>
<%--<h3 class="message"><%=service.Admin.getMessage()%></h3>--%>
<%@include file="includes/toplogo.jspf" %>



<c:if test="${action='showClientsByRole'}">
    <c:if test="${not empty clientList}">
      <table class="table table-striped table-hover table-sm   table-info ">
        <thead class="teal darken-3 text-white">
        <tr class="text-center"><th>*</th>
        <tr class="text-center"><th>*</th>
        <tr class="text-center"><th>*</th>
        <tr class="text-center"><th>*</th>
        <tr class="text-center"><th>*</th>
        <tr class="text-center"><th>*</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach var="cl" items="${clientList}">
          <tr class="text-center">
            <td>${cl.getId()}</td>
            <td>${cl.getName()}</td>
            <td>${cl.getEmail()}</td>
            <td>${cl.getAccountId()}</td>
          </tr>
        </c:forEach>
        </tbody>
      </table>
    </c:if>
</c:if>














  </body>
</html>
