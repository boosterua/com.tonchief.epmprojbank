package control.command;

import model.entity.Fee;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class CommandShowFees implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page = RB_PAGEMAP.getString("jsp.user");

        List<String> tableHeaders = Arrays.asList("ID", "FEE_CARD_NAME", "PER_TRANS_FEE", "NEW_CARD_FEE", "APR");
        List<Fee> feeList = SERVICE.getFees().getFees();
        req.setAttribute("tableName", "FEE_SCHEDULE");
        req.setAttribute("tableHeadersArr", tableHeaders);
        req.setAttribute("feeList", feeList);
        req.setAttribute("title", "FEE_SCHEDULE");
        req.setAttribute("action", "fees_table");
        return page;
    }
}
