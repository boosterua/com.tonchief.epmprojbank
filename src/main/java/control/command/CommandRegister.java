package control.command;


import org.apache.log4j.Logger;
import service.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class CommandRegister implements Command {
    private final Logger logger = Logger.getLogger(CommandRegister.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page = null;
        String name = req.getParameter("name");
        String login = req.getParameter("email");
        String password = req.getParameter("password");
        HttpSession session = req.getSession();


        if(req.getMethod().equals("POST")){

//TODO - Filters - check for form validity - create some util methods for general checks!!
//TODO: set Cookie with lang. Session is higher priority than cookie, ck gets overwritten by sess
            
            /* role=0 means - newly registered, but the value gets auto
             assigned after admin approves application and issues a new card */
            User user = new User(name, login, password, 0L);

            Integer uid = SERVICE.getUser().register(user);
            if(uid!=null && uid>0) {
                logger.info("saved to db with id=" + uid);

                req.setAttribute("infomsg", "REG_SUCCESS_LOGIN_NOW");
                //TODO: JSTL custom tags - error/info message

                req.setAttribute("email", login);
                req.setAttribute("action", "login");

                page = RB_PAGEMAP.getString("jsp.user.login");


            } else { // ERROR!!

                logger.error("New user registration: failed to insert User into db. Ref.Err#1441.");
                req.setAttribute("errormsg", "Error adding your registration to database. Please contact support " +
                        "at 1-800-000-00-00 and provide this error code:<b>#1441</b> and the exact time of this incident " +
                        "(<b>"+ new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date())+"</b>).");
                req.setAttribute("returnPage", RB_PAGEMAP.getString("jsp.user.registration"));
                page = RB_PAGEMAP.getString("jsp.error") ;
                return page;
            }


        } else {

            Map<Integer,String> fees = SERVICE.getFees().getFeeNamesMap();
            req.setAttribute("feeNames", fees);
            //TODO: CHOOSE BETWEEN THESE TWO!! FIXED: -For now - none works ( so using plain text
            /*List<Fee> feeList = SERVICE.getFees().getFees();
            req.setAttribute("feeList", feeList);*/

/*
            //doneTODO: TEMP!: before i find how to make this f*kin jstl to work
            StringBuffer options= new StringBuffer();
            for(Integer k: fees.keySet())
                if(k!=null && k!=0 && fees.get(k)!="")
                    options.append("<option value=\"" + k + "\">"+ fees.get(k) +"</option>");
            req.setAttribute("feeOptions", options.toString());
*/

//            logger.info("* MAP feeNames:"+fees);

            page =  RB_PAGEMAP.getString("jsp.user.registration");
        }
        return page;
    }
}

