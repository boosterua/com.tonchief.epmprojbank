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
import java.math.BigDecimal;

import static control.command.CommandManageAccount.accountBelongsToUser;
import static control.command.CommandManageAccount.getAccountFromSessById;

public class CommandTransaction implements Command {
    private static final Logger LOGGER = Logger.getLogger(CommandTransaction.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ExceptionDAO {
        String act = req.getParameter("action");
        HttpSession session = req.getSession();
        String page = RB_PAGEMAP.getString("jsp.user");

        if (!isAuthUserSessionScope(req)) {
            req.setAttribute("errormsg", "UNAUTHORIZED");
            return page;
        }

        String crAccount;
        Integer dtAccountId;
        BigDecimal trfAmount;
        String trfDescription;

        try {
            LOGGER.info( req.getParameter("account_id") + " - account_id");
            LOGGER.info( req.getParameter("cr_account"));
            LOGGER.info( req.getParameter("trf_amount"));
            LOGGER.info( req.getParameter("trf_description"));
                String crAcct = req.getParameter("cr_account");
            crAccount = crAcct==null ?"0" : crAcct;
            dtAccountId = (Integer.parseInt(req.getParameter("account_id")));
            String amt = (String)req.getParameter("trf_amount");
                amt = amt.replaceAll(",",".");
                amt = amt.replaceAll("[^\\.0-9]+","");
            trfAmount=new BigDecimal(amt);
            trfDescription = "" + req.getParameter("trf_description");
        } catch (NumberFormatException|NullPointerException nfe){
            req.setAttribute("errormsg", "WRONG_PARAM_REQUEST");
            LOGGER.warn("cr_account or dt_account - not a number or null", nfe);
            return page;
        }

        //req.setAttribute("command", "show_authuser_hp"); // next page

        if (act == null) act = "";
        Client client = (Client) session.getAttribute("client");
        Integer clientId = client.getId();
        LOGGER.info("Session.Client:" + client + "; action:" + act);


        LOGGER.debug(act+" : accountBelongsToUser(req, "+dtAccountId+")="+accountBelongsToUser(req, dtAccountId));
        if (!accountBelongsToUser(req, dtAccountId)) {
            req.setAttribute("errormsg", "WRONG_PARAM_REQUEST");
            return page;
        }
        Account dtAccount = getAccountFromSessById(req, dtAccountId);
        int trId=-1;

        switch (act) {
            case ("replenish"): // Same operation as transfer, except dt_account is user's acct, amount is negative
                try {
                    trId = SERVICE.getUser().replenishAccount(dtAccountId, trfAmount,
                        Integer.parseInt(RB_BANK.getString("CASH_ACCOUNT_ID")));
                } catch (ExceptionDAO e) {
                    LOGGER.error(e);
                }
                break;

            case ("make_payment"):
                trId = SERVICE.getUser().makePayment(dtAccount, crAccount, trfAmount, trfDescription);
                break;

            default:
                req.setAttribute("errormsg", "WRONG_PARAM_REQUEST");
                return page;

        }

        if(trId>0) {
            req.setAttribute("action", "transaction");
            req.setAttribute("dt_account_number", dtAccount.getName());
            req.setAttribute("transaction_id", trId);
            req.setAttribute("returnPage", "/bank/?command=show_authuser_hp");
            //req.setAttribute("infomsg", "to:"+ crAccount +" : from:"+ dtAccountId +" : Amount:"+ trfAmount +" OK. Tr.Id:"+ trId);
            //req.setAttribute("returnPage", req.getHeader("Referer"));

        } else {
            req.setAttribute("errormsg", trId == -1 ? "PROBLEM_WITH_DB" : "RESULT_ERROR");
            req.setAttribute("action", "form_transfer");
        }

        return page;
    }
}
