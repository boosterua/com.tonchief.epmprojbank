package control.command;

import model.entity.Account;
import model.entity.Client;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import static control.command.CommandManageAccount.getAccountByIdSessionScope;

public  class CommandUtils {
    private static final Logger LOGGER = Logger.getLogger(CommandUtils.class);
    static ResourceBundle RB_PAGEMAP = ResourceBundle.getBundle("webconfig.pagemapping");

    public static boolean accountBelongsToUser(HttpServletRequest req, Integer accountId, Logger LOGGER) {
        if (fieldsAreEmpty(accountId)) {
            req.setAttribute("errormsg", "ERROR_EMPTY_FIELDS");
            LOGGER.error("Empty fields at registration detected. Returning error.");
            req.setAttribute("returnPage", RB_PAGEMAP.getString("jsp.user"));
            return false;
        }

        Account account = getAccountByIdSessionScope(req, accountId);
        if (account == null) {
            req.setAttribute("errormsg", "WRONG_PARAM_REQUEST");
        } else if (account.getBlocked() || ((Client) req.getSession().getAttribute("client")).getRole().equals(0)) {
            req.setAttribute("errormsg", "OPER_ON_BLOCKED_ACCOUNT_FORBIDDEN");
            return false;
        }
        return true;
    }

    public static  boolean fieldsAreEmpty(Object ... args){
        for (Object str : args)
            if(str==null || str.toString().equals(""))
                return true;

        return false;
    }

    public static String md5Hash(String pass) {
        if(pass==null) return null;
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(pass.getBytes());
            byte byteData[] = md.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++)
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));

            String md5Hex = sb.toString();
            return md5Hex;

        } catch (NoSuchAlgorithmException e) {
            LOGGER.fatal("MD5 Algo not available!", e);

        }
        return pass;

    }









}
