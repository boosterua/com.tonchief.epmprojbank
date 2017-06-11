package model.dao.jdbc;

import model.dao.connection.DataSource;
import model.dao.exceptions.ExceptionDAO;
import model.dao.interfaces.UsersDAO;
import model.entity.Account;
import model.entity.Client;
import model.entity.Entity;
import model.utils.PrintResultSet;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import service.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UsersDAOimpl implements UsersDAO {

    private static UsersDAOimpl instance = null;
    private static final Logger LOGGER = Logger.getLogger(UsersDAOimpl.class);
    private BasicDataSource pool = DataSource.getInstance().getBds();
    private static final String UID = "id_client"; //1
    private static final String NAM = "name"; //2
    private static final String EML = "email"; //3;
    //    private static final String PWD = "password";//4;
    private static final String ROL = "role"; //5;
    private static final int ACC = 6;
    private static final String FEE = "fee_id";//6
    //Checked for fields equality b/w dao and db(v2), 2017-05-27

    public static UsersDAOimpl getInstance() {
        if(instance==null) instance = new UsersDAOimpl();
        return instance;
    }


    /**
     * @param  user
     * @return int : userId obtained from db
     */
    public Integer insert(Object user) {
        //TO+DO: change new user registration to include new account creation at the time of submitting application
        //TODO - Redesign DB, set email - as id (unique) -> catch exception &

        LOGGER.info("Insert into [clients] - [user] passed by account:" + user);

        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(BUNDLE.getString("clients.insert"), 1);
        ) {
            User client = (User) user;

            ps.setString    (1, client.getName());
            ps.setString    (2, client.getEmail());
            ps.setString    (3, client.getPassword());
            ps.setLong      (4, client.getRole());
            ps.setInt       (5, client.getFeeId());

            LOGGER.info("PS: " + ps.toString());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                rs.next();
                // LOGGER.debug(PrintResultSet.getDump(rs));
                Integer newUserId = rs.getInt(1);
                LOGGER.info("New client Registration: ID="+newUserId);
                return newUserId;
            } finally {
                ps.close();
            }
        } catch (SQLException sqlEx) {
            if(isConstraintViolation(sqlEx)){
                return -23; //made up code to indicate existing login (email)
            }
            LOGGER.error("SQL exception", sqlEx);
        }

        return 0;
    }

    private static boolean isConstraintViolation(SQLException e) {
        return e.getSQLState().startsWith("23");
    }

    public Integer authenticateUser(String email, String password) {
        //TODO : HASH PASSWORDS. plain text for now - only for debugging purposes
        LOGGER.info("fetching User with given credentials: " + email + ":*****");
        if(email==null || email.isEmpty() || password==null || password.isEmpty()) return null;
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(BUNDLE.getString("clients.checkLoginPwd"),0);
        ){
            ps.setString(1, email);
            ps.setString(2, password);
            LOGGER.info("Trying P S:" + ps);

            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                Integer userId = rs.getInt(1);
                rs.close();
                return userId;
            } catch (SQLException e) {
                LOGGER.error("SQL ex." + e.toString());
            }
        } catch (SQLException e) {
            LOGGER.error("SQL exception. ", e);
            return -500;
            //TODO : 500. Connection to db is unavailable
        } catch (Exception e) {
            LOGGER.error("Fatal General Exception.", e);
        }
        return null;
    }

    /**
     *
     * @param role // if r<-1 - show all
     * @param blocked // if null - not used
     * @return List<Client>
     */
    public List<Client> getClientsByRoleOrBlockedSt(Long role, Boolean blocked){

        List<Client> resultList = new ArrayList<>();
        LOGGER.info("fetching Client Entities for Role or blocked:");
        String query="clients.getByRole";
        if(role<-1) query="clients.getAll";
        if(blocked!=null) query="clients.getByBlockedAcct";

        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(BUNDLE.getString(query), 0);
        ){
            if(role>=0) ps.setLong(1, role);
            if(blocked!=null) ps.setBoolean(1, blocked);
            LOGGER.info("Getting PS:" + ps); //+"\n"+ps.getMetaData()

            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    Client cl= new Client( rs.getInt(UID), rs.getString(NAM), rs.getString(EML), rs.getInt(ROL));
                    Integer acctId = rs.getInt("accounts.id_account");
                    Account account = (acctId!=null && acctId>0) ?
                      new Account(acctId,rs.getString("accounts.number"),rs.getBoolean("accounts.is_blocked"), rs.getInt(UID)):
                      new Account(0, "Unassigned", true);
                    /* Account has not been assigned for some reason : set value to zero to avoid NP ex, check within jsp */
                    cl.setAccount(account);
                    // Fee fee = new Fee(rs.getInt("fees.id_fee"), rs.getString("fees.name"));
                    cl.setFeeName(rs.getString("fees.name"));

                    LOGGER.info("getUsersBy... usr="+cl);
                    resultList.add(cl);
                }
                rs.close();
                return resultList;
            }
        } catch (SQLException e) {
            LOGGER.error("SQL  exception.", e);
        } catch (Exception e) {
            LOGGER.error( e);
        }
        return null;
    }

    public Client getDetailedById(Integer clientId) {

        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(BUNDLE.getString("clients.getDetailedDataById"), 1);
             //id_client,clients.name,email,password,role,fees.name
        ){
            ps.setInt(1, clientId);
            LOGGER.info("Got connection. Exec.PS:" + ps.toString());
            try (ResultSet rs = ps.executeQuery()) {
                if(!rs.next()) return null;
                Client cl= new Client(rs.getInt(UID), rs.getString(NAM), rs.getString(EML), rs.getInt(ROL));
                cl.setFeeName(rs.getString("fees.name"));
                cl.setFeeId(rs.getInt("fees.id_fee"));
                Account account = new Account(rs.getInt("accounts.id_account"),
                        rs.getString("accounts.number"),rs.getBoolean("accounts.is_blocked"));
                cl.setAccount(account);
                LOGGER.info(account);
                return cl;
            } catch (SQLException e) {
                LOGGER.error("SQLex." + e.toString());
            }
        } catch (SQLException e) {
            LOGGER.error("SQL exception.", e);
        } catch (Exception e) {
            LOGGER.error("Major General Exception.", e);
        }
        return null;
    }

    public Client getClientWithAccounts (Integer clientId) {
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(BUNDLE.getString("clients.getDetailedDataById"), 1);
        ){
            ps.setInt(1, clientId);
            LOGGER.info("Got connection. Exec.PS:" + ps.toString());
            try (ResultSet rs = ps.executeQuery()) {
                boolean firstRecord = true;
                Client cl = null;
                while(rs.next()) {
                    if(firstRecord) {
                        cl = new Client(rs.getInt(UID), rs.getString(NAM), rs.getString(EML), rs.getInt(ROL));
                        cl.setFeeName(rs.getString("fees.name"));
                        cl.setFeeId(rs.getInt("fees.id_fee"));
                        firstRecord=false;
                    }
                    LOGGER.debug(rs.getInt("accounts.id_account") + ":"+
                            rs.getString("accounts.number") + ":"+ rs.getBoolean("accounts.is_blocked"));
                    Account account = new Account(rs.getInt("accounts.id_account"),
                            rs.getString("accounts.number"), rs.getBoolean("accounts.is_blocked"));
                    LOGGER.debug("account:"+account);
                    cl.addAccount(account);
                    LOGGER.debug("Still here");
                }
                LOGGER.info(cl);
                return cl;
            }
        } catch (SQLException e) {
            LOGGER.error("SQL exception.", e);
        } catch (Exception e) {
            LOGGER.error("Major General Exception.", e);
        }
        return null;
    }

    @Override
    public Boolean setUserRole(Integer clientId, Long role) throws ExceptionDAO {
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(BUNDLE.getString("clients.setUserRole"), 1);
        ){
            ps.setLong(1, role);
            ps.setInt(2, clientId);
            LOGGER.info("Got connection. Exec.PS:" + ps.toString());
            return (ps.executeUpdate() != 0);
        } catch (SQLException e) {
            throw new ExceptionDAO(e);
        } catch (Exception e) {
            LOGGER.error("Major General Exception.", e);
        }
        return false;
    }


    /* For security reasons: Password is not passed / nor stored here! */
    public Entity getById(Integer cid) {
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(BUNDLE.getString("clients.getById"), 1);
        ){
            LOGGER.info("Got connection.");
            ps.setInt(1, cid);
            LOGGER.info("Trying PS:" + ps);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                Client cl= new Client( rs.getInt(UID), rs.getString(NAM), rs.getString(EML), rs.getInt(ROL) );
                cl.setFeeId(rs.getInt(FEE));
                rs.close();
                return cl;
            } catch (SQLException e) {
                LOGGER.error("SQLex." + e.toString());
            }
        } catch (SQLException e) {
            LOGGER.error("SQL exception.", e);
        } catch (Exception e) {
            LOGGER.error("Major General Exception.", e);
        }
        return null;
    }

