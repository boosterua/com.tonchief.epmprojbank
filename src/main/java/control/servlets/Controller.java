package control.servlets;

import control.command.Command;
import model.dao.exceptions.ExceptionDAO;
import model.dao.exceptions.MySqlPoolException;
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
    transient private static final Logger LOGGER = Logger.getLogger(Controller.class);
    ControllerHelper controllerHelper = ControllerHelper.getInstance();


    public Controller() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        } catch (ServletException se) {
            LOGGER.error(se);
            req.setAttribute("errormsg", "SERVLET_EXCEPTION");
        } catch (IOException ie) {
            LOGGER.error(ie);
            req.setAttribute("errormsg", "IO_EXCEPTION");
        } catch (ExceptionDAO | MySqlPoolException exceptionDAO) {
            req.setAttribute("errormsg", "DB_EXCEPTION");
            LOGGER.error(exceptionDAO);
        }

        LOGGER.debug("Page:" + page + " inc.ReqQS: " + req.getQueryString());

        if (page != null && !page.equals("")) try {
            getServletContext().getRequestDispatcher(page).forward(req, resp);
        } catch (Error e) {
            LOGGER.error("getServletContext().getRequestDispatcher(page).forward(req,resp):", e);
        }
        /* do nothing, this is the case when no page is expected,
        direct call to writer is used, written/flushed/closed.. see printData method */
    }


}
