package control.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CommandLogin implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page = null;
        String login = req.getParameter("email");
        String password = req.getParameter("password");


        if(SERVICE.getLogin().checkCredentials(login, password)){
            req.setAttribute("auth", true);
            page = PROPS.getString("user.authorized");
        } else {
            if(!req.getMethod().equals("GET"))
                req.setAttribute("errormsg",
                    "Wrong login ["+login+"] or password ["+password+"]" );
            page = PROPS.getString("user.login");
        }

        return page;
    }
}
