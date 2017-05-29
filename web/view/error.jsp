<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page isErrorPage="true" import="java.io.*"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <%@ include file="includes/everypageheader.jspf" %>


</head>
<title>ERROR</title>

<body>
<%@include file="includes/toplogo.jspf" %>
<body>

<div class="container  w-50">
    <div class="">
        <div class="card">
            <div class="h-50">!</div><br>
            <div class="badge badge-warning"> An Error Occured.</div><br>

            <div class="alert alert-danger" role="alert">
            ${errorMsg}
            </div>
            <div class="h-25"></div>

            <div>
                <c:set var="exception" value="${requestScope['javax.servlet.error.exception']}"/>
                <pre class="text-sm-left">
                <jsp:scriptlet>
                    exception.printStackTrace(new java.io.PrintWriter(out));
                </jsp:scriptlet>
                </pre>
            </div>

            <div>
                <c:if test="${not empty returnPage}">
                    <div>
                        Return to <a href="${returnPage}">previous page.</a>
                    </div>
                </c:if>
            </div>

        </div>



    </div>

</div>


<%@include file="includes/btmlinks.jspf" %>
<%@include file="includes/everypagefooter.jspf" %>
</body>
</html>
</body>
</html>
