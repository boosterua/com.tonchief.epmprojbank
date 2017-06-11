<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page isErrorPage="true" import="java.io.*"%>

<fmt:setBundle basename="locale.locale" />

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <%@ include file="includes/everypageheader.jspf" %>

<%--//TODO Catch 404  errors --%>
</head>
<title>ERROR</title>

<body>
<%@include file="includes/toplogo.jspf" %>
<body>

<div class="container">
    <div class="">
        <div class="card">
            <div class="h-50">!</div><br>
            <div class="badge badge-warning"> An Error Occured.</div><br>

            <c:if test="${not empty errorMsg}">
                <div class="alert alert-danger" role="alert">
                    ${errorMsg}
                </div>
            </c:if>


            <c:if test="${not empty errormsg}">
                <div class="alert alert-danger" role="alert"><fmt:message key="${errormsg}"/></div>
            </c:if>
            <c:if test="${not empty sessionScope.errormsg}">
                <c:set var="errormsg" value="" scope="session"/>
            </c:if>



            <div class="h-25"></div>

            <div>
                <c:set var="exception" value="${requestScope['javax.servlet.error.exception']}"/>
                <pre class="text-sm-left">
<%--                <jsp:scriptlet>
                    if(exception!=null)
                        exception.printStackTrace(new java.io.PrintWriter(out));
                </jsp:scriptlet>--%>
                    <c:forEach items="${exception.stackTrace}" var="element">
                        <c:out value="${element}" />
                    </c:forEach>

${pageContext.out.flush();exception.printStackTrace(pageContext.response.writer)}

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
