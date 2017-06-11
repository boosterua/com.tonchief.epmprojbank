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
    private boolean customErrorAlreadySet = false;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException,
            ExceptionDAO, MySqlPoolException {
        customErrorAlreadySet = false;
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
        Integer accountId = (accIdStr == null || accIdStr.equals("")) ? 0 : Integer.parseInt(accIdStr);
        switch (act) {
            case ("order_new_account"):
                SERVICE.getUser().generateNewAccount(client.getId(), true);
                session.setAttribute("infomsg", "ORDER_SUBMITTED_WAIT_4_APPROVAL");
                resp.sendRedirect("/bank/?command=show_authuser_hp");
                page = "";
                break;

            case ("block"):
                if (CommandUtils.accountBelongsToUser(req, accountId, LOGGER)) {
                    if (SERVICE.getUser().blockAccount(accountId)) req.setAttribute("infomsg", "RESULT_OK");
                    else req.setAttribute("errormsg", "RESULT_ERROR");
                    session.setAttribute("accountsMap", SERVICE.getUser().getUserAccountsAsMap(clientId));
                }
                break;

            case ("replenish"): /* print_form only; actual transaction is executed via CommandTransactions */
            case ("make_payment"): /* both cases r equal - just pass param through  */
                if (CommandUtils.accountBelongsToUser(req, accountId, LOGGER)) {
                    req.setAttribute("action", "form_transfer");
                    req.setAttribute("type", act);
                    LOGGER.info("make_paym/repl... action=" + act);
                    if (act.equals("replenish")) {
                        req.setAttribute("cr_account", getAccountByIdSessionScope(req, accountId).getName());
                        req.setAttribute("account_id", "000000" + RB_BANK.getString("CASH_ACCOUNT_ID"));
                        req.setAttribute("description", "Cash ATM top-up / Поповнення готівкою через термінал");
                        LOGGER.info("replenish: setting extra attrs. " + req.getAttribute("cr_account") + " <CRAcct |" +
                                " AcctId> " + req.getAttribute("account_id"));
                    }
                }
                break;

            case ("list_transactions"):
                LOGGER.debug("list_transactions");
                if (!CommandUtils.accountBelongsToUser(req, accountId, LOGGER))
                    break;
                LOGGER.debug("isAuth=true");
                req.setAttribute("action", act);
                LOGGER.info("list_transactions... action=" + act);
                req.setAttribute("transactionsList", SERVICE.getUser().getTransactionsList(accountId, true));
                req.setAttribute("transactionsListCredit", SERVICE.getUser().getTransactionsList(accountId, false));
                break;

            default:
                if (!customErrorAlreadySet) req.setAttribute("errormsg", "WRONG_PARAM_REQUEST");
        }
        return page;

    }


    static Account getAccountByIdSessionScope(HttpServletRequest req, int accountId) {
        Map<Integer, Account> accountsMap = (Map<Integer, Account>) req.getSession().getAttribute("accountsMap");
        return accountsMap.get(accountId);
    }

    private static void wrongParam(HttpServletRequest req) {
        req.setAttribute("errormsg", "WRONG_PARAM_REQUEST");
    }

}
