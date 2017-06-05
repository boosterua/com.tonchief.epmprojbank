package model.dao.jdbc;

import com.mysql.jdbc.exceptions.MySQLTimeoutException;
import model.dao.interfaces.AccountsDAO;
import model.dao.connection.DataSource;
import model.dao.exceptions.ExceptionDAO;
import model.dao.exceptions.MySqlPoolException;
import model.entity.Account;
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
//  private final ResourceBundle BUNDLE = ResourceBundle.getBundle("database.psqueries");
// Moved to I:EntityDAO
    private final Logger logger = Logger.getLogger(AccountsDAOimpl.class);
    private BasicDataSource pool = DataSource.getInstance().getBds();
    private static final int ID = 1;
    private static final int NUM = 2;
    private static final int BLK = 3;
    private static final int BAL = 4;
    private static final int CLI = 5;
    //Checked for fields equality b/w dao and db(v2), 2017-05-27


    private AccountsDAOimpl() { }

    public static synchronized AccountsDAOimpl getInstance() {
        synchronized (AccountsDAOimpl.class){
            if (instance == null)
                instance = new AccountsDAOimpl();
        }
        return instance;
    }

    @Override
    @Deprecated
    public Integer insert(Object account) throws ExceptionDAO {
        Account acct = (Account) account;
        logger.info("Insert into [accounts]: " + acct);

        /* try with resources works perfectly with apache pool v2.
        Closing connection and prepSt automatically */
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(BUNDLE.getString("accounts.insert"), 1);
        ) {
            logger.info("Got conn for insert. ");
            logger.info("Params from account passed:(" + account.toString() + ")");

            ps.setString(1, acct.getName());
            ps.setBoolean(2, acct.getBlocked());
            ps.setInt(3, acct.getClientId());

            logger.info("PS: " + ps.toString());

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                rs.next();
                return rs.getInt(1+0);
            } finally {
                if(ps!=null)
                    ps.close();
            }
        } catch (SQLException e) {
            logger.error("SQL.exception", e);
        }
        return 0;
    }

/*
    @Override
    public Integer generate(int clientId, String acctPrefix) throws ExceptionDAO {
        logger.info("generate new acct for cl_id=" + clientId + " & acctPrefix="+ acctPrefix);

        try (Connection conn = pool.getConnection();
        ) {
            conn.setAutoCommit(false);
//            PreparedStatement ps = conn.prepareStatement(BUNDLE.getString("accounts.generate"), 1);
            PreparedStatement ps = conn.prepareStatement(BUNDLE.getString("accounts.generate1"), 1);
            ps.setInt(1, clientId);
            logger.info("PS: " + ps.toString());
            ps.executeUpdate();

            PreparedStatement ps2 = conn.prepareStatement(BUNDLE.getString("accounts.generate2"), 1);
            ps2.setString(1, acctPrefix);
            logger.info("PS: " + ps2.toString());
            ps.executeUpdate();

//            ps.setString(2, acctPrefix);
//            logger.info("PS: " + ps.toString());
//            ps.executeUpdate();
            conn.commit();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                rs.next();
                return rs.getInt(1);
            } finally {
                if(ps!=null) ps.close();
            }
        } catch (SQLException e) {
            logger.error("SQL exception", e);
        }
        return 0;
    }*/

    @Override
    public Integer generate(int clientId, String acctPrefix) throws ExceptionDAO {
        logger.info("generate new acct for cl_id=" + clientId + " & acctPrefix="+ acctPrefix);

        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(BUNDLE.getString("accounts.generate"), 1);
        ) {
            ps.setInt(1, clientId);
            ps.setString(2, acctPrefix);
            logger.info("PS: " + ps.toString());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                rs.next();
                return rs.getInt(1);
            } finally {
                if(ps!=null) ps.close();
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
            ps.setBoolean(2, account.getBlocked());
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
    public Entity getById(Integer id) throws ExceptionDAO {
        logger.info("fetching Entity.Account for id:" + id);
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(BUNDLE.getString("accounts.getById"), 1);
             /*	prepareStatement(String sql, int autoGeneratedKeys)
Creates a default PreparedStatement object that has the capability to retrieve auto-generated keys.*/
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
//                rs.close();
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


    public List<Account> findAllByClientId(Integer client) throws ExceptionDAO {
        List<Account> resultList = new ArrayList<>();
        logger.info("fetching Account Entities for Userid:" + client);
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(BUNDLE.getString("accounts.getByClient"), 0);
        ){
            logger.info("Got connection.");
            ps.setInt(1, client);
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
                if(ps!=null) ps.close();
                return res;
            }
        } catch (Exception e) {
            new MySqlPoolException("Db Pool aquire failed for isBlocked.", e);
        }
        return false;
    }

    /* Only transfers entity state to db, while setBlock(byId:int) - explicitly sets block state to true */
    @Override
    public boolean setBlock(Account account) {
        logger.info("setting isBlocked=(" + account.getBlocked() + ") for " + account);
        try (
                Connection conn = pool.getConnection();
                PreparedStatement ps = conn.prepareStatement(BUNDLE.getString("accounts.setBlock"), 0);
        ){
            ps.setBoolean(1, account.getBlocked());
            ps.setInt(2, account.getId());
            return (ps.executeUpdate() != 0);
        } catch (SQLException e) {
            logger.error("", e);
        }
        return false;
    }

    public boolean setBlock(int accId, boolean block) {
        logger.info("setting isBlocked="+block+" for accountId=" + accId);
        try (
            Connection conn = pool.getConnection();
            PreparedStatement ps = conn.prepareStatement(BUNDLE.getString("accounts.setBlock"), 0);
        ){
            ps.setBoolean(1, block);
            ps.setInt(2, accId);
            return (ps.executeUpdate() != 0);
        } catch (SQLException e) {
            logger.error("", e);
        }
        return false;
    }



    @Override
    @Deprecated
    /* Replaced with different implementation: see generate method above */
    public Long getMaxNumByAccountNum(String like) throws ExceptionDAO {
        logger.info("fetching Account Entity for id:" + like);
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(BUNDLE.getString("accounts.getMaxByNumLike"), 1);
        ){
            ps.setString(1, like);
            logger.info("Trying PS:" + ps);

            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getLong(1);
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


@Deprecated
    /* Newer implementation, using Util DAO*/
    public Entity getByIdTWR(Integer id) throws ExceptionDAO, SQLException {

        logger.info("fetching Account Entity for id:" + id);
        try{
            ResultSet rs = UtilDAO.getRsById(id.longValue(),BUNDLE.getString("accounts.getById"));

        // System.out.println("pDfrom getByIdTWR"); model.utils.PrintResultSet.printDump(rs);
            rs.beforeFirst();
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


// TODO:ALL DAO - when closing PS - check for null - EVERYWHERE when it's not checked

