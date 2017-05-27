package control.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


//@WebServlet("/files/*")
public class Static extends HttpServlet  {

    @Override
    protected void doHead(HttpServletRequest request, HttpServletResponse response) throws ServletException ,IOException {
        doRequest(request, response, true);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doRequest(request, response, false);
    }

    private void doRequest(HttpServletRequest request, HttpServletResponse response, boolean head) throws IOException {
/*        response.reset();
        StaticResource resource;

        try {
            resource = getStaticResource(request);
        }
        catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        if (resource == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String fileName = URLEncoder.encode(resource.getFileName(), StandardCharsets.UTF_8.name());
        boolean notModified = setCacheHeaders(request, response, fileName, resource.getLastModified());

        if (notModified) {
            response.sendError(HttpServletResponse.SC_NOT_MODIFIED);
            return;
        }

        setContentHeaders(response, fileName, resource.getContentLength());

        if (head) {
            return;
        }

        writeContent(response, resource);
        */
    }


}