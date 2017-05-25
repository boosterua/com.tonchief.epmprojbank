package model.dao.jdbc;

import com.mysql.jdbc.exceptions.MySQLTimeoutException;
import model.dao.interfaces.AccountsDAO;
import model.dao.connection.DataSource;
import model.dao.exceptions.ExceptionDAO;
import model.dao.exceptions.MySqlPoolException;
import model.entity.Account;
import model.entity.Client;
import model.entity.Entity;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/* Replaced Apache commons pool to v2.
   For v1 - PS and RS has to be closed manually, while borrowed connection has to stay open when returned back to pool. Crashes otherwise.
   apache pool v1 would not let close connection manually.
   SQL exception: com.mysql.jdbc.exceptions.jdbc4.MySQLNonTransientConnectionException:
    No operations allowed after connection closed. */



public class AccountsDAOimpl implements AccountsDAO {

    private static AccountsDAOimpl instance = null;
//    private final ResourceBundle BUNDLE = ResourceBundle.getBundle("database.psqueries");
    private final Logger logger = Logger.getLogger(AccountsDAOimpl.class);
    private BasicDataSource pool = DataSource.getInstance().getBds();
    private static final int ID = 1;
    private static final int NUM = 2;
    private static final int BLK = 3;
    private static final int CLI = 4;
    private static final int BAL = 5;

    private AccountsDAOimpl() { }

    public static synchronized AccountsDAOimpl getInstance() {
        if (instance == null)
            instance = new AccountsDAOimpl();
        return instance;
    }

    @Override
    public int insert(Entity acct) throws Exception {

        logger.info("Insert into [accounts]: " + acct);

        // try with resources works perfectly with apache pool v2. Closing connection and prepSt automatically
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(BUNDLE.getString("accounts.insert"), 1);
        ) {
            logger.info("Got conn for insert. ");

            Account account = (Account) acct;
            logger.info("Params from account passed:(" + account.toString() + ")");

            ps.setString(1, account.getName());
            ps.setBoolean(2, account.getBlockedStatus());
            ps.setInt(3, account.getClientId());

            logger.info("PS: " + ps.toString());

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                rs.next();
                return rs.getInt(1); //rs.getLong(1)
            } finally {
                ps.close();
            }
        } catch (SQLException e) {
            logger.error("SQL exception", e);
        }
        return 0;
    }

    @Override
    public boolean update(int id, Entity acct) throws ExceptionDAO {

        logger.info("Update [accounts] for acct.id: " + id);
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(BUNDLE.getString("accounts.update"), 0);
        ) {
            logger.info("Got connection for update. ");

            Account account = (Account) acct;
            logger.info("Params from account passed:(" + account.toString() + ")");
            //UPDATE accounts SET number=(?), is_blocked=(?), clients_id=(?)  WHERE id_account = (?);
            logger.info("** PS: " + ps.toString());

            ps.setString(1, account.getName());
            ps.setBoolean(2, account.getBlockedStatus());
            ps.setInt(3, account.getClientId());
            ps.setBigDecimal(4, account.getBalance());
            ps.setInt(5, id);

            logger.info("** PS: " + ps.toString());

            return (ps.executeUpdate() != 0);

        } catch (SQLException e) {
            logger.error("SQL exception", e);
        } catch (Exception e) {
            logger.error("Major Exception", e);
        }
        return false;
    }

    @Override
    public boolean delete(long id) throws ExceptionDAO {
        logger.info("Account  sql delete for id=" + id);
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(BUNDLE.getString("accounts.deleteById"), 0);
        ){
            logger.info("got conn.");
            ps.setLong(1, id);
            logger.info("Trying PS:" + ps);

            return ps.executeUpdate() != 0;

        } catch (SQLException e) {
            logger.error("SQL Exception.", e);
            throw new ExceptionDAO(e);
        } catch (Exception e) {
            logger.error("Fatal  General Exception.", e);
        }
        return false;
    }

    @Override
    public Entity getById(int id) throws ExceptionDAO {
        logger.info("fetching Account Entity for id:" + id);
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(BUNDLE.getString("accounts.getById"), 0);
        ){
            logger.info("got conn.");
            ps.setInt(1, id);
            logger.info("Trying PS:" + ps);

            try (ResultSet rs = ps.executeQuery()) {
                    //  model.utils.PrintResultSet.printDump(rs);
                rs.next();
                Account acct = new Account();
                acct.setId(rs.getInt(ID));
                acct.setName(Long.toString(rs.getLong(NUM)));
                acct.setBlock(rs.getBoolean(BLK));
                acct.setClientId(rs.getInt(CLI));
                acct.setBalance(rs.getBigDecimal(BAL));
                rs.close();
                return acct;
            } catch (SQLException e) {
                logger.error("SQLex." + e.toString());
            }
        } catch (SQLException e) {
            logger.error("SQL exception.", e);
        } catch (Exception e) {
            logger.error("Fatal General Exception.", e);
        }
        return null;
    }


    public List findAllByClient(Client client) throws ExceptionDAO {
        List<Account> resultList = new ArrayList<>();
        logger.info("fetching Account Entities for Userid:" + client.getId());
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(BUNDLE.getString("accounts.getByClient"), 0);
        ){
            logger.info("Got connection.");
            ps.setInt(1, client.getId());
            logger.info("Trying PS:" + ps);

            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    Account acct = new Account();
                    acct.setId(rs.getInt(ID));
                    acct.setName(Long.toString(rs.getLong(NUM)));
                    acct.setBlock(rs.getBoolean(BLK));
                    acct.setClientId(rs.getInt(CLI));
                    acct.setBalance(rs.getBigDecimal(BAL));
                    resultList.add(acct);
                }
                rs.close();
                return resultList;
            } catch (SQLException e) {
                logger.error("SQLex." + e.toString());
            }
        } catch (SQLException e) {
            logger.error("SQL exception.", e);
        } catch (Exception e) {
            logger.error("Fatal General Exception.", e);
        }
        return null;
    }



    @Override
    public boolean isBlocked(Account account) throws MySqlPoolException, SQLException {
        logger.info("fetching isBlocked for " + account + " id:" + account.getId());
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(BUNDLE.getString("accounts.isBlocked"), 0);
        ){
            if (conn == null) throw new MySqlPoolException("No connection", new MySQLTimeoutException());
            logger.info("got conn, acct id:" + account.getId());
            ps.setInt(ID, account.getId());
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                boolean res = rs.getBoolean(1);
                ps.close();
                return res;
            }
        } catch (Exception e) {
            new MySqlPoolException("Db Pool aquire failed for isBlocked.", e);
        }
        return false;
    }

    @Override
    public boolean setBlock(Account account) throws MySqlPoolException {
        logger.info("setting isBlocked=(" + account.getBlockedStatus() + ") for " + account);
        try (
                Connection conn = pool.getConnection();
                PreparedStatement ps = conn.prepareStatement(BUNDLE.getString("accounts.setblock"), 0);
        ){
            ps.setBoolean(1, account.getBlockedStatus());
            ps.setInt(2, account.getId());
            return (ps.executeUpdate() != 0);
        } catch (SQLException e) {
            logger.error("", e);
        }
        return false;
    }


    /* Newer implementation, using Util DAO*/
    public Entity getByIdTWR(int id) throws ExceptionDAO, SQLException {

        logger.info("fetching Account Entity for id:" + id);
        try(ResultSet rs = UtilDAO.getRsById(id, BUNDLE.getString("accounts.getById"))){
                // System.out.println("pDfrom getByIdTWR"); model.utils.PrintResultSet.printDump(rs);
            rs.next();
            Account acct = new Account();
            acct.setId(rs.getInt(1));
            acct.setBlock(rs.getBoolean(3));
            acct.setName(Long.toString(rs.getLong(2)));

            acct.setClientId(rs.getInt(4));
            rs.close();
            return acct;
            } catch (Exception e) {
                logger.error("getRsById." + e.toString());
            }
       return null;
    }
}


