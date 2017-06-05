<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
  <link href="<%=request.getContextPath()%>/resources/css/toggleswitch.css" rel="stylesheet">
  <style type="text/css">
    .menubar {
      background: #8a1a00 !important;
    }

    .toggleon td {
      background: #00B833;
    }

    .toggleoff td {
      background: inherit;
    }

    .toggleon td label div {
      background: #99DD99 !important;
    }

    div.bgred {
      background: #DD9999 !important;
    }

  </style>


  <%--<script src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
    <link href="https://gitcdn.github.io/bootstrap-toggle/2.2.2/css/bootstrap-toggle.min.css" rel="stylesheet">
    <script src="https://gitcdn.github.io/bootstrap-toggle/2.2.2/js/bootstrap-toggle.min.js"></script>
  --%>
</head>
<title>Admin Page</title>

<body>
<%@include file="includes/toplogo.jspf" %>


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

      <c:if test="${action==\"index\"}">
        <a href="<%=request.getContextPath()%>/bank/?command=admin&action=show_clients_by_role&role=0">Show newly registered clients waiting for approval</a><br>
        <a href="<%=request.getContextPath()%>/bank/?command=admin&action=show_clients_with_blocked_accounts">Show clients with blocked accounts</a><br>
        <a href="<%=request.getContextPath()%>/bank/?command=admin&action=show_all_clients">Show all clients</a><br>

      </c:if>


      <%-- ######## SHOW CLIENTS BY ROLE #########--%>

      <c:if test="${action==\"show_clients_by_role\"}">
        <div class="panel-heading">
          <h3 class="panel-title badge indigo">&nbsp; <fmt:message key="NEW_CLIENTS_APPROVE_LIST"
                                       bundle="${lang}"/>&nbsp;</h3>
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
                <td>
                  <a href="<%=request.getContextPath()%>/bank/?command=admin&action=get_one_client&user_id=${cl.getId()}">${cl.getId()}</a>
                </td>
                <td>
                  <nobr>${cl.getName()}</nobr>
                </td>
                <td>${cl.getEmail()}</td>
                <td>${cl.getAccount().getName()}
                  <c:if test="${cl.getAccount().getId()==0}">*</c:if>
                  <c:if test="${cl.getAccount().getBlocked()==true}"> <span
                    class="btn btn-deep-orange btn-sm"
                    style="line-height:2px;padding:7px 4px;margin:0;"><fmt:message key="BLOCKED"
                                                     bundle="${lang}"/></span>
                </td>
                <td>${client.getFeeName()}</span>
                  </c:if></td>
              </tr>
            </c:forEach>
            </tbody>
          </table>
        </c:if>
        <c:if test="${clientList.size() == 0}">
          <div class="badge badge-warning"><fmt:message key="NO_CLIENTS_PER_REQUEST" bundle="${lang}"/>.</div>
          <br>
        </c:if>
      </c:if>
      <%--</c:if>--%>


      <%-- ######## show Single Client To Approve #########--%>

      <c:if test="${action==\"show_сlient_to_approve\"}">

      <div class="panel-heading">
        <h3 class="panel-title badge indigo">&nbsp;
          <c:choose>
            <c:when test="${client.getRole()==0}"> <fmt:message key="NEW_CLIENT_TO_APPROVE"
                                      bundle="${lang}"/></c:when>
            <c:otherwise>
              <fmt:message key="CLIENT_DETAILS" bundle="${lang}"/>
            </c:otherwise>
          </c:choose>
          &nbsp;</h3>
      </div>
      <c:if test="${not empty client}">

      <div class="row">
        <div class="col-md-8">
          <table class="table table-striped table-hover table-sm table-info"
               style="background:#dddedf;<c:if test='${client.getRole()!=0}'>
                   background:#00DDAA !important</c:if>" id="client_details">
            <tbody>
            <tr>
              <td><fmt:message key="ID" bundle="${lang}"/></td>
              <td>${client.getId()}</td>
            <tr>
              <td><fmt:message key="NAME" bundle="${lang}"/></td>
              <td>${client.getName()}</td>
            <tr>
              <td><fmt:message key="EMAIL" bundle="${lang}"/></td>
              <td>${client.getEmail()}</td>
            <tr>
              <td><fmt:message key="FEE_CARD_NAME" bundle="${lang}"/></td>
              <td>${client.getFeeName()}</td>
            <tr>
              <td><fmt:message key="ROLE_CODE" bundle="${lang}"/></td>
              <td>${client.getRole()}</td>
            <tr id="tr_btn_appr">
              <td><fmt:message key="APPROVE" bundle="${lang}"/></td>
              <td style="line-height:8px;">

                <label id="btn_approve_client" class="switch" style="padding:0;margin:0;">
                  <input type="checkbox" name="approve_checkbox" id="approve_checkbox"
                       <c:if test="${client.getRole()!=0}">checked</c:if> data-toggle="toggle">
                  <div class="slider round"></div>
                </label>
              </td>
            </tbody>
          </table>

          <c:if test='${client.getRole()!=0}'>
            <script>$('#client_details').css('background', '#dddedf');</script>
          </c:if>
        </div>

        <script>  // btn_approve_client
        $(document).on("click", "#approve_checkbox", function () {
          // console.log("checkbox"" > "+$('#approve_checkbox').is(':checked'));
          $.get("<%=request.getContextPath()%>/bank/?command=admin&action=set_user_role"
            + "&content_type=ajax&user_id=${client.getId()}&role="
            + ( $('#approve_checkbox').is(':checked') ? "10" : "0"),
            function (resp) {
              if (resp === 'OK') {
                $('#tr_btn_appr').className = 'toggleon';
              } else if (resp === '0') {
                $('#tr_btn_appr').className = 'toggleoff';
              }
              recolorClientDetails();
            });
        });
        recolorClientDetails();
        function recolorClientDetails() {
          $('#client_details').css('background', $('#approve_checkbox').is(':checked') ?
            '#aaeecc' : '#dddedf');
        }
        </script>



        <%--Account--%>


        <div class="col-md-4">
          <table class="table table-striped table-hover table-sm table-info">
            <thead>
            <tr><th><fmt:message key="ACCT_NUMBER" bundle="${lang}"/></th></tr>
            </thead>
            <tbody>
              <tr><td title="id:${client.getAccount().getId()}">${client.getAccount().getName()}
              <c:choose>
              <c:when test='${client.getAccount().getBlocked()}'>
                <span id="account_status_badge">
                <span class="btn btn-deep-orange btn-sm" style="line-height:2px;padding:7px 4px;margin:0;">
                  <fmt:message key="BLOCKED" bundle="${lang}"/></span>

                <div class="btn-group" data-toggle="buttons">
                  <label class="btn btn-success btn-sm active" id="btn_unblock">
                    <input type="checkbox"  checked autocomplete="off"> <fmt:message
                      key="UNBLOCK" bundle="${lang}"/>
                  </label>
                </div>
                <span id="unblock_req_res"></span>

                <script>  // btn_unblock
                $(document).on("click", "#btn_unblock", function () {
                  $.get("<%=request.getContextPath()%>/bank/?command=admin&action=unblock_account"
                    + "&content_type=ajax&account_id=${client.getAccount().getId()}",
                    function (resp) {
                      if (resp == 'OK') {
                        $('#account_status_badge').html(
                          '<span class="btn btn-dark-green btn-sm" style="line-height:2px;' +
                          'padding:7px 4px;margin:0;"><fmt:message key="ACTIVE" bundle="${lang}"/></span>');
                      } else {
                        $('#unblock_req_res').html("Error");
                      }
                    });
                });
                </script>

              </c:when><%--/blocked account--%>
              <c:otherwise>
                <span class="btn btn-dark-green btn-sm" style="line-height:2px;padding:7px 4px;margin:0;">
                  <fmt:message key="ACTIVE" bundle="${lang}"/></span>
              </c:otherwise>
              </c:choose>
              </td></tr>

              <tr test="${not empty client.getAccount().getCards()}">
                <tr><th><fmt:message key="CONNECTED_CARDS" bundle="${lang}"/></th></tr>
                <c:forEach items="${client.getAccount().getCards()}" var="card">
                  <tr><td><small  title="id:${card.getId()}">&#x1f4b3;&nbsp;<c:out value="${card.getName()}"/> [<c:out value="${card.getExpDate()}"/>]</small></td></tr>
                </c:forEach>
                <tr><td><small><a href="<%=request.getContextPath()%>/bank/?command=admin&action=issue_new_card&account_id=${client.getAccount().getId()}&fee_id=${client.getFeeId()}&client_id=${client.getId()}"><fmt:message key="ISSUE_NEW_CARD" bundle="${lang}"/></a></small>
                </tr>


            </tbody>
          </table>


              <c:if test='${client.getAccount().getBlocked()}'>

        </div>
        </c:if>

            <small>
              <%--${client.getAccount()}</td>--%>
            </small>
      </div>

    </div>
    </c:if>



    </c:if>


    <%--</c:if>--%>


  </div>

  <div class="col-md-2"></div>
</div>
<%--/div.row--%>
</div> <!-- /container -->

<%@include file="includes/btmlinks.jspf" %>
<%@include file="includes/everypagefooter.jspf" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/bs3toggle.js"></script>

</body>
</html>

<%--//TODO:Formatting numbers -= which layer??--%>