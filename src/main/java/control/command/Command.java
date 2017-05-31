package control.command;

import service.SvcFactoryImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ResourceBundle;


public interface Command {
    SvcFactoryImpl SERVICE = SvcFactoryImpl.getInstance();
    ResourceBundle RB_PAGEMAP = ResourceBundle.getBundle("webconfig.pagemapping");
//    ResourceBundle RB_LOCALE = ResourceBundle.getBundle("locale");
    String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
}