// DoneTODO - read ~ logger - passing value / error. Add data here.
// DoneTODO QQQ: Try with resources - how to return connection back to pool
// DoneTODO QQQ: Singleton - what's best impl for this exact var
// CancelledTODO: update all indexes in db - to bigint (Long);


/* Для СУБД, которые поддерживают "auto increment" поля -  добавляем запись...
        int rowCount = stmt.executeUpdate(
            "INSERT INTO Messages(Msg) VALUES ('Test')", Statement.RETURN_GENERATED_KEYS);
    // ... и получаем ключ
    ResultSet rs = stmt.getGeneratedKeys();
    rs.next();
    long primaryKey = rs.getLong(1);
*/

/* // OLDER Impl (apache pool v1)
    @Override
    public boolean isBlocked(Account account) throws MySqlPoolException, SQLException {
        logger.info("fetching isBlocked for " + account + " id:" + account.getId());
        Connection conn = null;
        try {
            conn = (Connection) DBPool.pool.borrowObject();
            if (conn == null) throw new MySqlPoolException("No connection", new MySQLTimeoutException());
        } catch (Exception e) {
            new MySqlPoolException("Db Pool aquire failed for isBlocked.", e);
        }
        try (
                PreparedStatement ps = conn.prepareStatement(BUNDLE.getString("accounts.isBlocked"), 1);
        ) {
            logger.info("got conn, acct id:" + account.getId());
            ps.setInt(1, account.getId());
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                boolean res = rs.getBoolean(1);
                ps.close();
                return res;
            }
        }  catch (Exception e) {
            logger.error("", e);
        }
        return false;
    }

 */
