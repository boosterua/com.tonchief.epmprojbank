package model.dao.jdbc;

import model.dao.connection.DataSource;
import model.dao.exceptions.ExceptionDAO;
import model.dao.exceptions.MySqlPoolException;
import model.dao.interfaces.AccountsDAO;
import model.entity.Account;
import model.entity.Entity;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* Replaced Apache commons pool to v2.
   For v1 - PS and RS has to be closed manually, while borrowed connection has to stay open when returned back to
   pool. Crashes otherwise.
   apache pool v1 would not let close connection manually.
   SQL exception: com.mysql.jdbc.exceptions.jdbc4.MySQLNonTransientConnectionException:
   No operations allowed after connection closed. */


public class AccountsDAOimpl implements AccountsDAO {

    private static AccountsDAOimpl instance = null;
    //  private final ResourceBundle BUNDLE = ResourceBundle.getBundle("database.psqueries");
    // Moved to I:EntityDAO
    private final static Logger LOGGER = Logger.getLogger(AccountsDAOimpl.class);
    private BasicDataSource pool = DataSource.getInstance().getBds();
    private static final String ID = "id_account";
    private static final String NUM = "number";
    private static final String BLK = "is_blocked";
    private static final String BAL = "balance";
    private static final String CLI = "client_id";
    //Checked for fields equality b/w dao and db(v2), 2017-05-27


    private AccountsDAOimpl() {
    }

    public static synchronized AccountsDAOimpl getInstance() {
        synchronized (AccountsDAOimpl.class) {
            if (instance == null) instance = new AccountsDAOimpl();
        }
        return instance;
    }