@Deprecated
    public List<Client> getUsersByRole(Long role){

        List<Client> resultList = new ArrayList<>();
        LOGGER.info("fetching Client Entities for RoleId:" + role);
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(BUNDLE.getString("clients.getByRole"), 0);
        ){
            LOGGER.info("Got connection.");
            ps.setLong(1, role);
            LOGGER.info("Trying PS:" + ps);
            try (ResultSet rs = ps.executeQuery()) {   LOGGER.info(PrintResultSet.getDump(rs));
                while(rs.next()) {
                    Client cl= new Client( rs.getInt(UID), rs.getString(NAM), rs.getString(EML), rs.getInt(ROL));
                    Integer acctId = rs.getInt("accounts.id_account");
                    Account account = (acctId!=null && acctId>0) ?
                      new Account(acctId,rs.getString("accounts.number"),rs.getBoolean("accounts.is_blocked"), rs.getInt(UID)):
                      new Account(0, "Unassign"+"ed", true);
                    // Account has not been assigned for some reason : set value to zero to avoid NP ex, check within jsp
                    cl.setAccount(account);
                    LOGGER.info("getUsersByRole usr="+cl);
                    resultList.add(cl);
                }
                rs.close();
                return resultList;
            } catch (SQLException e) {
                LOGGER.error("SQL ex." + e.toString());
            }
        } catch (SQLException e) {
            LOGGER.error("SQL  exception.", e);
        } catch (Exception e) {
            LOGGER.error( e);
        }
        return null;
    }

    public boolean update(int id, Entity data) {
        throw new UnsupportedOperationException();
    }
    public boolean setRole(int id, Entity data) {
        throw new UnsupportedOperationException();
    }
    public boolean delete(long id) {
        throw new UnsupportedOperationException();
    }


}
