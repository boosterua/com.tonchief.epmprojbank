package model.dao.jdbc;

import com.mysql.jdbc.exceptions.MySQLTimeoutException;
import model.dao.AccountsDAO;
import model.dao.connection.DataSource;
import model.dao.exceptions.ExceptionDAO;
import model.dao.exceptions.MySqlPoolException;
import model.dto.Account;
import model.dto.Entity;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/* Replaced Apache commons pool to v2. */
/* apache pool v1 would not let close connection manually.
    SQL exception.
            com.mysql.jdbc.exceptions.jdbc4.MySQLNonTransientConnectionException: No operations allowed after connection closed. */

// DoneTODO - read ~ logger - passing value / error. Add data here.
// DoneTODO QQQ: Try with resources - how to return connection back to pool
// DoneTODO QQQ: Singleton - what's best impl for this exact var        // TODO: update all indexes in db - to bigint (Long);
/* TODO QQQ: Which is better? try (ResultSet rs = ps.getGeneratedKeys()) { rs.next(); return true;}     --OR--  return (ps.executeUpdate() != 0);
TODO QQQ: Если запрашиваемого клиента/счета и т.п. не существует - правильнее вернуть null и проверить это в логике или бросить эксепшн, который поймать и обработать через try/catch ?
 */

public class AccountsDAOimpl implements AccountsDAO {

    private static AccountsDAOimpl instance = null;
    private final ResourceBundle accountsPS = ResourceBundle.getBundle("database.psqueries");
    private final Logger logger = Logger.getLogger(AccountsDAOimpl.class);
    private BasicDataSource pool = DataSource.getInstance().getBds();

    private AccountsDAOimpl() { }

    public static synchronized AccountsDAOimpl getInstance() {
        if (instance == null)
            instance = new AccountsDAOimpl();
        return instance;
    }


    public int insert(Entity acct) throws Exception {

        logger.info("Insert into [accounts]: " + acct);
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(accountsPS.getString("accounts.insert"), 1);
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
                //DBPool.pool.returnObject(conn);
            }
        } catch (SQLException e) {
            logger.error("SQL exception", e);
        }
        return 0;
    }


    public boolean update(int id, Entity acct) throws ExceptionDAO {

        logger.info("Update [accounts] for acct.id: " + id);
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(accountsPS.getString("accounts.update"), 0);
        ) {
            logger.info("Got connection for update. ");

            Account account = (Account) acct;
            logger.info("Params from account passed:(" + account.toString() + ")");
            //UPDATE accounts SET number=(?), is_blocked=(?), clients_id=(?)  WHERE id_account = (?);
            ps.setString(1, account.getName());
            ps.setBoolean(2, account.getBlockedStatus());
            ps.setInt(3, account.getClientId());
            ps.setInt(4, id);

            logger.info("PS: " + ps.toString());

            return (ps.executeUpdate() != 0);

        } catch (SQLException e) {
            logger.error("SQL exception", e);
        } catch (Exception e) {
            logger.error("Major Exception", e);
        }
        return false;
    }

    public boolean delete(long id) throws ExceptionDAO {
        logger.info("Account  sql delete for id=" + id);
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(accountsPS.getString("accounts.deleteById"), 0);
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


    public Entity getById(int id) throws ExceptionDAO {
        logger.info("fetching Account Entity for id:" + id);
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(accountsPS.getString("accounts.getById"), 0);
        ){
            logger.info("got conn.");
            ps.setInt(1, id);
            logger.info("Trying PS:" + ps);

            try (ResultSet rs = ps.executeQuery()) {
//                model.utils.PrintResultSet.printDump(rs);

                rs.next();
                Account acct = new Account();
                acct.setId(rs.getInt(1));
                acct.setName(Long.toString(rs.getLong(2)));
                acct.setBlock(rs.getBoolean(3));
                acct.setClientId(rs.getInt(4));
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


    public List findAll() throws ExceptionDAO {
        return new ArrayList();
    }

    @Override
    public boolean isBlocked(Account account) throws MySqlPoolException, SQLException {
        logger.info("fetching isBlocked for " + account + " id:" + account.getId());
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(accountsPS.getString("accounts.isBlocked"), 0);
        ){
            if (conn == null) throw new MySqlPoolException("No connection", new MySQLTimeoutException());
            logger.info("got conn, acct id:" + account.getId());
            ps.setInt(1, account.getId());
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
    public boolean setblock(Account account, boolean block) throws MySqlPoolException {
        logger.info("setting isBlocked=(" + block + ") for " + account);
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(accountsPS.getString("accounts.isBlocked"), 0);
        ){
            ps.setBoolean(1, block);
            ps.setInt(2, account.getId());
            return (ps.executeUpdate() != 0);
        } catch (SQLException e) {
            logger.error("", e);
        }
        return false;
    }




    public Entity getByIdTWR(int id) throws ExceptionDAO, SQLException {

        logger.info("fetching Account Entity for id:" + id);
        try(ResultSet rs = UtilDAO.getRsById(id, accountsPS.getString("accounts.getById"))){
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
                PreparedStatement ps = conn.prepareStatement(accountsPS.getString("accounts.isBlocked"), 1);
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
