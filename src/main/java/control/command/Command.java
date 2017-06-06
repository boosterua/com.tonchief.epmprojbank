package control.command;

import model.dao.exceptions.ExceptionDAO;
import model.dao.exceptions.MySqlPoolException;
import service.SvcFactoryImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ResourceBundle;


public interface Command {
    SvcFactoryImpl SERVICE = SvcFactoryImpl.getInstance();
    ResourceBundle RB_PAGEMAP = ResourceBundle.getBundle("webconfig.pagemapping");
//    ResourceBundle RB_LOCALE = ResourceBundle.getBundle("locale");
    String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ExceptionDAO, MySqlPoolException;
    default void printData(HttpServletResponse resp, String ctype, String message) throws IOException, ServletException {
        if(message==null) throw new ServletException("NP as string passed to pagewriter.");
        resp.setContentType(ctype!=null && !ctype.equals("") ? ctype : "text/html");
        resp.setContentLength(message.length());
        PrintWriter writer = resp.getWriter();
        writer.print(message);
        writer.flush();
        writer.close();
    }
}
