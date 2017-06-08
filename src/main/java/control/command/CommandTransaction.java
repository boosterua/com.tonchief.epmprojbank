package control.command;

import model.dao.exceptions.ExceptionDAO;
import model.entity.Client;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;

import static control.command.CommandManageAccount.accountBelongsToUser;

public class CommandTransaction implements Command {
    private static final Logger LOGGER = Logger.getLogger(CommandTransaction.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ExceptionDAO {
        String act = req.getParameter("action");
        HttpSession session = req.getSession();
        String page = RB_PAGEMAP.getString("jsp.user");

        Boolean isAuth = (Boolean) session.getAttribute("isAuthorized");
        if (isAuth == null || !isAuth) {
            req.setAttribute("errormsg", "UNAUTHORIZED");
            return page;
        }
        Long crAccount;
        Integer dtAccountId;
        BigDecimal trfAmount;

        try {
            LOGGER.info( req.getParameter("cr_account"));
            LOGGER.info( req.getParameter("account_id") + " - account_id");
            LOGGER.info( req.getParameter("trf_amount"));
            crAccount = Long.parseLong("" + req.getParameter("cr_account"));
            dtAccountId = (Integer.parseInt(req.getParameter("account_id")));
            String amt = (String)req.getParameter("trf_amount"); //TODO replace , to . - Rege
                amt = amt.replaceAll(",",".");
                amt = amt.replaceAll("[^\\.0-9]+","");
            trfAmount=new BigDecimal(amt);
        } catch (NumberFormatException nfe){
            req.setAttribute("errormsg", "WRONG_PARAM_REQUEST");
            LOGGER.warn("cr_account or dt_account - not a number or null", nfe);
            return page;
        }

        req.setAttribute("command", "show_authuser_hp"); // next page

        if (act == null) act = "";
        Client client = (Client) session.getAttribute("client");
        Integer clientId = client.getId();
        LOGGER.info("Client:" + client);

        switch (act) {
            case ("replenish"):

                break;
            case ("make_payment"):
                if (!accountBelongsToUser(req, dtAccountId)) {
                    req.setAttribute("errormsg", "WRONG_PARAM_REQUEST");
                    return page;
                }

//              SERVICE.getTransactions().makePayment(accountId, )

                req.setAttribute("infomsg", "to:"+crAccount + " : from:" + dtAccountId + " : Amount:" + trfAmount);
        }
        req.setAttribute("infomsg", "to:"+crAccount + " : from:" + dtAccountId + " : Amount:" + trfAmount);
        return page;
    }
}
