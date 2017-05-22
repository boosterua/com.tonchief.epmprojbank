package model.dao.jdbc;

import com.mysql.jdbc.exceptions.MySQLTimeoutException;
import model.dao.AccountsDAO;
import model.dao.connection.ConnectionToDB;
import model.dao.connection.DBPool;
import model.dao.exceptions.ExceptionDAO;
import model.dao.exceptions.MySqlPoolException;
import model.dto.Account;
import model.dto.Entity;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/*TEMPLATE:
        logger.info("fetching .... for " + account + " id:" + account.getId());
        try  {
            Connection conn = (Connection) DBPool.pool.borrowObject();
            PreparedStatement ps = conn.prepareStatement(accountsPS.getString("accounts."), 1);
            logger.info("got conn, acct id:" + account.getId());
            ps.setInt(1, account.getId());
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                boolean res = rs.getBoolean(1);
                DBPool.pool.returnObject(conn);
                return res;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
* */
public class AccountsDAOimpl implements AccountsDAO {
    //TODO Singleton!!

    private static AccountsDAOimpl instance = null;
    private final ResourceBundle accountsPS = ResourceBundle.getBundle("database.psqueries");
    private final Logger logger = Logger.getLogger(AccountsDAOimpl.class);

    private AccountsDAOimpl() {
    }

    ;

    public static synchronized AccountsDAOimpl getInstance() {
        if (instance == null)
            instance = new AccountsDAOimpl();
        return instance;
    }


    public Integer insert(Entity acct) throws Exception {
        // TODO - read ~ logger - passing value / error. Add data here.
        // TODO QQQ: Try with resources - how to return connection back to pool
        // TODO QQQ: Singleton - what's best impl for this exact var
        /* TODO QQQ: Which is better? try (ResultSet rs = ps.getGeneratedKeys()) { rs.next(); return true;}     --OR--  return (ps.executeUpdate() != 0);
        TODO QQQ: Если запрашиваемого клиента/счета и т.п. не существует - правильнее вернуть null и проверить это в логике или бросить эксепшн, который поймать и обработать через try/catch ?
         */

        logger.info("Insert into [accounts]: " + acct);
        try {
            Connection conn = (Connection) DBPool.pool.borrowObject();
            PreparedStatement ps = conn.prepareStatement(accountsPS.getString("accounts.insert"), 1);
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
        return null;

/*        try (
//          Connection conn = new DBConnectionSingle().getConnection();
//          Connection conn = DataSourceMgr.getConnection();
            Connection conn = new ConnectionToDB().getConnection();
            PreparedStatement ps = conn.prepareStatement(accountsPS.getString("accounts.insert"), 1);
        ) {
            Account account = (Account) acct;
            ps.setString(1, account.getName());
            ps.setBoolean(2, account.getBlockedStatus());
            ps.setInt(3, account.getClientId());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                rs.next();
                return true; //rs.getLong(1)
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }*/


        /* Для СУБД, которые поддерживают "auto increment" поля

        *  добавляем запись...
            int rowCount = stmt.executeUpdate(
                "INSERT INTO Messages(Msg) VALUES ('Test')",
            Statement.RETURN_GENERATED_KEYS);

        // ... и получаем ключ
        ResultSet rs = stmt.getGeneratedKeys();
        rs.next();
        long primaryKey = rs.getLong(1);*/
    }


    public boolean update(int id, Entity acct) throws ExceptionDAO {

        logger.info("Update [accounts] for acct.id: " + id);
        try {
            Connection conn = (Connection) DBPool.pool.borrowObject();
            PreparedStatement ps = conn.prepareStatement(accountsPS.getString("accounts.update"), 1);
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

    public boolean delete(int id) throws ExceptionDAO {
        logger.info("fetching Account Entity for id:" + id);
        try {
            Connection conn = (Connection) DBPool.pool.borrowObject();
            PreparedStatement ps = conn.prepareStatement(accountsPS.getString("accounts.deleteById"), 1);
            logger.info("got conn.");
            ps.setInt(1, id);
            logger.info("Trying PS:" + ps);

            return ps.executeUpdate() != 0;

        } catch (SQLException e) {
            logger.error("SQL exception.", e);
            throw new ExceptionDAO(e);
        } catch (Exception e) {
            logger.error("Fatal General Exception.", e);
        }
        return false;
    }


    public Entity getById(int id) throws ExceptionDAO {
        logger.info("fetching Account Entity for id:" + id);
        try {
            Connection conn = (Connection) DBPool.pool.borrowObject();
            PreparedStatement ps = conn.prepareStatement(accountsPS.getString("accounts.getById"), 1);
            logger.info("got conn.");
            ps.setInt(1, id);
            logger.info("Trying PS:" + ps);

            try (ResultSet rs = ps.executeQuery()) {
                model.utils.PrintResultSet.printDump(rs);

                rs.next();
                Account acct = new Account();
                acct.setId(rs.getInt(1));
                acct.setName(Long.toString(rs.getLong(2)));
                acct.setBlock(rs.getBoolean(3));

                acct.setClientId(rs.getInt(4));
                DBPool.pool.returnObject(conn);
                ps.close();
                return acct;
            } catch (SQLException e) {
                logger.error("SQLex." + e.toString());
//                return null;
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
                DBPool.pool.returnObject(conn);
                ps.close();
//                ConnectionToDB.closeAll(conn, ps, rs);
                return res;
            }
        } /*catch (NullPointerException e) { // Will never get here, as NP is checked before trying PS. Put this catch here only for Sonar to be happy ;)
            logger.error("", e);
        } */ catch (Exception e) {
            logger.error("", e);
        } finally {

        }
        /*try ( Connection conn = ConnectionToDB.getConnection();
        PreparedStatement ps = conn.prepareStatement(accountsPS.getString("accounts.isBlocked"), 1);) {
            ps.setInt(1, account.getId());
                try (ResultSet rs = ps.executeQuery()) {
                    rs.next();
                    boolean res = rs.getBoolean(1);
                    ConnectionToDB.closeAll(conn, ps, rs);
                    return res;
                }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {}*/
        return false;
    }


    @Override
    public boolean setblock(Account account, boolean block) throws MySqlPoolException {
        logger.info("setting isBlocked=(" + block + ") for " + account);
        try (
                Connection conn = ConnectionToDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(accountsPS.getString("accounts.setblock"), 1);
        ) {
            ps.setBoolean(1, block);
            ps.setInt(2, account.getId());
            return (ps.executeUpdate() != 0);
        } catch (SQLException e) {
            logger.error("", e);
        }
        return false;
    }
}
