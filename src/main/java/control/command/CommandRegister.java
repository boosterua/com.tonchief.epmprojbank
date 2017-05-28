package control.command;


import model.entity.Fee;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CommandRegister implements Command {
    private final Logger logger = Logger.getLogger(CommandRegister.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page = null;
        String login = req.getParameter("email");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        //TODO - Filters - check for form validity
        if(req.getMethod().equals("POST")){
            //TODO *** Service - submit new application
            req.setAttribute("infomsg_html","<br><span class=\"badge badge-success\">Registration Successful. You may login now.</span>");
            //TODO: JSTL tags - error/info message

            req.setAttribute("email",login);
            page = PROPS.getString("user.login");
        } else {
            Map<Integer,String> fees = SERVICE.getFees().getFeeNamesMap();

            req.setAttribute("feeNames", fees);

            List<Fee> feeList = SERVICE.getFees().getFees();
            req.setAttribute("feeList", feeList);




            logger.info("* MAP feeNames:");
            logger.info(fees);
            for (Integer i: fees.keySet())
                logger.info(i+"."+fees.get(i));








            //TODO - send fees list to jsp








            page =  PROPS.getString("user.registration");
        }
        return page;
    }
}

