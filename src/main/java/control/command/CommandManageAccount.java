package control.command;

import model.dao.exceptions.ExceptionDAO;
import model.dao.exceptions.MySqlPoolException;
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
    private static final Logger LOGGER = Logger.getLogger(CommandManageAccount.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ExceptionDAO, MySqlPoolException {
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
        LOGGER.info("Client:" + client);
        String accIdStr = req.getParameter("account_id");
        Integer accountId = ( accIdStr==null || accIdStr.equals("") ) ? 0 : Integer.parseInt(accIdStr);
        switch (act) {
            case ("order_new_account"):
                SERVICE.getUser().generateNewAccount(client.getId(), true);
                session.setAttribute("infomsg", "ORDER_SUBMITTED_WAIT_4_APPROVAL");
                resp.sendRedirect("/bank/?command=show_authuser_hp");
                page = "";
                break;

            case ("block"):
                if(accountBelongsToUser(req, accountId)){
                    if (SERVICE.getUser().blockAccount(accountId))
                        req.setAttribute("infomsg", "RESULT_OK");
                    else req.setAttribute("errormsg", "RESULT_ERROR");
                    session.setAttribute("accountsMap", SERVICE.getUser().getUserAccountsAsMap(clientId));
                }
                break;

            case ("replenish"): /* print_form only; actual transaction is executed via CommandTransactions */
            case ("make_payment"): /* both cases r equal - just pass param through  */
                if(accountBelongsToUser(req, accountId)){
                    req.setAttribute("action", "form_transfer");
                    req.setAttribute("type", act);
                    LOGGER.info("make_paym/repl... action=" + act);
                    if(act.equals("replenish")) {
                        req.setAttribute("cr_account", getAccountByIdSessionScope(req, accountId).getName());
                        req.setAttribute("account_id", "000000"+RB_BANK.getString("CASH_ACCOUNT_ID"));
                        req.setAttribute("description", "Cash ATM top-up / Поповнення готівкою через термінал");
                        LOGGER.info("replenish: setting extra attrs. " + req.getAttribute("cr_account") + " <CRAcct | AcctId> "+req.getAttribute("account_id") );
                    }
                }
                break;

            case ("list_transactions"):
                LOGGER.debug("list_transactions");
                if(accountBelongsToUser(req, accountId)){
                    LOGGER.debug("isAuth=true");
                    req.setAttribute("action", act);
                    LOGGER.info("list_transactions... action=" + act);
                    req.setAttribute("transactionsList", SERVICE.getUser().getTransactionsList(accountId,true));
                    req.setAttribute("transactionsListCredit", SERVICE.getUser().getTransactionsList(accountId,false));
                }
                break;

            default:
                req.setAttribute("errormsg", "WRONG_PARAM_REQUEST");
        }
        return page;

    }

    static boolean accountBelongsToUser(HttpServletRequest req, Integer accountId){
        Account account = getAccountByIdSessionScope(req, accountId);
        if(account==null) {
            wrongParam(req);
            return false;
        } else if(account.getBlocked() ||
                ((Client)req.getSession().getAttribute("client")).getRole().equals(0)){
            req.setAttribute("errormsg", "OPER_ON_BLOCKED_ACCOUNT_FORBIDDEN");
            return false;
        }
        return true;
    }

    static Account getAccountByIdSessionScope(HttpServletRequest req, int accountId){
        Map<Integer,Account> accountsMap =
                (Map<Integer, Account>) req.getSession().getAttribute("accountsMap");
        return accountsMap.get(accountId);
    }
//    static String getClientRoleFromSess(HttpServletRequest req){
//        Map<Integer,Client> clientsMap =
//                (Map<Integer, String>) req.getSession().getAttribute("client");
//        return clientsMap.get("role");
//    }


    private static void wrongParam(HttpServletRequest req) {
        req.setAttribute("errormsg", "WRONG_PARAM_REQUEST");
    }

}

/*

tableName
tableHeadersArr
tableDataArr
*/
