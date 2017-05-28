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

<div class="container w-50">

  <%--<div class="md-form form-sm">--%>
    <div class="card">
      <div class="card-block">
    <br><br>
    <div class="panel-heading">
      <h3 class="panel-title  mdb-color lighten-5">&nbsp; REGISTRATION (new usr + new card)&nbsp;</h3>
    </div>

    ${errormsg_html}

    <div class="panel-body" style="width:50%">
      <form action="?command=register" method="POST" class="form-signin">


          <input type="text" name="name" value="" id="name" class="form-control"
                 placeholder="Full name" required>
        <label for="name" class="">Your full name</label>
        <br>

          <input type="text" name="email" value="" id="email" class="form-control"
                 placeholder="Email address" required><br>
        <label for="email" class="sr-only">Email address</label>
        <br>

          <input type="password" name="password" id="password" class="form-control"
                 placeholder="Password" required><br>
        <label for="password" class="sr-only">Email address</label>

        <c:if test="${not empty feeNames}">
          <div class="form-group">
            <i class="fa fa-list-ul" aria-hidden="true"></i>
            <label for="fee">Select the type of card you would like to get</label>
            <select class="form-control" id="fee">
              <c:forEach var="fee" items="${feeNames}">
                <option value="${fee.key}">${fee.value}</option>
              </c:forEach>
            </select>
          </div>
        </c:if>




        <input type="submit" value="Proceed" class="btn btn-sm  mdb-color darken-3 white-text">
      </form>
    </div>
    </div>

  </div>

</div>





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


<%@include file="includes/btmlinks.jsp" %>
<%@include file="includes/everypagefooter.jsp" %>
</body>
</html>