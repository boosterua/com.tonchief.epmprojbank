package control.command;

import model.entity.Client;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CommandShowClients implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page = RB_PAGEMAP.getString("jsp.admin");
        String act = req.getParameter("action");
        Long role = Long.parseLong(req.getParameter("role"));

        switch (act){
            case ("showClientsByRole"):
                List<Client> clients = SERVICE.getAdmin().getClientsByRole(role);
//                Logger logger = Logger.getLogger(CommandShowClients.class);
//                logger.info("From CommandShowClients List Size=" + clients.size());
                req.setAttribute("clientList", clients);
                req.setAttribute("action", "showClientsByRole");
                break;
        }

        return page;
    }
}

/*

tableName
tableHeadersArr
tableDataArr
*/
