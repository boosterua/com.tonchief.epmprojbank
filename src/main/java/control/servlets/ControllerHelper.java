package control.servlets;


import control.command.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public class ControllerHelper {

    private static ControllerHelper instance = null;
    HashMap<String, Command> commands = new HashMap<String, Command>();

    private ControllerHelper() {
        commands.put("login", new CommandLogin());
        commands.put("authenticate", new CommandLogin());
        commands.put("register", new CommandRegister());
        commands.put("showfees", new CommandShowFees());
        commands.put("submit_registration", new CommandRegister());
        commands.put("showclients", new CommandShowClients());
        commands.put("logout", new CommandLogin());
        commands.put("switch_lang", new CommandEmpty());
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
