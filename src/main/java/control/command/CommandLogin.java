package control.command;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CommandLogin implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page = null;
        String login = req.getParameter("email");
        String password = req.getParameter("password");
        HttpSession session = req.getSession();

        Logger logger = Logger.getLogger(CommandLogin.class);
        logger.info(login + ":" + password + ":" + SERVICE.getLogin().checkCredentials(login, password));

        if (req.getParameter("command").equals("logout")) {
            session.invalidate();
            req.setAttribute("infomsg", "YOU_HAVE_LOGGED_OUT");
            page = RB_PAGEMAP.getString("jsp.user.main");
        } else {

            if (SERVICE.getLogin().checkCredentials(login, password)) {

                req.setAttribute("isAuthorized", true);
                session.setAttribute("isAuthorized", true);
                page = RB_PAGEMAP.getString("jsp.user.authorized");

            } else {

                if (!req.getMethod().equals("GET")) {
                    req.setAttribute("errormsg", "Wrong login  or password ");
                    req.setAttribute("errorcode", 9401);
                }
                req.setAttribute("action", "login");
                page = RB_PAGEMAP.getString("jsp.user.login");
            }
        }
        return page;
    }
}