    @Override
    @Deprecated
    public Integer insert(Object account) throws ExceptionDAO {
        Account acct = (Account) account;
        LOGGER.info("Insert into [accounts]: " + acct);

        /* try with resources works perfectly with apache pool v2.
        Closing connection and prepSt automatically */
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(BUNDLE.getString ("accounts.insert"), 1);) {
            LOGGER.info("Got conn for insert. ");
            LOGGER.info("Params from account passed:(" + account.toString() + ")");

            ps.setString(1, acct.getName());
            ps.setBoolean(2, acct.getBlocked());
            ps.setInt(3, acct.getClientId());

            LOGGER.info("PS: " + ps.toString());

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                rs.next();
                return rs.getInt(1 + 0);
            } finally {
                ps.close();
            }
        } catch (SQLException e) {
            LOGGER.error("SQL.exception", e);
        }
        return 0;
    }

    @Override
    public Integer generate(int clientId, String acctPrefix, Boolean setBlocked) throws ExceptionDAO {
        LOGGER.info("generate new acct for cl_id=" + clientId + " & acctPrefix=" + acctPrefix);

        try (Connection conn = pool.getConnection(); PreparedStatement ps = conn.prepareStatement(BUNDLE.getString
                ("accounts.generate"), 1);) {
            ps.setInt(1, clientId);
            ps.setString(2, acctPrefix);
            LOGGER.info("PS: " + ps.toString());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                rs.next();
                Integer acctId = rs.getInt(1);
                if (setBlocked != null) setBlock(acctId, setBlocked);
                return rs.getInt(1);
            } finally {
                ps.close();
            }
        } catch (SQLException e) {
            LOGGER.error("SQL exception", e);
        }
        return 0;
    }

    @Override
    public boolean update(int id, Entity acct) throws ExceptionDAO {

        LOGGER.info("Update [accounts] for acct.id: " + id);
        try (Connection conn = pool.getConnection(); PreparedStatement ps = conn.prepareStatement(BUNDLE.getString
                ("accounts.update"), 0);) {
            LOGGER.info("Got connection for update. ");

            Account account = (Account) acct;
            LOGGER.info("Params from account passed:(" + account.toString() + ")");
            //UPDATE accounts SET number=(?), is_blocked=(?), clients_id=(?)  WHERE id_account = (?);
            LOGGER.info("** PS: " + ps.toString());

            ps.setString(1, account.getName());
            ps.setBoolean(2, account.getBlocked());
            ps.setInt(3, account.getClientId());
            ps.setBigDecimal(4, account.getBalance());
            ps.setInt(5, id);

            LOGGER.info("** PS: " + ps.toString());
            boolean resultOK = ps.executeUpdate() != 0;
            ps.close();
            return (resultOK);
        } catch (SQLException e) {
            LOGGER.error("SQL exception", e);
        } catch (Exception e) {
            LOGGER.error("Major Exception", e);
        }
        return false;
    }

    @Override
    public boolean delete(long id) throws ExceptionDAO {
        LOGGER.info("Account  sql delete for id=" + id);
        try (Connection conn = pool.getConnection(); PreparedStatement ps = conn.prepareStatement(BUNDLE.getString
                ("accounts.deleteById"), 0);) {
            LOGGER.info("got conn.");
            ps.setLong(1, id);
            LOGGER.info("Trying PS:" + ps);

            return ps.executeUpdate() != 0;

        } catch (SQLException e) {
            LOGGER.error("SQL Exception.", e);
            throw new ExceptionDAO(e);
        } catch (Exception e) {
            LOGGER.error("Fatal  General Exception.", e);
        }
        return false;
    }

    @Override
    public Entity getById(Integer id) throws ExceptionDAO {
        LOGGER.info("fetching Entity.Account for id:" + id);
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(BUNDLE.getString ("accounts.getById"), 1);) {
            LOGGER.info("got conn.");
            ps.setInt(1, id);
            LOGGER.info("Trying PS:" + ps);

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
                LOGGER.error("SQLex." + e.toString());
            }
        } catch (SQLException e) {
            LOGGER.error("SQL exception.", e);
        } catch (Exception e) {
            LOGGER.error("Fatal General Exception.", e);
        }
        return null;
    }

    @Deprecated
    public List<Account> findAllByClientId(Integer client) throws ExceptionDAO {
        List<Account> resultList = new ArrayList<>();
        LOGGER.info("fetching Account Entities for Userid:" + client);
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(BUNDLE.getString ("accounts.getByClient"), 0);) {
            LOGGER.info("Got connection.");
            ps.setInt(1, client);
            LOGGER.info("Trying PS:" + ps);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
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
                LOGGER.error("SQLex." + e.toString());
            }
        } catch (SQLException e) {
            LOGGER.error("SQL exception.", e);
        } catch (Exception e) {
            LOGGER.error("Fatal General Exception.", e);
        }
        return null;
    }

    @Override
    public Map<Integer, Account> findAccountsByClientId(Integer client) {
        Map<Integer, Account> resultMap = new HashMap<>();
        LOGGER.info("fetching Account Entities for Userid:" + client);
        try (Connection conn = pool.getConnection(); PreparedStatement ps = conn.prepareStatement(BUNDLE.getString
                ("accounts.getByClient"), 0);) {
            ps.setInt(1, client);
            LOGGER.info("Trying PS:" + ps);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Account acct = new Account();
                    Integer accountId = rs.getInt(ID);
                    acct.setId(accountId);
                    acct.setName(Long.toString(rs.getLong(NUM)));
                    acct.setBlock(rs.getBoolean(BLK));
                    acct.setClientId(rs.getInt(CLI));
                    acct.setBalance(rs.getBigDecimal(BAL));
                    resultMap.put(accountId, acct);
                }
                rs.close();
                return resultMap;
            } catch (SQLException e) {
                LOGGER.error("SQLex." + e.toString());
            }
        } catch (SQLException e) {
            LOGGER.error("SQL exception.", e);
        } catch (Exception e) {
            LOGGER.error("Fatal General Exception.", e);
        }
        return null;
    }


    @Override
    public boolean isBlocked(Account account) throws MySqlPoolException, SQLException {
        Boolean res = false;
        LOGGER.info("fetching isBlocked for " + account + " id:" + account.getId());
        try (Connection conn = pool.getConnection(); PreparedStatement ps = conn.prepareStatement(BUNDLE.getString
                ("accounts.isBlocked"), 0);) {
            LOGGER.info("got conn, acct id:" + account.getId());
            ps.setInt(1, account.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) res = rs.getBoolean(1);
            ps.close();
        } catch (Exception e) {
            throw new MySqlPoolException("Db Pool aquire failed for isBlocked.", e);
        }
        return res;
    }

    /* Only transfers entity state to db, while setBlock(byId:int) - explicitly sets block state to true */
    @Override
    public boolean setBlock(Account account) {
        LOGGER.info("setting isBlocked=(" + account.getBlocked() + ") for " + account);
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(BUNDLE.getString ("accounts.setBlock"), 0);) {
            ps.setBoolean(1, account.getBlocked());
            ps.setBoolean(3, !account.getBlocked());
            ps.setInt(2, account.getId());
            return (ps.executeUpdate() != 0);
        } catch (SQLException e) {
            LOGGER.error("", e);
        }
        return false;
    }

    @Override
    public boolean setBlock(int accId, boolean block) {
        LOGGER.info("setting isBlocked=" + block + " for accountId=" + accId);
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(BUNDLE.getString ("accounts.setBlock"), 0);) {
            ps.setBoolean(1, block);
            ps.setBoolean(3, !block);
            ps.setInt(2, accId);
            LOGGER.info("PS:" + ps);
            return (ps.executeUpdate() != 0);
        } catch (SQLException e) {
            LOGGER.error("", e);
        }
        return false;
    }


    @Override
    @Deprecated
    /* Replaced with different implementation: see generate method above */
    public Long getMaxNumByAccountNum(String like) throws ExceptionDAO {
        LOGGER.info("fetching Account Entity for id:" + like);
        try (Connection conn = pool.getConnection(); PreparedStatement ps = conn.prepareStatement(BUNDLE.getString
                ("accounts.getMaxByNumLike"), 1);) {
            ps.setString(1, like);
            LOGGER.info("Trying PS:" + ps);

            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getLong(1);
            } catch (SQLException e) {
                LOGGER.error("SQLex." + e.toString());
            }
        } catch (SQLException e) {
            LOGGER.error("SQL exception.", e);
        } catch (Exception e) {
            LOGGER.error("Fatal General Exception.", e);
        }
        return null;
    }

}


// DoneTODO - read ~ LOGGER - passing value / error. Add data here.
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


// TODO:ALL DAO - when closing PS - check for null - EVERYWHERE when it's not checked
