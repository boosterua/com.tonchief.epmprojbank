package control.servlets;


import control.command.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public class ControllerHelper {

    private static ControllerHelper instance = null;
    HashMap<String, Command> commands = new HashMap<>();


    private ControllerHelper() {
        commands.put("admin",           new CommandAdmin());

        commands.put("user",            new CommandUser());
        commands.put("login",               commands.get("user"));
        commands.put("logout",              commands.get("user"));
        commands.put("show_authuser_hp",    commands.get("user"));
        commands.put("authenticate",        commands.get("user"));
        commands.put("client",              commands.get("user"));

        commands.put("account",         new CommandManageAccount());

        commands.put("register",        new CommandRegister());
        commands.put("submit_registration", commands.get("register"));

        commands.put("showfees",        new CommandShowFees());

        commands.put("switch_lang",     new CommandEmpty());

        commands.put("transaction",     new CommandTransaction());

        //commands.put("show_clients", new CommandShowClients());
        //commands.put("show_clients", new CommandAdmin());
    }




    public Command getCommand(HttpServletRequest req) {
        Command command = commands.get(req.getParameter("command"));
        if (command == null)
            command = new CommandEmpty();
        return command;
    }

    public static ControllerHelper getInstance() {
        if (instance == null)
            instance = new ControllerHelper();
        return instance;
    }
}
