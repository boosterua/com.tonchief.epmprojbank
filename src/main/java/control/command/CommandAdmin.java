package control.command;


import model.dao.exceptions.ExceptionDAO;
import model.dao.exceptions.MySqlPoolException;
import model.entity.Card;
import model.entity.Client;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class CommandAdmin implements Command {
    private static final Logger LOGGER = Logger.getLogger(CommandAdmin.class);
    private String page = "";


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ExceptionDAO, MySqlPoolException {
        LOGGER.info("servlet get Cmd/Param/Attr "+ req.getParameter("command") +":"+req.getParameter("action") + " : " + req.getAttribute("action"));
        String act = req.getParameter("action");  if (act == null) act = "";
        String uid = req.getParameter("user_id"); Integer clientId = uid==null? 0 : Integer.parseInt(uid);;
        String acctIdStr = req.getParameter("account_id"); Integer acctId = acctIdStr==null ? 0 : Integer.parseInt(acctIdStr);
        String feeIdStr = req.getParameter("fee_id"); Integer feeId = feeIdStr==null? 0 : Integer.parseInt(feeIdStr);
        List<Client> clients;
        String page = RB_PAGEMAP.getString("jsp.admin");

        HttpSession session = req.getSession();
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (isAdmin==null || !isAdmin) {
            unauthorized(req);
            LOGGER.info("Unauthorized admin call");
            return  page;
        }


        switch (act){
            case ("set_user_role"): // WORKS
                String roleStr = req.getParameter("role");
                LOGGER.info("clientID:"+clientId + " > setuserrole " + roleStr);
                setRole(req, resp, clientId, roleStr);
                LOGGER.debug("#set_user_role");
                break;
            case ("unblock_account"):
                setAccountBlock(req, resp, acctId, false);
                LOGGER.debug("#unblock_account");
                break;
            case ("issue_new_card"): // WORKS
                issueNewCard(req, resp, acctId, feeId, clientId);
                LOGGER.debug("#issue_new_card");
                break;
            case ("show_clients_with_blocked_accounts"):
                clients = SERVICE.getAdmin().getClientsWithBlockedAccts();
                req.setAttribute("clientList", clients);
                req.setAttribute("action", "show_clients_by_role");
                LOGGER.debug("#show_clients_with_blocked_accounts");
                break;
            case ("show_all_clients"):
                clients = SERVICE.getAdmin().getClientsAll();
                req.setAttribute("clientList", clients);
                req.setAttribute("action", "show_clients_by_role");
                LOGGER.debug("#show_all_clients.");
                break;
            case ("show_clients_by_role"):
                Long role = Long.parseLong(req.getParameter("role")); //if(role==null) role=0L;
                clients = SERVICE.getAdmin().getClientsByRole(role);
                req.setAttribute("clientList", clients);
                req.setAttribute("action", "show_clients_by_role");
                LOGGER.debug("#show_clients_by_role.");
                break;
            case ("get_one_client"):
                Client client = SERVICE.getAdmin().getClientDetailedById(clientId);
                req.setAttribute("action", "show_сlient_to_approve");
                req.setAttribute("client", client);
                LOGGER.debug("#get one client.");
                break;
            case (""):
                req.setAttribute("action", "index");
                break;
            default:
                LOGGER.debug("Wrong Param:");
                wrongParam(req);
        }
        return page;
    }

    private void setAccountBlock(HttpServletRequest req, HttpServletResponse resp, Integer accountId, boolean b) throws MySqlPoolException, ExceptionDAO, IOException, ServletException {
        if(accountId==0){wrongParam(req); return;}
        boolean res = SERVICE.getAdmin().removeAccountBlock(accountId);
        LOGGER.info(""+accountId+ " > unblocking acct " );
        req.setAttribute("result", res);
        req.setAttribute("infomsg", "RESULT_" + (res?"OK":"ERROR"));
        ifAjaxPrintOK(req, resp, "OK");
    }

    private void setRole(HttpServletRequest req, HttpServletResponse resp, Integer clientId, String roleStr ) throws ExceptionDAO, IOException, ServletException, MySqlPoolException {

        if(roleStr==null || clientId==0){
            LOGGER.info("client_id="+clientId );
            LOGGER.info("; roleStr="+roleStr);
            req.setAttribute("errormsg", "NO_USER_ID");
            wrongParam(req);
            return;
        }
        Integer currentRole = SERVICE.getAdmin().getClientById(clientId).getRole();
        LOGGER.info("Current role before change: " + currentRole);
        if((RB_BANK.getString("ADMIN_ROLE").equals(currentRole.toString())) ) {
            LOGGER.error("ROLE is Admin - Cannot change it. " + currentRole);
            req.setAttribute("errormsg", "You cannot disable administrator's account");
            return;
        }

        Long role = Long.parseLong(roleStr);
        boolean res = SERVICE.getAdmin().setRole(clientId, role);
        LOGGER.info(""+clientId + " > set.user.role " + roleStr);
        req.setAttribute("command", "admin");//
        req.setAttribute("action", "get"+"_one_client");
        req.setAttribute("result", res);
        req.setAttribute("infomsg", "RESULT_" + (res?"OK":"ERROR"));
        ifAjaxPrintOK(req, resp, role>0?"OK":"0");
        //  req.setAttribute("OUT", role>0?"OK":"0");
        //  page = RB_PAGEMAP.getString("jsp.ajax.out");
    }


    private void issueNewCard(HttpServletRequest req, HttpServletResponse resp, Integer accountId,
                              Integer feeId, Integer clientId) throws MySqlPoolException, ExceptionDAO, IOException,
            ServletException {
        if (accountId == 0) {
            wrongParam(req);
            return;
        }
        Card card = SERVICE.getAdmin().issueNewCard(accountId, feeId);
        LOGGER.info("Issue new card for Acct=" + accountId + " >  " + card);
        if (card == null) {
            req.setAttribute("errormsg", "CARD_NOT_ISSUED");
        } else {

            String referer = req.getHeader("Referer");
            resp.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
            resp.setHeader("Location", referer);

            req.setAttribute("imfomsg", "!!!");
            req.setAttribute("command", "admin");
            req.setAttribute("action", "get_one_client");
        }
        req.setAttribute("infomsg", "RESULT_" + (card != null ? "OK" : "ERROR"));
        ifAjaxPrintOK(req, resp, card != null ? "OK" : "ERROR");
    }

    private void wrongParam(HttpServletRequest req){
        req.setAttribute("errormsg", "WRONG_PARAM_REQUEST");
    }

    private void unauthorized(HttpServletRequest req){
        req.setAttribute("errormsg", "UNAUTHORIZED");
    }


    private boolean ifAjaxPrintOK(HttpServletRequest req, HttpServletResponse resp, String message) throws IOException, ServletException {
        if(req.getParameter("content_type")==null) //not ajax
        return false;
            printData(resp, "text/plain", message);

                        req.setAttribute("OUT", message);
                        page = RB_PAGEMAP.getString("jsp.ajax.out");
        return true;
    }
}
