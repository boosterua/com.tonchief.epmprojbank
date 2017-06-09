package model.dao.jdbc;

import model.dao.connection.DataSource;
import model.dao.interfaces.TransactionsDAO;
import model.entity.Entity;
import model.entity.Transaction;
import model.utils.PrintResultSet;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static model.dao.jdbc.UtilDAO.encodeUTF8KillCharsNotBMP;


public class TransactionsDAOimpl  implements TransactionsDAO {

    private static TransactionsDAOimpl instance = null;
    private final ResourceBundle resBundle = ResourceBundle.getBundle("database.psqueries");
    private final Logger logger = Logger.getLogger(TransactionsDAOimpl.class);
    private BasicDataSource pool = DataSource.getInstance().getBds();
    private static final String TID = "id_trans";
    private static final String CRA = "cr_account"; //2
    private static final String AMT = "amount";//3
    private static final String DAT = "date"; //4
    private static final String DSC = "description"; //5
    private static final String AID = "account_id"; // 6

    public static TransactionsDAOimpl getInstance() {
        if(instance==null)
            instance = new TransactionsDAOimpl();
        return instance;
    }

    /**
     *
     * @param transaction
     * @return
     * 3 queries are performed in a single transaction: update balance on credit account, debit account, and enter payment into transactions table
     * Expected SQL exception when new transaction being of bigger amount than available balance
     * com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException: Column 'account_id' cannot be null
     */
    public Integer insert(Object transaction) {
        //TODO: SQL transaction: new transaction + update balance

        try (Connection conn = pool.getConnection();
        ) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement(BUNDLE.getString("transactions.insertAndUpdate"), 1);){
                Transaction tr = (Transaction) transaction;
                logger.info("Insert into [transaction]: " + tr);

                ps.setString    (1, tr.getCreditAccount());
                ps.setBigDecimal(2, tr.getAmount());
                ps.setDate      (3, java.sql.Date.valueOf((tr.getDate()  )));
                ps.setString    (4, encodeUTF8KillCharsNotBMP(tr.getDescription()));//TODO: convert longSymbols (₴) to UTF8, Crashes on sql.insert otherwise
                ps.setBigDecimal(5, tr.getAmount());
                ps.setInt       (6, tr.getDtAccount().getId());

                logger.info("PS[1]: " + ps.toString());
                ps.executeUpdate();


                PreparedStatement psUpdate = conn.prepareStatement(BUNDLE.getString("accounts.updateBalanceByAccountId"));
                psUpdate.setBigDecimal(1, tr.getAmount());
                psUpdate.setInt(2, tr.getDtAccount().getId());
                logger.info("PS[2]: " + psUpdate.toString());
                psUpdate.executeUpdate();

                PreparedStatement psUpdBalance2 = conn.prepareStatement(BUNDLE.getString("accounts.updateBalanceIfLocal"));
                psUpdBalance2.setString(1, tr.getCreditAccount());
                psUpdBalance2.setBigDecimal(2, tr.getAmount());
                logger.info("PS[3]: " + psUpdBalance2.toString());
                psUpdBalance2.executeUpdate();

                conn.commit();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    logger.debug( PrintResultSet.getDump(rs));
                    rs.next();
                    return rs.getInt(1);
                } catch (SQLException e){
                    logger.error("SQL ex. Could not insert new transaction", e);
                    return 0;
                } finally {
                    ps.close();
                    psUpdate.close();
                }

            }
        } catch (SQLException e) {
            //Here - Most probably - because of negative balance. Return some code
            logger.fatal("SQL exception", e);
        }

        return -1;
    }

    public boolean update(int id, Entity data) {
        return false;
    }

    public boolean delete(long id) {
        return false;
    }

    public Entity getById(Integer id) {
        return null;
    }


}
