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
import java.util.List;

public class CommandLogin implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ExceptionDAO {
        String page = RB_PAGEMAP.getString("jsp.user.login");

        String login = req.getParameter("email");
        String password = req.getParameter("password");
        HttpSession session = req.getSession();

        Logger logger = Logger.getLogger(CommandLogin.class);
        logger.info(login + ":" + password + ":" + SERVICE.getLogin().checkCredentials(login, password));

        //TODO: split long method to custom methods

        if (req.getParameter("command").equals("logout")) {
            session.invalidate();
            req.setAttribute("infomsg", "YOU_HAVE_LOGGED_OUT");
            req.setAttribute("action", "login");
            page = RB_PAGEMAP.getString("jsp.user.main");
            page = RB_PAGEMAP.getString("jsp.user.login");

        } else if(req.getParameter("command").equals("show_authuser_hp")){
            Boolean isAuth=(Boolean)session.getAttribute("isAuthorized");
            if(isAuth!=null && isAuth){
                List<Account> accounts = SERVICE.getUser().getUserAccounts(
                        ((Client)session.getAttribute("client")).getId());
                req.setAttribute("accounts", accounts);
                page = RB_PAGEMAP.getString("jsp.user.main");
            } else {
                req.setAttribute("errormsg", "UNAUTHORIZED");
            }

        } else {

            Integer uid = SERVICE.getLogin().getUserIdOnAuth(login, password);
            if (uid!=null && uid>0) {
                Client client =  SERVICE.getLogin().getClientById(uid);
                List<Account> accounts = SERVICE.getUser().getUserAccounts(uid);
                req.setAttribute("accounts", accounts);
                //req.setAttribute("isAuthorized", true);
                session.setAttribute("isAuthorized", true);
                session.setAttribute("client", client);

                page = RB_PAGEMAP.getString("jsp.user.authorized");

            } else {

                if (!req.getMethod().equals("GET")) {
                    req.setAttribute("errormsg", "WRONG_LOGIN_PASS");
                    req.setAttribute("errorcode", 9401);
                }
                req.setAttribute("action", "login");
                page = RB_PAGEMAP.getString("jsp.user.login");
            }
        }
        return page;
    }
}
