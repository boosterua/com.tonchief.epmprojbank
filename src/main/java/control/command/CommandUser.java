package control.command;

import model.dao.exceptions.ExceptionDAO;
import model.dao.exceptions.MySqlPoolException;
import model.entity.Client;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ResourceBundle;
//TODO: split long method to custom methods

public class CommandUser implements Command {
    private static final Logger LOGGER = Logger.getLogger(CommandUser.class);
    static final ResourceBundle RB_BANK = ResourceBundle.getBundle("banksettings");

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ExceptionDAO, MySqlPoolException {
        String page = RB_PAGEMAP.getString("jsp.user.login");

        String login = req.getParameter("email");
        String password = req.getParameter("password");
        HttpSession session = req.getSession();

        Logger logger = Logger.getLogger(CommandUser.class);
        logger.info(login + ":" + password + ":" + SERVICE.getLogin().checkCredentials(login, password));
        if(session.getAttribute("locale")==null)
            saveCookieToSession(req, "locale");


        if (req.getParameter("command").equals("logout")) {
            session.invalidate();
            req.setAttribute("infomsg", "YOU_HAVE_LOGGED_OUT");
            req.setAttribute("action", "login");
        } else if(req.getParameter("command").equals("show_authuser_hp") ||
                req.getParameter("command").equals("client")){ //[Home] in top links
            Boolean isAuth=(Boolean)session.getAttribute("isAuthorized");
            // TODO - does this request accounts for logged in admin too??? If so - fix it immediately
            if(isAuth!=null && isAuth){
                Integer uid = ((Client)session.getAttribute("client")).getId();
                session.setAttribute("accountsMap", SERVICE.getUser().getUserAccountsAsMap(uid));
                page = RB_PAGEMAP.getString("jsp.user.main");
            } else {
                req.setAttribute("errormsg", "UNAUTHORIZED");
            }

        } else { // Check credentials
            Integer uid = SERVICE.getLogin().getUserIdOnAuth(login, password);
            if (uid!=null && uid>0) { // Authorized
                Client client =  SERVICE.getLogin().getClientById(uid);
                LOGGER.info(client);

                /* TODO: Should be removed when everything is tested and fixed */
                if(Boolean.TRUE.equals(Boolean.valueOf(RB_BANK.getString("APPLICATION_IS_IN_DEBUG_MODE"))));
                    session.setAttribute("SYSTEM_IN_DEBUG_STATE", true);

                session.setAttribute("isAuthorized", true);
                session.setAttribute("client", client);

                page="";
                if(RB_BANK.getString("ADMIN_ROLE").equals(client.getRole().toString()) ) {
                    LOGGER.info("User with admin privileges has just logged in.");
                    session.setAttribute("isAdmin", true);
                    resp.sendRedirect("/bank/?command=admin");
                } else {
//                    page = RB_PAGEMAP.getString("jsp.user.authorized");
//                    List<Account> accounts = SERVICE.getUser().getUserAccounts(uid);
//                    req.setAttribute("accounts", accounts);
                    resp.sendRedirect("/bank/?command=show_authuser_hp");
                }

            } else { // Authorization Failed
                if(uid!=null && uid==-500){
                    req.setAttribute("errormsg", "WRONG_LOGIN_PASS");

                } else if (!req.getMethod().equals("GET")) { // This is most definitely a hack attempt, or bot crawling
                    req.setAttribute("errormsg", "WRONG_LOGIN_PASS");
                    req.setAttribute("errorcode", 9401);
                }
                req.setAttribute("action", "login");
                    // page = RB_PAGEMAP.getString("jsp.user.login");
            }
        }
        return page;
    }
}
