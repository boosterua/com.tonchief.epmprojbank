package control.command;

import model.dao.exceptions.ExceptionDAO;
import model.dao.exceptions.MySqlPoolException;
import service.SvcFactoryImpl;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ResourceBundle;


public interface Command {
    SvcFactoryImpl SERVICE = SvcFactoryImpl.getInstance();
    ResourceBundle RB_PAGEMAP = ResourceBundle.getBundle("webconfig.pagemapping");
    ResourceBundle RB_BANK = ResourceBundle.getBundle("systemsettings");
    //    ResourceBundle RB_LOCALE = ResourceBundle.getBundle("locale"); // All moved to jsps
    String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ExceptionDAO, MySqlPoolException;


    default void printData(HttpServletResponse resp, String ctype, String message) throws IOException, ServletException {
        if(message==null) throw new ServletException("NP as string passed to pagewriter.");
        resp.setContentType(ctype!=null && !ctype.equals("") ? ctype : "text/html");
        resp.setContentLength(message.length());
        PrintWriter writer = resp.getWriter();
        writer.print(message);
        writer.flush();
        writer.close();
    }

    default void saveCookieToSession(HttpServletRequest request, String name) {
        HttpSession sess = request.getSession();
        Cookie[] cookies = request.getCookies();
        if (cookies != null){
            for(Cookie c: cookies){
                if (!name.equals(c.getName()))
                    continue;
                sess.setAttribute(name, c.getValue());
                break;
            }
        }
    }

    default void setCookie(HttpServletResponse response, String name, String lang) {
        Cookie cookie = new Cookie(name, lang);
        cookie.setMaxAge(60*60*24*30);
        response.addCookie(cookie);
    }

    default String getRelativeReferer(HttpServletRequest request) {
        String referer = getReferer(request);
        String context = request.getContextPath();
        String[] uriParts = referer.split(context);
        return uriParts[1];
    }

    default String getReferer(HttpServletRequest request) {
        return request.getHeader("Referer");
    }

    default Boolean sessIsAdmin(HttpServletRequest request){
        return Boolean.TRUE.equals(request.getSession(false).getAttribute("isAdmin"));
    }

    default Boolean isAuthUserSessionScope(HttpServletRequest request){
        return Boolean.TRUE.equals(request.getSession(false).getAttribute("isAuthorized"));
    }

}
