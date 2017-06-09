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
      <h3 class="col-md-12 text-right" id="hello"></h3>

      ${errormsg_html}
      <c:if test="${not empty errormsg}">
        <%--<span class="badge badge-danger">${errormsg}</span>--%>
        <div class="alert alert-danger" role="alert"><fmt:message key="${errormsg}" bundle="${lang}"/></div>
      </c:if>
      <c:if test="${not empty sessionScope.errormsg}">
        <%--<div class="alert alert-danger" role="alert"><fmt:message key="${sessionScope.errormsg}" bundle="${lang}"/></div>--%>
        <c:set var="errormsg" value="" scope="session"/>
      </c:if>


      ${infomsg_html}
      <c:if test="${not empty infomsg}">
        <span class="badge badge-success black-text"><fmt:message key="${infomsg}" bundle="${lang}"/></span>
      </c:if>
      <c:if test="${not empty sessionScope.infomsg}">
      <%--<span class="badge badge-success"><fmt:message key="${sessionScope.infomsg}" bundle="${lang}"/></span>--%>
        <c:set var="infomsg" value="" scope="session"/>
      </c:if>


      <c:if test="${sessionScope.isAuthorized==true}">
        <script>
        $("#hello").html('<fmt:message key="HELLO_AND_WELCOME_BACK" bundle="${lang}"/>, ${sessionScope.client.getName()}!');
        </script>


        <%-- ########  AUTHORIZED USER INDEX  ######## --%>
        <c:if test="${(empty action) }">
          <%@ include file="includes/user.index.jspf" %>
        </c:if><%--empty action--%>


        <%-- ########  ACTION form_transfer ######## --%>
        <c:if test="${action=='form_transfer' || action=='form_replenish'}">
          <%@ include file="includes/user.form_transfer.jspf" %>
        </c:if><%--/form_transfer--%>

        <%-- ########  ACTION form_transfer ######## --%>
        <%--<c:if test="${action=='form_replenish'}">--%>
          <%--<%@ include file="includes/user.form_replenish.jspf" %>--%>
        <%--</c:if>&lt;%&ndash;/form_replenish&ndash;%&gt;--%>

        <%-- ########  ACTION transaction ######## --%>
        <c:if test="${action=='transaction'}">
          <%@ include file="includes/user.transaction.jspf" %>
        </c:if>



      </c:if><%--/Authorized Usr index--%>





      <%-- ########  LOGIN  ######## --%>
      <c:if test="${(action=='login')}">
        <%@ include file="includes/user.form_login.jspf" %>
      </c:if><%--/Login--%>


      <%-- ########  FEES Tbl  ######## --%>
      <c:if test="${action=='fees_table'}">
        <%@ include file="includes/open.fees.jspf" %>
      </c:if>


    </div>









    <div class="col-md-2"></div>
  </div>  <%--/div.row--%>
</div> <!-- /container -->

<%@include file="includes/btmlinks.jspf" %>
<%@include file="includes/everypagefooter.jspf" %>


</body>
</html>

<%--<h3 class="message"><%=service.User.getMessage()%></h3>--%>

