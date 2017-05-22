import model.dao.connection.ConnectionToDB;
import model.dao.connection.DBConnection;
import model.dao.exceptions.MySqlPoolException;
import model.dao.jdbc.AccountsDAOimpl;
import model.dto.Account;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by tonchief on 05/20/2017.
 */
public class Main {
    public static void main(String[] args) throws SQLException, MySqlPoolException, InterruptedException {
        Statement st = new DBConnection().getStatement();
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
            System.out.println("vai POOL:\n\t" + "id_Account=" + rs2.getString(1) +
                    "; number=" + rs2.getString(2) + ";");


        AccountsDAOimpl acct = new AccountsDAOimpl();
        Account account = new Account();
        account.setId(5);
        System.out.println(acct.isBlocked(account));
        account.setId(4);
        System.out.println(acct.isBlocked(account));
        rs.close();
        st.close();
    }
}


//TODO Alter table transactions, add column description varchar 128