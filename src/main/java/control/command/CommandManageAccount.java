package control.command;

import model.dao.exceptions.ExceptionDAO;
import model.entity.Account;
import model.entity.Client;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

public class CommandManageAccount implements Command {
    Logger logger = Logger.getLogger(CommandManageAccount.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException,
            ExceptionDAO {
        String act = req.getParameter("action");
        HttpSession session = req.getSession();
        String page = RB_PAGEMAP.getString("jsp.user");

        Boolean isAuth = (Boolean) session.getAttribute("isAuthorized");

        if (isAuth == null || !isAuth) {
            req.setAttribute("errormsg", "UNAUTHORIZED");
            return page;
        }
        req.setAttribute("command", "show_authuser_hp"); // next page

        if (act == null) act = "";
        Client client = (Client) session.getAttribute("client");
        Integer clientId = client.getId();
        logger.info("Client:" + client);
        String accId = req.getParameter("account_id");
        Integer accountId = ( accId==null || accId.equals("") ) ? 0 : Integer.parseInt(accId);

        switch (act) {
            case ("order_new_account"):
                SERVICE.getUser().generateNewAccount(client.getId(), true);
                session.setAttribute("infomsg", "ORDER_SUBMITTED_WAIT_4_APPROVAL");
//                req.setAttribute("infomsg", "ORDER_SUBMITTED_WAIT_4_APPROVAL");
//                req.setAttribute("command","show_authuser_hp");
//                req.setAttribute("accounts", SERVICE.getUser().getUserAccounts( clientId ));
                resp.sendRedirect("/bank/?command=show_authuser_hp");
                page = "";
                break;

            case ("block"):
                if(accountBelongsToUser(req, accountId)){
                    if (SERVICE.getUser().blockAccount(accountId))
                        req.setAttribute("infomsg", "RESULT_OK");
                    else req.setAttribute("errormsg", "RESULT_ERROR");
                    session.setAttribute("accountsMap", SERVICE.getUser().getUserAccountsAsMap(clientId));
                    // req.setAttribute("accounts", SERVICE.getUser().getUserAccounts(clientId));
                }
                break;

            case ("replenish"): /* print_form only; actual transaction is executed via CommandTransactions */
            case ("make_payment"): /* both cases r equal - just pass param through  */
                if(accountBelongsToUser(req, accountId)){
                    req.setAttribute("action", "form_transfer");
                    req.setAttribute("type", act);
                }
                break;

            default:
                req.setAttribute("errormsg", "WRONG_PARAM_REQUEST");
        }
        return page;

    }

    static boolean accountBelongsToUser(HttpServletRequest req, Integer accountId){
        Map<Integer,Account> accountsMap =
                (Map<Integer, Account>) req.getSession().getAttribute("accountsMap");
        if(accountsMap.get(accountId)==null) {
            req.setAttribute("errormsg", "WRONG_PARAM_REQUEST");
            return false;
        } else if(accountsMap.get(accountId).getBlocked()){
            req.setAttribute("errormsg", "OPER_ON_BLOCKED_ACCOUNT_FORBIDDEN");
            return false;
        }
        return true;
    }

//    private checkAcctBelongsToUser(HttpSession session)


    private void wrongParam(HttpServletRequest req) {
        req.setAttribute("errormsg", "WRONG_PARAM_REQUEST");
    }


}

/*

tableName
tableHeadersArr
tableDataArr
*/
