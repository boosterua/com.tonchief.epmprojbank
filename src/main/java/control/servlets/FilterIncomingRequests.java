package control.servlets;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterIncomingRequests implements Filter {
    private ServletContext context;
    private static final Logger LOGGER = Logger.getLogger(FilterIncomingRequests.class);


    static class FilteredRequest extends HttpServletRequestWrapper {

        public FilteredRequest(ServletRequest request) {
            super((HttpServletRequest)request);
        }

        public String getParameter(String paramName) {
            String value = super.getParameter(paramName);
            if(value==null) return null;
            String val0 = value;
            String name = paramName;

            value = value.replaceAll("<(.*?)>",""); //No tags, thank you
            value = value.replaceAll("[^\\w А-Яа-яЁёІіЇїЄє!@#№;:,\\.\\$\\%\\&\\*\\(\\)\\-\\=\\+\\/]+","");

            //system commands
            if(name.equals("action") || name.equals("command") || name.equals("type") || name.equals("locale") )
                value = value.replaceAll("[^a-zA-Z0-9_\\.\\-]+","");

            //Digits only
            if(name.indexOf("_id")>0 || name.indexOf("_number")>0 || name.contains("account")
                    || name.contains("index") || name.equals("role")) {
                value = value.replaceAll("[^0-9]", "");
                //if value='' throw exception
            }

            //Amounts
            if(name.indexOf("_id")>0 || name.indexOf("_number")>0 || name.contains("amount") ) {
                if(value.contains(".") && value.contains(","))
                    value = value.replaceAll(",", "");
                else
                    value = value.replaceAll(",", ".");
                value = value.replaceAll("[^0-9\\.]", "");
                Pattern p = Pattern.compile("^(\\d*\\.\\d+|\\d+\\.?\\d*)$");
                Matcher m = p.matcher(value);
                if(!m.matches())
                    value = "0";
                //throw new ExceptionIncomingRequestMalformed("NAN:", name +"="+ value);
            }

            if(name.equals("email"))
                value = value.replaceAll("[^\\w\\.\\-\\@]","");

            if(!val0.equals(value))
                LOGGER.debug( "::Request Params::{"+name+"="+val0+"} val Changed to "+value);
            return value;
        }
    }



    public void init(FilterConfig fConfig) throws ServletException {
        this.context = fConfig.getServletContext();
        this.context.log("RequestLoggingFilter initialized");
    }
    public void destroy() {
    }


    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        String path = req.getRequestURI().substring(req.getContextPath().length());


        if (path.startsWith("/static") || path.startsWith("/resources")) {
            chain.doFilter(req, response); // Goes to default servlet.
        } else {
            chain.doFilter(new FilteredRequest(request), response);
            //request.getRequestDispatcher("/pages" + path).forward(req, response);
        }
    }

    private String parseString(String val){
        return null;
    }
}




/*
  Request Params::{type=make_payment}
  Request Params::{action=make_payment}
  Request Params::{account_id=76}
  Request Params::{acct_id=76}
  Request Params::{cr_account=1}
  Request Params::{trf_amount=1}
  Request Params::{trf_description=133}
                  {command=register}
  Request Params::{name=111111111111111111111}
  Request Params::{email=11111111111111111111111111@11111111111111111111}
  Request Params::{password=1}
  Request Params::{fee=1}
*/


/**
*
        public String cleanUp(String name, String value){
            Enumeration<String> params = req.getParameterNames();
            while(params.hasMoreElements()){
                String name = params.nextElement();
                String value = request.getParameter(name);
                this.context.log(req.getRemoteAddr() + "::Request Params::{"+name+"="+value+"}");
                LOGGER.debug(req.getRemoteAddr() + "::Request Params::{"+name+"="+value+"}");


                value = value.replaceAll("[^\\wА-Яа-яІіЇїЄє!@#№;:,\\.\\$\\%\\&\\*\\(\\)\\-\\=\\+\\/]+","");

                //Extra checks

                //system commands
                if(name.equals("action") || name.equals("command") || name.equals("type") || name.equals("action") ||
                        name.equals("locale") )
                    value = value.replaceAll("[^a-zA-Z0-9_\\.\\-]+","");

                //Digits only
                if(name.indexOf("_id")>0 || name.indexOf("_number")>0 || name.contains("account")
                        || name.contains("index") || name.equals("role"))
                    value = value.replaceAll("[^0-9]","");

                //Amounts
                if(name.indexOf("_id")>0 || name.indexOf("_number")>0 || name.contains("amount") ) {
                    if(value.contains(".") && value.contains(","))
                        value = value.replaceAll(",", "");
                    else
                        value = value.replaceAll(",", ".");
                    value = value.replaceAll("[^0-9\\.]", "");
                    Pattern p = Pattern.compile("^(\\d*\\.\\d+|\\d+\\.?\\d*)$");
                    Matcher m = p.matcher(value);
                    if(!m.matches())
                        value = "0";
                    //throw new ExceptionIncomingRequestMalformed("NAN:", name +"="+ value);
                }

                if(name.equals("email"))
                    value = value.replaceAll("[^\\w\\.\\-\\@]","");

                req.setAttribute(name, value);
                LOGGER.debug( "::Changed Request Params::{"+name+"="+value+"}");

            }
        }
        */