package control.command;


import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CommandEmpty implements Command {
    private final Logger logger = Logger.getLogger(CommandEmpty.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String reqCommand = request.getParameter("command");

        if(reqCommand!=null && reqCommand.equals("switch_lang")){
            HttpSession sess = request.getSession();
            String lang = request.getParameter("lang");

            sess.setAttribute("locale", lang);
            Cookie cookie = new Cookie("locale", lang);
            cookie.setMaxAge(60*60*24*30);
            response.addCookie(cookie);

            String referer = request.getHeader("Referer");

            response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
            response.setHeader("Location", referer);
            request.setAttribute("imfomsg", request.getAttribute("javax.servlet.forward.request_uri"));
            return RB_PAGEMAP.getString("jsp.main");
        }


        /* for a new user without session, check lang cookie and set it in a new session */

        HttpSession sess = request.getSession();
        String cookieName = "lang";
        Cookie[] cookies = request.getCookies();
        if (cookies != null){
            for(Cookie c: cookies){
                if (cookieName.equals(c.getName()))
                    sess.setAttribute("locale", c.getValue());
            }
        }




        return RB_PAGEMAP.getString("jsp.main");
    }
}
