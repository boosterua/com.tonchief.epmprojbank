<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="/view/error.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/xhtml"  xmlns:c="http://java.sun.com/jsp/jstl/core">
<head>
  <meta charset="utf-8">
  <%@ include file="includes/everypageheader.jspf" %>

</head>
<title>

</title>
<body>
<%@include file="includes/toplogo.jspf" %>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>

<div class="container w-50">

  <%--<div class="md-form form-sm">--%>
    <div class="card">
      <div class="card-block">
    <br><br>



        ${errormsg_html}
        <c:if test="${not empty errormsg}">
          <%--<span class="badge badge-danger">${errormsg}</span>--%>
          <div class="alert alert-danger" role="alert"><fmt:message key="${errormsg}" bundle="${lang}"/></div>
        </c:if>

        ${infomsg_html}
        <c:if test="${not empty infomsg}">
          <span class="badge badge-success"><fmt:message key="${infomsg}" bundle="${lang}"/></span>
        </c:if>





        <div class="panel-heading">
      <h3 class="panel-title  mdb-color lighten-5">&nbsp; <fmt:message key="Registration" bundle="${lang}"/>&nbsp;</h3>
    </div>

    ${errormsg_html}

    <div class="panel-body">
      <form action="?command=register" method="POST" class="form-signin">

          <%--    <input type="text" name="name" value="" id="name" class="form-control"
                           placeholder="Full name" required>
                  <label for="name" class="">Your full name</label>

                    <input type="text" name="email" value="" id="email" class="form-control"
                           placeholder="Email address" required><br>
                  <label for="email" class="sr-only">Email address</label>

                    <input type="password" name="password" id="password" class="form-control"
                           placeholder="Password" required><br>
                  <label for="password" class="sr-only">Email address</label>--%>


        <div class="md-form">
          <i class="fa fa-user prefix"></i>
          <input type="text" id="name" name="name" class="form-control" NOplaceholder="Name"  required>
          <label for="name"><fmt:message key="Full Name" bundle="${lang}"/></label>
        </div>
        <div class="md-form">
          <i class="fa fa-envelope prefix"></i>
          <input type="email" id="email" name="email" class="form-control" NOplaceholder="Email address" required>
          <label for="email">e-mail</label>
        </div>

        <div class="md-form">
          <i class="fa fa-lock prefix"></i>
          <input type="password" id="password" name="password" class="form-control" NOplaceholder="Password" required>
          <label for="password"><fmt:message key="Your" bundle="${lang}"/><fmt:message key="Password" bundle="${lang}"/></label>
        </div>


        <c:if test="${not empty feeNames}">
          <div class="form-group">
              <i class="fa fa-list-ul prefix"></i>
            <label for="fee"  class="control-label"><fmt:message key="SELECT_CARD_TYPE" bundle="${lang}"/></label>
              <div class="col-auto">

            <select class="form-control" id="fee" name="fee">
              <c:forEach var="feeKV" items="${feeNames}">
                <option value="${feeKV.key}">${feeKV.value}</option>
              </c:forEach>
            </select><br>
              </div>
          </div>
        </c:if>




        <input type="submit" value="<fmt:message key="PROCEED" bundle="${lang}"/>" class="btn btn-orange btn btn-sm btn-block orange white-text text-lg-center">
      </form>

<%--

      <c:forEach var = "fee" items = "${feeList}">
        ${fee.id}
        *
        ${fee.getId()}
        **
        ${fee}.getId()
        ***${fee}<br>
                <c:out value = "${fee}" />
      </c:forEach>
      <hr>

      <small>${feeList}<hr>
          ${feeNames}

          <br>
        <c:out value="${feeList}" />

--%>



















    </div>
    </div>

  </div>

</div>



<%--


<div class="container w-25">
<!--Form with header-->
<div class="card">
  <div class="card-block">

    <!--Header-->
    <div class="form-header blue-gradient">
      <h3><i class="fa fa-user"></i> Register:</h3>
    </div>

    <!--Body-->
    <div class="md-form">
      <i class="fa fa-user prefix"></i>
      <input type="text" id="form3" class="form-control">
      <label for="form3">Your name</label>
    </div>
    <div class="md-form">
      <i class="fa fa-envelope prefix"></i>
      <input type="text" id="form2" class="form-control">
      <label for="form2">Your email</label>
    </div>

    <div class="md-form">
      <i class="fa fa-lock prefix"></i>
      <input type="password" id="form4" class="form-control">
      <label for="form4">Your password</label>
    </div>

    <div class="text-center">
      <button class="btn btn-indigo">Sign up</button>
      <hr>
      <fieldset class="form-group">
        <input type="checkbox" id="checkbox1">
        <label for="checkbox1">Subscribe me to the newsletter</label>
      </fieldset>
    </div>

  </div>
</div>
<!--/Form with header-->




<!--Form with header-->
<div class="card w-25">
  <div class="card-block">

    <!--Header-->
    <div class="form-header blue-gradient">
      <h3><i class="fa fa-user"></i> Register:</h3>
    </div>

    <!--Body-->
    <div class="md-form">
      <i class="fa fa-user prefix"></i>
      <input type="text" id="form3" class="form-control">
      <label for="form3">Your name</label>
    </div>
    <div class="md-form">
      <i class="fa fa-envelope prefix"></i>
      <input type="text" id="form2" class="form-control">
      <label for="form2">Your email</label>
    </div>

    <div class="md-form">
      <i class="fa fa-lock prefix"></i>
      <input type="password" id="form4" class="form-control">
      <label for="form4">Your password</label>
    </div>

    <div class="text-center">
      <button class="btn btn-indigo">Sign up</button>
      <hr>
      <fieldset class="form-group">
        <input type="checkbox" id="checkbox1">
        <label for="checkbox1">Subscribe me to the newsletter</label>
      </fieldset>
    </div>

  </div>
</div>
<!--/Form with header-->
</div>
--%>


<%@include file="includes/btmlinks.jspf" %>
<%@include file="includes/everypagefooter.jspf" %>
</body>
</html>