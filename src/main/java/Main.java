import model.dao.connection.ConnectionToDB;
import model.dao.connection.DBConnectionSingle;
import model.dao.jdbc.AccountsDAOimpl;
import model.dto.Account;

import java.sql.*;
import java.util.ResourceBundle;

/**
 * Created by tonchief on 05/20/2017.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Statement st = new DBConnectionSingle().getStatement();
        ResultSet rs = st.executeQuery("SELECT * from accounts");
        if (rs != null) {
            while (rs.next()) {
                System.out.println("\tid_Account=" + rs.getString(1) +
                        "; number=" + rs.getString(2) + ";");
            }
        }


        Connection conn = new ConnectionToDB().getConnection();
        ResultSet rs2 = conn.createStatement().executeQuery("SELECT * from accounts ");
        if (rs2 != null && rs2.next())
            System.out.println("via POOL:\n\t" + "id_Account=" + rs2.getString(1) +
                    "; number=" + rs2.getString(2) + ";");


        Connection con;
        ResultSet rs0;
        try {
            ResourceBundle accountsPS = ResourceBundle.getBundle("database.psqueries");
            for (int i = 1; i < 6; i++) {
                con = ConnectionToDB.getConnection();
                PreparedStatement ps = con.prepareStatement(accountsPS.getString("accounts.isBlocked"), 1);
                ps.setInt(1, i);
                rs0 = ps.executeQuery();
                rs0.next();
                boolean res = rs0.getBoolean(1);
                System.out.println("*** " + i + ". Res from Main: " + res);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
//                cdb.closeAll(con, ps, rs0);
        }

        AccountsDAOimpl acct = AccountsDAOimpl.getInstance();
        Account account = new Account();
        account.setId(5);
        System.out.println(acct.isBlocked(account));
        account.setId(4);
        System.out.println(acct.isBlocked(account));
        rs.close();
        st.close();

        Account newAccount = new Account();
        newAccount.setName("2600");
        newAccount.setClientId(5);
        newAccount.setBlock(true);
        System.out.println("insert new account(**)+" + newAccount.toString());
        int newId = acct.insert(newAccount);

        System.out.println("getById(" + newId + "):" + acct.getById(newId));
        System.out.println("delete(" + newId + ")" + acct.delete(newId));
        System.out.println("getById(" + newId + "):" + acct.getById(newId));

        account = (Account) acct.getById(10);
        System.out.println(account);

        System.out.println("Acct 10 isBlocked = " + account.getBlockedStatus());
        account.setBlock(!account.getBlockedStatus());
        acct.update(10, account);
        System.out.println("AfterUpdate: Acct 10 isBlocked = " + ((Account) acct.getById(10)).getBlockedStatus());


    }
}


//TODO Alter table transactions, add column description varchar 128