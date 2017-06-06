package control.command;

import model.dao.exceptions.ExceptionDAO;
import model.dao.exceptions.MySqlPoolException;
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
    String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ExceptionDAO, MySqlPoolException;
    default void disablePageCache(HttpServletResponse resp){
        /*resp.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
        resp.setHeader("Pragma", "no-cache");*/
        //Moved to filter
    }
}
