package model.dao.jdbc;

import model.dao.connection.DataSource;
import model.dao.exceptions.MySqlPoolException;
import model.dao.interfaces.TransactionsDAO;
import model.entity.Account;
import model.entity.Entity;
import model.entity.Transaction;
import model.utils.PrintResultSet;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static model.dao.jdbc.UtilDAO.encodeUTF8KillCharsNotBMP;


public class TransactionsDAOimpl  implements TransactionsDAO {

    private static TransactionsDAOimpl instance = null;
    private final ResourceBundle resBundle = ResourceBundle.getBundle("database.psqueries");
    private static final Logger LOGGER = Logger.getLogger(TransactionsDAOimpl.class);
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

    @Override
    public Integer insert(Object transaction) {
        //doneTODO: SQL transaction: new transaction + update balance

        try (Connection conn = pool.getConnection();
        ) {
            conn.setAutoCommit(false);
            try (
                PreparedStatement ps = conn.prepareStatement(BUNDLE.getString("transactions.insertAndUpdate"), 1);
                PreparedStatement psUpdate = conn.prepareStatement(BUNDLE.getString("accounts.updateBalanceByAccountId"));
                PreparedStatement psUpdBalance2 = conn.prepareStatement(BUNDLE.getString("accounts.updateBalanceIfLocal"));
            ){
                Transaction tr = (Transaction) transaction;
                LOGGER.info("Got [transaction]: " + tr + "; Trying to insert it to db.");

                ps.setString    (1, tr.getCreditAccount());
                ps.setBigDecimal(2, tr.getAmount());
                ps.setDate      (3, java.sql.Date.valueOf((tr.getDate()  )));
                ps.setString    (4, encodeUTF8KillCharsNotBMP(tr.getDescription())); //TODO: convert longSymbols (₴) to UTF8, Crashes on sql.insert otherwise
                ps.setBigDecimal(5, tr.getAmount());
                ps.setInt       (6, tr.getDtAccount().getId());
                LOGGER.info("PS[1]: " + ps.toString());
                ps.executeUpdate();

                psUpdate.setBigDecimal(1, tr.getAmount());
                psUpdate.setInt(2, tr.getDtAccount().getId());
                LOGGER.info("PS[2]: " + psUpdate.toString());
                psUpdate.executeUpdate();

                psUpdBalance2.setString(1, tr.getCreditAccount());
                psUpdBalance2.setBigDecimal(2, tr.getAmount());
                LOGGER.info("PS[3]: " + psUpdBalance2.toString());
                psUpdBalance2.executeUpdate();

                conn.commit();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    LOGGER.debug( PrintResultSet.getDump(rs));
                    rs.next();
                    return rs.getInt(1);
                } catch (SQLException e){
                    LOGGER.error("SQL ex. Could not insert new transaction", e);
                    return 0;
                }
            }
        } catch (SQLException e) {
            //Here - Most probably - because of negative balance. Return some code
            LOGGER.error("SQL exception [MySQLIntegrityConstraintViolationException]? - Expected when insufficient funds for transaction ", e);
            return -1;
        }
        //return -1;
    }


    @Override
    public List<Transaction> getListByAccountId(Integer accountId, boolean forDebit) throws MySqlPoolException {
        String QS = (forDebit) ?
                /*=DEBIT */ BUNDLE.getString("transactions.getDtByAccountId"):
                /*=CREDIT*/ BUNDLE.getString("transactions.getCrByAccountId");

        List<Transaction> resultList = new ArrayList<>();
        try (Connection conn = pool.getConnection(); PreparedStatement ps = conn.prepareStatement(QS,1);) {
            ps.setInt(1, accountId);
            LOGGER.info("PpepSt:" + ps);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Transaction transaction = new Transaction(
                        new Account(null, accountId.toString(), false),
                        rs.getString("acc_number"),
                        rs.getBigDecimal("amount"),
                        rs.getDate("date").toLocalDate(),
                        rs.getString("description")
                    );
                    transaction.setId(rs.getInt("id_trans"));
                    resultList.add(transaction);
                }
                rs.close();
                return resultList;
            } catch (SQLException e) {
                LOGGER.error("SQLex." + e.toString());
            }
        } catch (SQLException e) {
            LOGGER.error("Pool Exception.", e);
            throw new MySqlPoolException("SQL exception in " + TransactionsDAOimpl.class, e);
        } catch (Exception e) {
            LOGGER.error("General Exception.", e);
        }

        return null;
    }






    @Override
    public boolean update(int id, Entity data) {
        return false;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public Entity getById(Integer id) {
        return null;
    }


}
