<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="/view/error.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xmlns:c="http://java.sun.com/jsp/jstl/core">
<head>
    <meta charset="utf-8">
    <%@ include file="includes/everypageheader.jspf" %>

</head>
<title>

</title>
<body>
<%@include file="includes/toplogo.jspf" %>

<div class="container w-50">

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
                <h3 class="panel-title  mdb-color lighten-5">&nbsp; <fmt:message key="Registration"/>&nbsp;</h3>

            </div>

            ${errormsg_html}

            <div class="panel-body">
                <form action="?command=register" method="POST" class="form-signin">

                    <div class="md-form">
                        <i class="fa fa-user prefix"></i>
                        <input type="text" id="name" name="name" class="form-control" NOplaceholder="Name" required>
                        <label for="name"><fmt:message key="FULL_NAME" bundle="${lang}"/></label>
                    </div>
                    <div class="md-form">
                        <i class="fa fa-envelope prefix"></i>
                        <input type="email" id="email" name="email" class="form-control" NOplaceholder="Email address"
                               required>
                        <label for="email">e-mail</label>
                    </div>

                    <div class="md-form">
                        <i class="fa fa-lock prefix"></i>
                        <input type="password" id="password" name="password" class="form-control"
                               NOplaceholder="Password" required>
                        <label for="password"><fmt:message key="Your" bundle="${lang}"/><fmt:message key="Password"
                                                                                                     bundle="${lang}"/></label>
                    </div>


                    <c:if test="${not empty feeNames}">
                        <div class="form-group">
                            <i class="fa fa-list-ul prefix"></i>
                            <label for="fee" class="control-label"><fmt:message key="SELECT_CARD_TYPE"
                                                                                bundle="${lang}"/></label>
                            <div class="col-auto">

                                <select class="form-control" id="fee" name="fee">
                                    <c:forEach var="feeKV" items="${feeNames}">
                                        <option value="${feeKV.key}">${feeKV.value}</option>
                                    </c:forEach>
                                </select><br>
                            </div>
                        </div>
                    </c:if>


                    <button type="submit" class="btn btn-orange btn btn-sm btn-block orange white-text text-lg-center">
                        <fmt:message key="PROCEED"/>
                    </button>
                </form>
            </div>
        </div>

    </div>

</div>


<%@include file="includes/btmlinks.jspf" %>
<%@include file="includes/everypagefooter.jspf" %>
</body>
</html>