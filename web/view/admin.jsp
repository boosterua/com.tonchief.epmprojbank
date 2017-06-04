<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    .menubar { background: #8a1a00  !important; }
    .toggleon td {background: #00B833;}
    .toggleoff td {background: inherit;}

    .toggleon td label div {background: #99DD99 !important;}
    div.bgred {background: #DD9999 !important;}

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

        <%-- ######## SHOW CLIENTS BY ROLE #########--%>

        <c:if test="${action==\"show_clients_by_role\"}">
          <div class="panel-heading">
            <h3 class="panel-title badge indigo">&nbsp; <fmt:message key="NEW_CLIENTS_APPROVE_LIST" bundle="${lang}"/>&nbsp;</h3>
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
                  <td><a href="<%=request.getContextPath()%>/bank/?command=show_clients&action=get_one_client&id=${cl.getId()}">${cl.getId()}</a></td>
                  <td><nobr>${cl.getName()}</nobr></td>
                  <td>${cl.getEmail()}</td>
                  <td>${cl.getAccount().getName()}<c:if test="${cl.getAccount().getId()==0}">*</c:if><c:if test="${cl.getAccount().getBlocked()==true}"> <span class="btn btn-deep-orange btn-sm" style="line-height:2px;padding:7px 4px;margin:0;"><fmt:message key="BLOCKED" bundle="${lang}"/></span></td><td>${client.getFeeName()}</span></c:if></td>
                </tr>
              </c:forEach>
              </tbody>
            </table>
          </c:if>
          <c:if test="${clientList.size() == 0}">
            <div class="badge badge-warning">  <fmt:message key="NO_CLIENTS_PER_REQUEST" bundle="${lang}"/>.</div><br>
          </c:if>
        </c:if>
      <%--</c:if>--%>




       <%-- ######## showClientToApprove #########--%>

        <c:if test="${action==\"show_сlient_to_approve\"}">

<%--
        <script>

            function js_unblock_account(accId){
                $.ajax({
                    type: "post",
                    url: "<%=request.getContextPath()%>/bank/?command=admin&action=get_account_status&content-type=plaintext",
                    data: "id="+accId,
                    success: function(msg){ $('#account_status_badge').append(msg);}});
            }


            /*$(document).on("click","#btn_unblock", function(){
                $.get("<%=request.getContextPath()%>/bank/?command=admin&action=approve_user&user_id=${client.getId()}", function (resp){
                    $("#account_status_badge").text=resp;
                });
            });*/

        </script>

--%>


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

            <div class="row">
              <div class="col-md-8">
              <table class="table table-striped table-hover table-sm table-info" style="background:#dddedf;<c:if test='${client.getRole()!=0}'>
              background:#00DDAA !important</c:if>" id="client_details" >
                <tbody>
                  <tr><td><fmt:message key="ID" bundle="${lang}"/></td><td>${client.getId()}</td>
                  <tr><td><fmt:message key="NAME" bundle="${lang}"/></td><td>${client.getName()}</td>
                  <tr><td><fmt:message key="EMAIL" bundle="${lang}"/></td><td>${client.getEmail()}</td>
                  <tr><td><fmt:message key="FEE_CARD_NAME" bundle="${lang}"/></td><td>${client.getFeeName()}</td>
                  <tr><td><fmt:message key="ROLE_CODE" bundle="${lang}"/></td><td>${client.getRole()}</td>
                  <tr id="tr_btn_appr"><td><fmt:message key="APPROVE" bundle="${lang}"/></td><td style="line-height:8px;">
                    <label id="btn_approve_client" class="switch" style="padding:0;margin:0;">




                      <input type="checkbox" name="approve_checkbox" id="approve_checkbox" <c:if test="${client.getRole()!=0}">checked</c:if> data-toggle="toggle">



                      <div class="slider round <c:if test='${client.getRole()==0}'>bgred</c:if>"></div>
                    </label>
                    </td>
                </tbody>
              </table>
              <c:if test='${client.getRole()!=0}'>
                  <script>$('#client_details').css('background', '#dddedf');</script>
              </c:if>
              </div>

              <script>  // btn_approve_client
                  $(document).on("click","#approve_checkbox", function(){
                      console.log("checkbox"+$('#approve_checkbox') + " > "+$('#approve_checkbox').is(':checked'));
                      $.get("<%=request.getContextPath()%>/bank/?command=admin&action=set_user_role"
                          +"&content_type=ajax&user_id=${client.getId()}&role="
                          +( $('#approve_checkbox').is(':checked')?"10":"0"),
                          function (resp){
                            if(resp=='OK'){
                                $('#tr_btn_appr').className='toggleon';
                                $('#client_details').css('background', '#aaeecc');
                            } else if(resp=='0'){
                                $('#tr_btn_appr').className='toggleoff';
                                $('#client_details').css('background', '#dddedf');
                            }
                      });
                  });
              </script>











              <%--Account--%>



              <div class="col-md-4">
                <table class="table table-striped table-hover table-sm table-info">
                  <thead>
                    <tr><th><fmt:message key="ACCT_NUMBER" bundle="${lang}"/></th>
                  </thead>
                  <tbody>
                  <tr><td>${client.getAccount().getName()}



                    <span id="account_status_badge">
                    <c:choose>
                      <c:when test='${client.getAccount().getBlocked()}'>
                        <span class="btn btn-deep-orange btn-sm" style="line-height:2px;padding:7px 4px;margin:0;"><fmt:message key="BLOCKED" bundle="${lang}"/></td><td>
                      <%--${client.getFeeName()}--%>








                    <script>
                      $(document).on("click","#btn_unblock", function(){
                        $.get("<%=request.getContextPath()%>/bank/?command=admin&action=approve_user&user_id=${client.getId()}", function (resp){
                        $("#account_status_badge").text=(resp=="OK")?"":"";
                        });
                      });
                    </script>












                   <%--
                      <input type="hidden" name="command" value="admin">
                      <input type="hidden" name="action" value="approve_user">
                      <input type="hidden" name="user_id" value="${client.getId()}">
                    </form>--%>


                        <a href="<%=request.getContextPath()%>/bank/?command=admin&action=unblock_account&id=${client.getAccount().getId()}" onclick="js_unblock_account('${client.getAccount().getId()}');">**
                      <div class="btn-group" data-toggle="buttons" id="btn_unblock">
                          <label class="btn btn-success btn-sm active">
                            <input type="checkbox" checked autocomplete="off"> Unblock
                          </label>
                        </div>
                    </a>
                      </c:when>
                    <c:otherwise>
                    <span class="btn btn-dark-green btn-sm" style="line-height:2px;padding:7px 4px;margin:0;">ACTIVE</span>
                    </c:otherwise>
                    </c:choose>
                    </span>

                      <c:if test='${client.getAccount().getBlocked()}'>

                      </div></c:if>






                  </td>
                  <tr><td><small>${client.getAccount()}</td>
                  </tr>
                  </tbody>
                </table>

              </div>

            </div>
          </c:if>
          <a href="<%=request.getContextPath()%>/bank/?command=admin&action=approve_user&user_id=${client.getId()}">Approve.User</a> |
          <a href="<%=request.getContextPath()%>/bank/?command=admin&action=unblock_user_account&user_id=${client.getId()}">unblock_user_account</a>
          <a href="<%=request.getContextPath()%>/bank/?command=admin&action=issue_new_card&user_id=${client.getId()}">issue_new_card</a>




        </c:if>



      <%--</c:if>--%>












    </div>

    <div class="col-md-2"></div>
  </div>  <%--/div.row--%>
</div> <!-- /container -->

<%@include file="includes/btmlinks.jspf" %>
<%@include file="includes/everypagefooter.jspf" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/bs3toggle.js"></script>

  </body>
</html>

<%--//TODO:Formatting numbers -= which layer??--%>