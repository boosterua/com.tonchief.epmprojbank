package model.dao.connection;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DataSourceMgr {
    private static final Logger logger = Logger.getLogger(DataSourceMgr.class);
    /**
     * Data source object used to get db connection.
     */
    private static DataSource ds;
    private static final String SQLEX = "SQLException, full stack trace:";

    static {
        try {
            logger.debug("Attempting to get connection pool");
            Context initialContext = new InitialContext();
            Context envContext = (Context) initialContext.lookup("java:/comp/env");
            ds = (DataSource) envContext.lookup("jdbc/epmprojbank");
            if (ds != null)
                logger.debug("Connection pool received");
            else
                logger.error("Unable to get Connection pool");
        } catch (NamingException e) {
            logger.error("NamingException thrown, full stack trace:", e);
        }
    }

    private DataSourceMgr() {

    }

    public static Connection getConnection() {
        Connection connection = null;

        try {
            logger.debug("Trying to get connection");
            connection = ds.getConnection();
            if (connection != null)
                logger.debug("Connection received");
            else
                logger.error("Connection could NOT be received");
        } catch (SQLException e) {
            logger.error(SQLEX, e);
        }

        return connection;
    }

    public static void closeAll(Connection con, Statement stm, ResultSet rs) {
        try {
            if (con != null)
                con.close();
        } catch (SQLException e) {
            logger.error(SQLEX, e);
        } finally {
            try {
                if (stm != null)
                    stm.close();
            } catch (SQLException e) {
                logger.error(SQLEX, e);
            } finally {
                try {
                    if (rs != null)
                        rs.close();
                } catch (SQLException e) {
                    logger.error(SQLEX, e);
                }

            }
        }
    }
}
