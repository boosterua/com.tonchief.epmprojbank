package control.command;


import model.dao.exceptions.ExceptionDAO;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CommandAdmin implements Command {
    private static final Logger LOGGER = Logger.getLogger(CommandAdmin.class);
    private String page = RB_PAGEMAP.getString("jsp.admin");


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ExceptionDAO {
        String act = req.getParameter("action");
        if (act == null) act = "";
        String uid = req.getParameter("user_id");
        Integer clientId = uid==null? 0 : Integer.parseInt(uid);;

        switch (act){
            case ("set_user_role"):
                String roleStr = req.getParameter("role");
LOGGER.info("clientID:"+clientId + " > setuserrole " + roleStr);
                setRole(req, resp, clientId, roleStr);
                break;
            default:
                wrongParam(req);
        }
//        LOGGER.info(page);
        return page;
    }

    private void setRole(HttpServletRequest req, HttpServletResponse resp, Integer clientId, String roleStr ) throws ExceptionDAO {

        if(roleStr==null || clientId==0){
            LOGGER.info("client_id="+clientId + "; roleStr="+roleStr);
            req.setAttribute("errormsg", "NO_USER_ID");
            wrongParam(req);
            return;
        }
        Long role = Long.parseLong(roleStr);

        try {
            boolean res = SERVICE.getAdmin().setRole(clientId, role);
            LOGGER.info(""+clientId + " > set.user.role " + roleStr);
            req.setAttribute("action", "get_one_client");
            req.setAttribute("command", "admin");
            req.setAttribute("result", res);
            req.setAttribute("infomsg", "RESULT_" + (res?"OK":"ERROR"));
            if(req.getParameter("content_type")!=null) {//ajax
//                page = RB_PAGEMAP.getString("jsp.ajax.result");
//                LOGGER.info("content-type found. trying plaintext as output (ajax)");
                resp.setContentType("text/plain");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(role>0?"OK":"0");
                page="";
            }
        } catch (ExceptionDAO exceptionDAO) {
            throw exceptionDAO;
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    private void wrongParam(HttpServletRequest req){
        req.setAttribute("errormsg", "WRONG_PARAM_REQUEST");
    }
}
