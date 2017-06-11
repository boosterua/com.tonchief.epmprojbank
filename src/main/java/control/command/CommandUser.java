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

import static control.command.CommandUtils.md5Hash;
//TODO: split long method to custom methods

public class CommandUser implements Command {
    private static final Logger LOGGER = Logger.getLogger(CommandUser.class);
    static final ResourceBundle RB_BANK = ResourceBundle.getBundle("systemsettings");

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException,
            ExceptionDAO, MySqlPoolException {
        String page = RB_PAGEMAP.getString("jsp.user");

        String login = req.getParameter("email");
        String passwordHash = md5Hash( req.getParameter("password") );
        HttpSession session = req.getSession();

        LOGGER.info(login + ":" + passwordHash + ":" + SERVICE.getLogin().checkCredentials(login, passwordHash));
        if (session.getAttribute("locale") == null) saveCookieToSession(req, "locale");

        switch (req.getParameter("command")) {
            case "logout":
                req.setAttribute("locale", session.getAttribute("locale"));
                session.invalidate();
                req.setAttribute("infomsg", "YOU_HAVE_LOGGED_OUT");
                req.setAttribute("action", "login");
                break;
            case "client": // intensional (cmds're equal)
            case "show_authuser_hp":
                Boolean isAuth = (Boolean) session.getAttribute("isAuthorized"); // TODO - does this request accounts for
                if (isAuth != null && isAuth) {
                    Integer uid = ((Client) session.getAttribute("client")).getId();
                    session.setAttribute("accountsMap", SERVICE.getUser().getUserAccountsAsMap(uid));
                    page = RB_PAGEMAP.getString("jsp.user");
                } else {
                    req.setAttribute("errormsg", "UNAUTHORIZED");
                }
                break;

            default:
                // Check credentials
                Integer uid = SERVICE.getLogin().getUserIdOnAuth(login, passwordHash);
                if (uid != null && uid > 0) { // Authorized
                    Client client = SERVICE.getLogin().getClientById(uid);
                    CommandUser.LOGGER.info("[" + req.getRemoteAddr() + "] Client: " + client);
                        /* TODO: Should be removed when everything is tested and fixed */
                        /*if(RB_BANK.getString("APPLICATION_IS_IN_DEBUG_MODE").equals(1));
                            session.setAttribute("SYSTEM_IN_DEBUG_STATE", true);*/
                    session.setAttribute("isAuthorized", true);
                    session.setAttribute("client", client);
                    page = "";

                    // check for Admin credentials (999)
                    if (RB_BANK.getString("ADMIN_ROLE").equals(client.getRole().toString())) {
                        CommandUser.LOGGER.info("User with admin privileges has just logged in.");
                        session.setAttribute("isAdmin", true);
                        resp.sendRedirect("/bank/?command=admin");
                    } else {
                        resp.sendRedirect("/bank/?command=show_authuser_hp");
                    }

                } else { // Authorization Failed
                    if (uid != null && uid.equals(-500)) {
                        req.setAttribute("errormsg", "WRONG_LOGIN_PASS");

                    } else if (!req.getMethod().equals("GET")) { // This is most definitely a hack attempt, or bot crawling
                        req.setAttribute("errormsg", "WRONG_LOGIN_PASS");
                        req.setAttribute("errorcode", 9401);
                    }
                    req.setAttribute("action", "login");
                }

        }
        return page;
    }
}
