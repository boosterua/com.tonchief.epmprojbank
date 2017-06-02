package control.servlets;

import control.command.Command;
import model.dao.exceptions.ExceptionDAO;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


//When switching to annotations:
//@WebServlet("/s")
//@WebServlet(name = "Controller", urlPatterns = {"/Controller"})

public class Controller extends HttpServlet {
    transient private final Logger logger = Logger.getLogger(Controller.class);
    transient ControllerHelper controllerHelper = ControllerHelper.getInstance();

    //TODO ??? How to create WAR file automaticalyy w/o changing type of server start? Where do i put it then?
    //TODO ??? Как отлавливать 500е ошибки.

    public Controller(){ super();}

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String page = null;
        try {
            Command command = controllerHelper.getCommand(req);
            page = command.execute(req, resp);
        } catch (ServletException se){
            logger.error(se);
            req.setAttribute("msgErr", "SERVLET_EXCEPTION");
        } catch (IOException ie){
            logger.error(ie);
            req.setAttribute("msgErr", "IO_EXCEPTION");
        } catch (ExceptionDAO exceptionDAO) {
            logger.error(exceptionDAO);
        }
        getServletContext().getRequestDispatcher(page).forward(req, resp);
    }


}




/*      //TEMP Impl
        req.setAttribute("thisname","T.C.");
        req.getRequestDispatcher("view/index.jsp").forward(req, resp);
        PrintWriter out = resp.getWriter();
        out.print("#EPMPROJBANK by tonchief. Servlet responce.");
*/
