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
import java.util.ResourceBundle;
//TODO: split long method to custom methods

public class CommandLogin implements Command {
    private static final Logger LOGGER = Logger.getLogger(CommandLogin.class);
    static final ResourceBundle RB_BANK = ResourceBundle.getBundle("banksettings");

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ExceptionDAO {
        String page = RB_PAGEMAP.getString("jsp.user.login");

        String login = req.getParameter("email");
        String password = req.getParameter("password");
        HttpSession session = req.getSession();

        Logger logger = Logger.getLogger(CommandLogin.class);
        logger.info(login + ":" + password + ":" + SERVICE.getLogin().checkCredentials(login, password));

        if (req.getParameter("command").equals("logout")) {
            session.invalidate();
            req.setAttribute("infomsg", "YOU_HAVE_LOGGED_OUT");
            req.setAttribute("action", "login");
        } else if(req.getParameter("command").equals("show_authuser_hp")){ //[Home] in top links
            Boolean isAuth=(Boolean)session.getAttribute("isAuthorized");
            if(isAuth!=null && isAuth){
                req.setAttribute("accounts", SERVICE.getUser().getUserAccounts( ((Client)session.getAttribute("client")).getId() ));
                page = RB_PAGEMAP.getString("jsp.user.main");
            } else {
                req.setAttribute("errormsg", "UNAUTHORIZED");
            }
        } else {
            Integer uid = SERVICE.getLogin().getUserIdOnAuth(login, password);
            if (uid!=null && uid>0) {
                Client client =  SERVICE.getLogin().getClientById(uid);
                LOGGER.info(client);
                List<Account> accounts = SERVICE.getUser().getUserAccounts(uid);
                req.setAttribute("accounts", accounts);
                session.setAttribute("isAuthorized", true);
                session.setAttribute("client", client);
                if( client.getRole().equals(RB_BANK.getString("ADMIN_ROLE")) ) {
                    session.setAttribute("isAdmin", true);
                    resp.sendRedirect("/bank/?command=admin");
                    page="";
//                    req.setAttribute("command", "admin");
//                    req.setAttribute("action", "index");
//                    page = RB_PAGEMAP.getString("jsp.admin");

                } else {
                    page = RB_PAGEMAP.getString("jsp.user.authorized");
                }

//                String referer = req.getHeader("Referer");
//                resp.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
//                resp.setHeader("Location", page);
//                req.setAttribute("imfomsg", "!!!");
//                req.setAttribute("command", "admin");
//                req.setAttribute("action", "get_one_client");



            } else {
                if (!req.getMethod().equals("GET")) {
                    req.setAttribute("errormsg", "WRONG_LOGIN_PASS");
                    req.setAttribute("errorcode", 9401);
                }
                req.setAttribute("action", "login");
//                page = RB_PAGEMAP.getString("jsp.user.login");
            }
        }
        return page;
    }
}
