import model.dao.connection.DataSource;
import model.dao.jdbc.AccountsDAOimpl;
import model.entity.Account;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by tonchief on 05/20/2017.
 */
public class Main {
    private static BasicDataSource pool = DataSource.getInstance().getBds();

    public static void main(String[] args) throws Exception {



        final Logger LOG = Logger.getLogger(AccountsDAOimpl.class);
        try {
            LOG.info("org.apache.commons.dbcp2.BasicDataSource found "
                    + Thread.currentThread()
                    .getContextClassLoader()
                    .loadClass(
                            "org.apache.commons.dbcp2.BasicDataSource")
                    .getProtectionDomain().getCodeSource()
                    .getLocation());
            LOG.info("org.apache.commons.pool2.impl.GenericObjectPool found "
                    + Thread.currentThread()
                    .getContextClassLoader()
                    .loadClass(
                            "org.apache.commons.pool2.impl.GenericObjectPool")
                    .getProtectionDomain().getCodeSource()
                    .getLocation());
        } catch (Exception e) {
            System.out.println("ERROR" + e);
        }


        ResourceBundle.clearCache();

/*
        Statement st = new DBConnectionSingle().getStatement();
        ResultSet rs = st.executeQuery("SELECT * from accounts");
        if (rs != null) {
            while (rs.next()) {
                System.out.println("\tid_Account=" + rs.getString(1) +
                        "; number=" + rs.getString(2) + ";");
            }
        }


        Connection conn = new DBConnection().getConnection();
        ResultSet rs2 = conn.createStatement().executeQuery("SELECT * from accounts ");
        if (rs2 != null && rs2.next())
            System.out.println("via POOL:\n\t" + "id_Account=" + rs2.getString(1) +
                    "; number=" + rs2.getString(2) + ";");
*/

        Connection con;
        ResultSet rs0;
        try {
            ResourceBundle accountsPS = ResourceBundle.getBundle("database.psqueries");
            for (int i = 1; i < 6; i++) {
                Connection conn = pool.getConnection();
                PreparedStatement ps = conn.prepareStatement(accountsPS.getString("accounts.isBlocked"), 0);

                ps.setInt(1, i);
                rs0 = ps.executeQuery();
                rs0.next();
                boolean res = rs0.getBoolean(1);
                System.out.println("*** " + i + ". Res from Main: " + res);
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        AccountsDAOimpl acct = AccountsDAOimpl.getInstance();
        Account account = new Account();
        account.setId(5);
        System.out.println(acct.isBlocked(account));
        account.setId(4);
        System.out.println(acct.isBlocked(account));
//        rs.close();
//        st.close();

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

        System.out.println("Acct 10 isBlocked = " + account.getBlocked());
        account.setBlock(!account.getBlocked());
        acct.update(10, account);
        System.out.println("AfterUpdate: Acct 10 isBlocked = " + ((Account) acct.getById(10)).getBlocked());


        System.out.println(acct.getById(10));
        System.out.println(acct.getByIdTWR(10));
        System.out.println(acct.getByIdTWR(10));


    }
}


/*
// PRESENTATION:
AccountsDAOimpl - dao
UtilDAO
Service.User - documentation
Admin - comments
*/






//doneTODO Alter table transactions, add column description varchar 128