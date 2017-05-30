<%--<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="/view/error.jsp" %>--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="/view/error.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta charset="utf-8">
    <%@ include file="../includes/everypageheader.jspf" %>
</head>
<title>${title}</title>

<body>

<%@include file="../includes/toplogo.jspf" %>


<div class="container">
    <div class="row">
        <div class="col-md-3"></div>

        <div class="col-md-6">
             <div class="card-block">
                 <br><br>
                 <div class="panel-heading">
                    <h3 class="panel-title badge indigo">&nbsp; Fee Schedule &nbsp;</h3>
                 </div>

                 ${errormsg_html}

                <div class="panel-body">
                    <c:if test="${not empty feeList}">
                      <c:if test="not empty ${tableName}">
                        <h4 class="badge indigo">${tableName}</h4>
                      </c:if>

                      <table class="table table-striped table-hover table-sm   table-info ">
                        <thead class="teal darken-3 text-white">
                        <tr class="text-center"><c:forEach var="th" items="${tableHeadersArr}">
                          <th>${th}</th>
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

                      <%--<%@include file="includes/dynamictable.jspf" %>--%>
                    </c:if>
                </div>
             </div>
        </div>
        <div class="col-md-3"></div>
    </div><%--/row--%>
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


<%@include file="../includes/btmlinks.jspf" %>
<%@include file="../includes/everypagefooter.jspf" %>
</body>
</html>