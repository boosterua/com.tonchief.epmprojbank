package control.command;

import model.entity.Fee;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by tonchief on 05/29/2017.
 */
public class CommandShowFees implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page = PROPS.getString("fees");

        List<String> tableHeaders =  Arrays.asList("ID", "FEE_NAME", "PER_TRANSACTION_FEE", "NEW_CARD_FEE", "APR");
        List<Fee> feeList = SERVICE.getFees().getFees();
        req.setAttribute("tableName","FEE_SCHEDULE");
        req.setAttribute("tableHeadersArr",tableHeaders);
        req.setAttribute("feeList",feeList);
        req.setAttribute("title","FEE_SCHEDULE");
//        req.setAttribute("sectionID","FEES");
        return page;
    }
}

/*

tableName
tableHeadersArr
tableDataArr
*/
