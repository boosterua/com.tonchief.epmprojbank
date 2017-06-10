package control.command;


import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CommandEmpty implements Command {
    private final Logger logger = Logger.getLogger(CommandEmpty.class);


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String reqCommand = request.getParameter("command");
        HttpSession sess = request.getSession(false);

        if(reqCommand!=null && reqCommand.equals("switch_lang")){
            String lang = request.getParameter("locale");
            if(sess!=null)
                sess.setAttribute("locale", lang);

            setCookie(response, "locale", lang);
            String referer = request.getHeader("Referer");
            response.sendRedirect(referer);
            response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
            response.setHeader("Location", referer);
            return "";
        }

        /* for a new user without session, check lang cookie and set it in a new session */
        saveCookieToSession(request,"locale");
        //TODO setLang - stopped working when using cookies, move all lang changing stuff to filter?

        logger.debug("req " + request);
        logger.debug("sessIsAdmin(request) " + sessIsAdmin(request));
        logger.debug("isAuthUserSessionScope(request) " + isAuthUserSessionScope(request));
        if(sessIsAdmin(request)) {
            request.setAttribute("command", "admin");
            request.setAttribute("action", "index");
            return RB_PAGEMAP.getString("jsp.admin");
        } /*else if(isAuthUserSessionScope(request)){
            request.setAttribute("command", "show_authuser_hp");
            request.setAttribute("action", "index");
        }*/
        if(isAuthUserSessionScope(request))
            return RB_PAGEMAP.getString("jsp.user");

        return RB_PAGEMAP.getString("jsp.main");
    }
}
