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

import static control.command.CommandUtils.fieldsAreEmpty;
import static control.command.CommandUtils.md5Hash;

public class CommandRegister implements Command {
    private static final Logger LOGGER = Logger.getLogger(CommandRegister.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page = null;
        String name = req.getParameter("name");
        String login = req.getParameter("email");
        String password = req.getParameter("password");
        String feeId = req.getParameter("fee");
        HttpSession session = req.getSession();


        if(req.getMethod().equals("POST")){

            /* role=0 means - newly registered, but the value gets auto
             assigned after admin approves application and issues a new card */
            if(fieldsAreEmpty(name, login, password)){
                LOGGER.error("Empty fields at registration detected. Returning error.");
                req.setAttribute("errormsg", "ERROR_EMPTY_FIELDS");
                req.setAttribute("returnPage", RB_PAGEMAP.getString("jsp.user.registration"));
                page = RB_PAGEMAP.getString("jsp.error") ;
                return page;
            }

            User user = new User(name, login, md5Hash(password), 0L);
            if(feeId==null) feeId="1";
            user.setFeeId(Integer.parseInt(feeId));

            Integer uid = SERVICE.getUser().register(user);
            if(uid!=null && uid>0) {
                LOGGER.info("saved to db with id=" + uid);
                req.setAttribute("infomsg", "REG_SUCCESS_LOGIN_NOW");
                req.setAttribute("email", login);
                req.setAttribute("action", "login");
                page = RB_PAGEMAP.getString("jsp.user.login");


            } else if(uid==-23) {
                LOGGER.info("User registration - email constraint violation: already exists in db");
                req.setAttribute("errormsg", "USER_ID_ALREADY_USED");
                req.setAttribute("returnPage", RB_PAGEMAP.getString("jsp.user.registration"));
                page = RB_PAGEMAP.getString("jsp.user.registration") ;

            }else { // ERROR!!

                LOGGER.error("New user registration: failed to insert User into db. Ref.Err#1441.");
                req.setAttribute("errormsg", "Error adding your registration to database. Please contact support " + "at 1-800-000-00-00 and provide this error code:<b>#1441</b> and the exact time of this incident " +
                        "(<b>"+ new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date())+"</b>).");
                req.setAttribute("returnPage", RB_PAGEMAP.getString("jsp.user.registration"));
                page = RB_PAGEMAP.getString("jsp.error") ;
                return page;
            }


        } else {

            Map<Integer,String> fees = SERVICE.getFees().getFeeNamesMap();
            req.setAttribute("feeNames", fees);
            //doneTODO: CHOOSE BETWEEN THESE TWO!! FIXED: -For now - none works ( so using plain text
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

//            LOGGER.info("* MAP feeNames:"+fees);

            page =  RB_PAGEMAP.getString("jsp.user.registration");
        }
        return page;
    }
}

