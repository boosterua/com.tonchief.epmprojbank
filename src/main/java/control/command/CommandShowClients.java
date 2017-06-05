package control.command;

import model.dao.exceptions.ExceptionDAO;
import model.entity.Client;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CommandShowClients implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ExceptionDAO {
        String page = RB_PAGEMAP.getString("jsp.admin");
        String act = req.getParameter("action");
        if(act==null) act="";


        switch (act){
            case ("show_clients_by_role"):

                Long role = Long.parseLong(req.getParameter("role"));
                List<Client> clients = SERVICE.getAdmin().getClientsByRole(role);
//                Logger logger = Logger.getLogger(CommandShowClients.class);
//                logger.info("From CommandShowClients List Size=" + clients.size());
                req.setAttribute("clientList", clients);
                req.setAttribute("action", "show_clients_by_role");
                break;
            case ("get_one_client"):
                Integer clientId = Integer.parseInt(req.getParameter("user_id"));
                Client client = null;
                try {
//                    client = SERVICE.getAdmin().getClientById(clientId);
                    client = SERVICE.getAdmin().getClientDetailedById(clientId);
//                    Card card = SERVICE.getAdmin().issueNewCard(clientId);

                    req.setAttribute("client", client);
                    req.setAttribute("action", "show_сlient_to_approve");
                } catch (ExceptionDAO exceptionDAO) {
                    throw exceptionDAO;
                }
                break;
            default:
                req.setAttribute("errormsg", "WRONG_PARAM_REQUEST");
        }

        return page;
    }
}

/*

tableName
tableHeadersArr
tableDataArr
*/
