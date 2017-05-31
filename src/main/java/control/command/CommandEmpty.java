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
        if(reqCommand!=null && reqCommand.equals("switch_lang")){
            HttpSession sess = request.getSession();
            sess.setAttribute("locale", request.getParameter("lang"));

            String referer = request.getHeader("Referer");

            response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
            response.setHeader("Location", referer);


            request.setAttribute("imfomsg", request.getAttribute("javax.servlet.forward.request_uri"));
            return RB_PAGEMAP.getString("jsp.main");
        }

        return RB_PAGEMAP.getString("jsp.main");
    }
}
