package control.servlets;


import control.command.Command;
import control.command.CommandLogin;
import control.command.CommandEmpty;
import control.command.CommandRegister;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public class ControllerHelper {

    private static ControllerHelper instance = null;
    HashMap<String, Command> commands = new HashMap<String, Command>();

    private ControllerHelper() {
        commands.put("login", new CommandLogin());
        commands.put("authenticate", new CommandLogin());
        commands.put("register", new CommandRegister());
        commands.put("logout", new CommandEmpty());//TODO logout logic here
    }




    public Command getCommand(HttpServletRequest req) {
        Command command = commands.get(req.getParameter("command"));
        if (command == null) {
            command = new CommandEmpty();
        }
        return command;
    }

    public static ControllerHelper getInstance() {
        if (instance == null) {
            instance = new ControllerHelper();
        }
        return instance;
    }
}
