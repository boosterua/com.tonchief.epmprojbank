package control.old;

import control.command.Command;
import model.dao.exceptions.ExceptionDAO;
import model.entity.Client;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
@Deprecated
public class CommandShowClients implements Command {
    Logger logger = Logger.getLogger(CommandShowClients.class);
    String page = RB_PAGEMAP.getString("jsp.admin");

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ExceptionDAO {
        String act = req.getParameter("action");
        if(act==null) act="";



        String uid = req.getParameter("user_id");
        Integer clientId = (uid==null)? 0 : Integer.parseInt(uid);

        if(act!=null){
            //Chekcing if i got rid of all calls to this method
            logger.error("**********************\n\n\n\n\n\nSTILL USING CommandShowClients.execute\n\n\n\n\n");
            return RB_PAGEMAP.getString("jsp.error");
        }


        switch (act){
            case ("show_clients_by_role"):

                Long role = Long.parseLong(req.getParameter("role"));
                List<Client> clients = SERVICE.getAdmin().getClientsByRole(role);
                req.setAttribute("clientList", clients);
                req.setAttribute("action", "show_clients_by_role");
                break;
            case ("get_one_client"):
                //TODO - remove the whole class in favor of CommandAdmin
                Client client = null;
                client = SERVICE.getAdmin().getClientDetailedById(clientId);
                req.setAttribute("client", client);
                req.setAttribute("action", "show_сlient_to_approve");
                break;

            case ("set_user_role"): //OK
                String roleStr = req.getParameter("role");
                logger.info("clientID:"+ clientId + " > setuserrole " + roleStr);
                setRole(req, resp, clientId, roleStr);
                break;


            default:
                req.setAttribute("errormsg", "WRONG_PARAM_REQUEST");
        }

        return page;
    }


    private void setRole(HttpServletRequest req, HttpServletResponse resp, Integer clientId, String roleStr ) throws ExceptionDAO {

        if(roleStr==null || clientId==0){
            logger.info("client_id="+clientId + "; roleStr="+roleStr);
            req.setAttribute("errormsg", "NO_USER_ID");
            return;
        }
        Long role = Long.parseLong(roleStr);

        try {
            boolean res = SERVICE.getAdmin().setRole(clientId, role);
            logger.info(""+clientId + " > set.user.role " + roleStr);
            req.setAttribute("command", "admin");
            req.setAttribute("command", "show_clients");
            req.setAttribute("action", "get"+"_one_client");
            req.setAttribute("result", res);
            req.setAttribute("infomsg", "RESULT_" + (res?"OK":"ERROR"));
            //ifAjaxPrintOK(req, resp, role>0?"OK":"0");
        } catch (ExceptionDAO exceptionDAO) {
            logger.error(exceptionDAO);
        }
    }



}

/*

tableName
tableHeadersArr
tableDataArr
*/
