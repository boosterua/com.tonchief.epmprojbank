package control.jstl;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

public class Table extends TagSupport {

    private static final long serialVersionUID = 1L;
    private List<List<String>> list;
    private String tableClass;
    private String headerClass;
    private List<String> headers;
    private List<String> list2;

    public void setList2(List<String> list2) {
        this.list2 = list2;
    }

    public void setTableClass(String className) {
        this.tableClass = className;
    }

    public void setHeaderClass(String className) {
        this.headerClass = className;
    }

    public void setList(List<List<String>> list){
        this.list = list;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    @Override
    public int doStartTag() throws JspException {
        try {

            int i=1;
            if(headers!=null) {
                list.add(0, headers);
                i=0;
            }

            pageContext.getOut().print("<table class='"+ tableClass +"' style='font-size:small'>");

            for(Object row : list) {
                if(i==0) pageContext.getOut().print("<thead class='"+headerClass+"'>\n");
                pageContext.getOut().print("\n<tr>\n");
                for (Object td : (List) row) {
                    if(i==0)
                        pageContext.getOut().print("\t<th>" + td.toString() + "</th>\n");
                    else
                        pageContext.getOut().print("\t<td>" + td.toString() + "</td>\n");
                }
                if(i==0) pageContext.getOut().print("\n</thead>\n<tbody>");
                pageContext.getOut().print("\n</tr>\n");
                i++;
            }
            pageContext.getOut().print("\n</tbody>");
            pageContext.getOut().print("\n</table>");



        } catch (IOException ioException) {
            throw new JspException("Error: " + ioException.getMessage());
        }
        return SKIP_BODY;
    }
}