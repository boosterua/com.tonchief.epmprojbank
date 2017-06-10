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
    <div class="col-md-1 verticaltext">
      <h1 class="verticaltext_content display-4" style="text-shadow: 3px 3px 6px #CCC; color:#FFF9F9;">admin&nbsp;module</h1>
    </div>

    <div class="col-md-10 container-fluid" >      <%--style="border:2px solid red;"--%>

      ${errormsg_html}
      <c:if test="${not empty errormsg}">
        <div class="alert alert-danger" role="alert"><fmt:message key="${errormsg}" bundle="${lang}"/></div>
      </c:if>

      ${infomsg_html}
      <c:if test="${not empty infomsg}">
        <span class="badge badge-success"><fmt:message key="${infomsg}" bundle="${lang}"/></span>
      </c:if>


      <c:if test="${sessionScope.isAdmin==true}">
        <%--<c:if test="${isAuthorized==true}">--%>

      <c:if test="${action==\"index\"}">
        <br>
        <a href="<%=request.getContextPath()%>/bank/?command=admin&action=show_clients_by_role&role=0">
        <button type="button" class="btn btn-sm btn-default teal darker waves-effect waves-light" aria-label="Left Align">
          <span class="glyphicon glyphicon-align-left" aria-hidden="true"></span>
          <i class="fa-li fa fa-check-square"></i><fmt:message key="LNK_SHOW_NEW_CLIENTS"></fmt:message>
        </button>
        </a><br>

        <a href="<%=request.getContextPath()%>/bank/?command=admin&action=show_clients_with_blocked_accounts">
          <button type="button" class="btn btn-sm  btn-default  teal darker waves-effect waves-light" aria-label="Left Align">
          <span class="glyphicon glyphicon-align-left" aria-hidden="true"></span>
          <i class="fa-li fa fa-check-square"></i><fmt:message key="LNK_SHOW_BLOCKED_CLIENTS"></fmt:message>
        </button></a><br>

        <a href="<%=request.getContextPath()%>/bank/?command=admin&action=show_all_clients">
          <button type="button" class="btn btn-sm  btn-default teal darker waves-effect waves-light" aria-label="Left Align">
          <span class="glyphicon glyphicon-align-left" aria-hidden="true"></span>
          <i class="fa-li fa fa-check-square"></i><fmt:message key="LNK_SHOW_ALL_CLIENTS"></fmt:message>
        </button></a><br>

     <br>

      </c:if>


        <%-- ######## SHOW CLIENTS BY ROLE #########--%>

      <c:if test="${action==\"show_clients_by_role\"}">
        <div class="panel-heading">
          <h3 class="panel-title badge indigo">&nbsp; <fmt:message key="NEW_CLIENTS_APPROVE_LIST" bundle="${lang}"/>&nbsp;</h3>
        </div>
        <c:if test="${clientList.size() > 0}">
          <table class="table table-striped table-hover table-sm table-info z-depth-3" style="font-size: smaller" >
            <thead class="teal darken-3 text-white">
            <tr class="text-center">
              <th><fmt:message key="ID" bundle="${lang}"/></th>
              <th><fmt:message key="NAME" bundle="${lang}"/></th>
              <th><fmt:message key="EMAIL" bundle="${lang}"/></th>
              <th><fmt:message key="ACCOUNT_ID" bundle="${lang}"/></th>
              <th><fmt:message key="FEE_CARD_NAME" bundle="${lang}"/></th>
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
                <td class="text-left"><nobr>${cl.getAccount().getName()}
                  <c:if test="${cl.getAccount().getId()==0}">*</c:if>
                  <c:if test="${cl.getAccount().getBlocked()==true}">&nbsp;<span class="btn btn-deep-orange btn-sm"
                      style="line-height:2px;padding:7px 4px;margin:0;"><fmt:message key="BLOCKED" bundle="${lang}"/></span></nobr>
                  </c:if>
                  <c:if test="${cl.getAccount().getBlocked()==false}">
                    <span class="btn btn-dark-green btn-sm" style="line-height:2px;padding:7px 4px;margin:0;">
                      <fmt:message key="ACTIVE" bundle="${lang}"/>
                    </span></nobr>
                  </c:if>
                </td>
                <td>${cl.getFeeName()}</td>
              </tr>
              <%--<tr><td colspan="4">${cl}</td></tr>--%>
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
            <c:when test="${client.getRole()==0}"> <fmt:message key="NEW_CLIENT_TO_APPROVE" bundle="${lang}"/></c:when>
            <c:otherwise>
              <fmt:message key="CLIENT_DETAILS" bundle="${lang}"/>
            </c:otherwise>
          </c:choose>
          &nbsp;</h3>
      </div>



      <c:if test="${not empty client}">
      <div class="row-fluid">
        <div class="col-md-4 pull-left" style="width2:auto;">
          <table class="table table-striped table-hover table-sm table-info z-depth-3 text-sm"
               style="font-size:smaller;background:#dddedf;<c:if test='${client.getRole()!=0}'>
                   background:#00DDAA !important</c:if>" id="client_details">
            <tbody>
            <tr>
              <th><fmt:message key="ID" bundle="${lang}"/></th>
              <td>${client.getId()}</td>
            <tr>
              <th><fmt:message key="NAME" bundle="${lang}"/></th>
              <td>${client.getName()}</td>
            <tr>
              <th><fmt:message key="EMAIL" bundle="${lang}"/></th>
              <td>${client.getEmail()}</td>
            <tr>
              <th><fmt:message key="FEE_CARD_NAME" bundle="${lang}"/></th>
              <td>${client.getFeeName()}</td>
            <tr>
              <th><fmt:message key="ROLE_CODE" bundle="${lang}"/></th>
              <td>${client.getRole()}</td>
            <tr id="tr_btn_appr">
              <th><fmt:message key="APPROVE" bundle="${lang}"/></th>
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




          <%--Accounts--%>

        <div class="col-md-8 pull-right" >
        <span class="row-fluid">
        <c:forEach var="account" items="${client.getAccountList()}">
          <div class="col-md-5 pull-left">
          <table class="table table-sm z-depth-2 Ttable-striped table-bordered table-hover table-info"
                 style="font-size: smaller">
            <thead>
            <tr>
              <th><fmt:message key="ACCT_NUMBER" bundle="${lang}"/></th>
            </tr>
            </thead>
            <tbody>
            <tr>
              <td title="id:${account.getId()}">${account.getName()}
                <c:choose>
                <c:when test='${account.getBlocked()}'>
                <span id="account_status_badge_${account.getId()}">
            <span class="btn btn-deep-orange btn-sm" style="line-height:2px;padding:7px 4px;margin:0;">
            <fmt:message key="BLOCKED" bundle="${lang}"/></span>

            <div class="btn-group" data-toggle="buttons">
            <label class="btn btn-success btn-sm active" id="btn_unblock_${account.getId()}">
              <input type="checkbox" checked autocomplete="off"> <fmt:message
                key="UNBLOCK" bundle="${lang}"/>
            </label>
            </div>
            <span id="unblock_req_res"></span>

            <script>  // btn_unblock
            $(document).on("click", "#btn_unblock_${account.getId()}", function () {
              $.get("<%=request.getContextPath()%>/bank/?command=admin&action=unblock_account"
                + "&content_type=ajax&account_id=${account.getId()}",
                function (resp) {
                  if (resp == 'OK') {
                    $('#account_status_badge_${account.getId()}').html(
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
              </td>
            </tr>

            <tr test="${not empty account.getCards()}">
            <tr>
              <th><fmt:message key="CONNECTED_CARDS" /></th>
            </tr>
            <c:forEach items="${account.getCards()}" var="card">
              <tr>
                <td>
                  <small title="id:${card.getId()}">&#x1f4b3;&nbsp;<c:out value="${card.getName()}"/>
                    [<c:out value="${card.getExpDate()}"/>]
                  </small>
                </td>
              </tr>
            </c:forEach>
            <tr>
              <td>
                <small><a
                    href="<%=request.getContextPath()%>/bank/?command=admin&action=issue_new_card&account_id=${account.getId()}&fee_id=${client.getFeeId()}&client_id=${client.getId()}"><fmt:message
                    key="ISSUE_NEW_CARD" bundle="${lang}"/></a></small>
            </tr>


              </tbody>
            </table>
          </div>
        </c:forEach>
        </span>
        </div>

        <small>
            <%--${client.getAccount()}</td>--%>
        </small>
      </div>
    </div>
      </c:if><%--not empty client--%>
    </c:if><%--show_сlient_to_approve--%>
    </c:if><%--sessionScope.isAdmin==true--%>


  </div>

  <div class="col-md-1"></div>
</div>
<%--/div.row--%>
</div> <!-- /container -->

<%@include file="includes/btmlinks.jspf" %>
<%@include file="includes/everypagefooter.jspf" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/bs3toggle.js"></script>

</body>
</html>

<%--//TODO:Formatting numbers -= which layer??--%>