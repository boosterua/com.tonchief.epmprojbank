package control.command;

import model.entity.Client;
import model.entity.Fee;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by tonchief on 05/29/2017.
 */

public class CommandShowClients implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page = PROPS.getString("admin");
        String act = req.getParameter("action");
        Long role = Long.getLong(req.getParameter("role"));

        switch (act){
            case ("showClientsByRole"):
                List<Client> clients = SERVICE.getAdmin().getClientsByRole(role);
                req.setAttribute("clientList", clients);
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
