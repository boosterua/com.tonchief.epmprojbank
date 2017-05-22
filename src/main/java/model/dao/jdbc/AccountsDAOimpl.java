package model.dao.jdbc;

import model.dao.AccountsDAO;
import model.dao.connection.ConnectionToDB;
import model.dao.exceptions.ExceptionDAO;
import model.dao.exceptions.MySqlPoolException;
import model.dto.Account;
import model.dto.Entity;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;


public class AccountsDAOimpl implements AccountsDAO {
    //TODO Singleton!!

    private final ResourceBundle accountsPS = ResourceBundle.getBundle("database.psqueries");
    private final Logger logger = Logger.getLogger(AccountsDAOimpl.class);

    public boolean insert(Entity acct) throws Exception {
        // TODO - read ~ logger - passing value / error. Add data here.
        logger.info("Insert into accounts: " + acct);

        // TODO QQQ: Try with resources - how to return connection back to pool
        try (
//          Connection conn = new DBConnection().getConnection();
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
        }

        return false;

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


    public boolean update(int id, Entity data) throws ExceptionDAO {
        return false;
    }

    public boolean delete(int id) throws ExceptionDAO {
        return false;
    }

    public Entity getById(int id) throws ExceptionDAO {
        return null;
    }

    public List findAll() throws ExceptionDAO {
        return null;
    }

    @Override
    public boolean isBlocked(Account account) throws MySqlPoolException, SQLException {
        logger.info("fetching isBlocked for " + account + " id:" + account.getId());
        try (
                Connection conn = ConnectionToDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(accountsPS.getString("accounts.isBlocked"), 1);
        ) {
            logger.info("got conn, acct id:" + account.getId());
            ps.setInt(1, account.getId());
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                boolean res = rs.getBoolean(1);
                ConnectionToDB.closeAll(conn, ps, rs);
                return res;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

        }
        return false;
    }


    @Override
    public boolean setblock(Account account, boolean block) throws MySqlPoolException {
        logger.info("setting isBlocked=true for " + account);
        try (
                Connection conn = ConnectionToDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(accountsPS.getString("accounts.setblock"), 1);
        ) {
            ps.setBoolean(1, block);
            ps.setInt(2, account.getId());
            return (ps.executeUpdate() != 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
