package control.command;

import model.dao.exceptions.ExceptionDAO;
import model.entity.Client;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CommandManageAccount implements Command {
    Logger logger = Logger.getLogger(CommandManageAccount.class);
    String page = RB_PAGEMAP.getString("jsp.user");

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ExceptionDAO {
        String act = req.getParameter("action");
        HttpSession session = req.getSession();
        Boolean isAuth = (Boolean) session.getAttribute("isAuthorized");
        if(isAuth==null || isAuth==false){
            req.setAttribute("errormsg", "UNAUTHORIZED");
            return page;
        }
        req.setAttribute("command","show_authuser_hp"); // next page

        if(act==null) act="";
        Client client = (Client)session.getAttribute("client");

        Integer clientId = client.getId();
            logger.info("Client:"+client);

        switch (act){
            case ("order_new_account"):
                SERVICE.getUser().generateNewAccount(client.getId(), true);
                req.setAttribute("infomsg", "ORDER_SUBMITTED_WAIT_4_APPROVAL");

//                req.setAttribute("command","show_authuser_hp");

                req.setAttribute("accounts", SERVICE.getUser().getUserAccounts( clientId ));
//                page = RB_PAGEMAP.getString("jsp.user.main");
                //TODO: Change this to redirect!!! (avoid resending new acct request)
//                resp.sendRedirect("/bank/?command=show_authuser_hp");page="";
                return page;
//                break;

/*              try {
                    page="";
                    ControllerHelper.getInstance().getCommand(req).execute(req, resp);
                    page="";
                } catch (MySqlPoolException e) {
                    logger.error(e);
                }
*/
          case ("block"):
              String accountIdStr = req.getParameter("account_id");
//              req.setAttribute("command","show_authuser_hp");

              if(accountIdStr==null){
                wrongParam(req);
                return page;
              }
              if(SERVICE.getUser().blockAccount(Integer.parseInt(accountIdStr))){
                  req.setAttribute("infomsg", "RESULT_OK");
              } else {
                  req.setAttribute("errormsg", "RESULT_ERROR");
              }
              req.setAttribute("accounts", SERVICE.getUser().getUserAccounts( clientId ));
              break;

            default:
                req.setAttribute("errormsg", "WRONG_PARAM_REQUEST");
        }

        return page;
    }


    private void wrongParam(HttpServletRequest req){
        req.setAttribute("errormsg", "WRONG_PARAM_REQUEST");
    }


}

/*

tableName
tableHeadersArr
tableDataArr
*/
