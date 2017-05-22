package model.dao.connection;

import model.dao.exceptions.MySqlPoolException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionToDB {
    private static final Logger LOGGER = Logger.getLogger(ConnectionToDB.class);
    private static final String SQLEX = "SQLException, full stack trace:";

    public ConnectionToDB() throws MySqlPoolException, SQLException {
    }

    public static Connection getConnection() throws SQLException, MySqlPoolException {
        Connection conn = null;
        Statement st = null;
        ResultSet res = null;
        try {
            LOGGER.debug("Attempting to borrow connection from pool");
            conn = (Connection) DBPool.pool.borrowObject();
            if (conn != null)
                LOGGER.debug("Connection received");
            else
                LOGGER.error("Unable to get Connection from pool");
            return conn;
            /*st = conn.createStatement();
            res = st.executeQuery("");
            while (res.next()) {
                String someRecord = String.valueOf(res.getInt(1))+", "+
                        String.valueOf(res.getString(2))+", "+ res.getInt(3);
            }*/
        } catch (SQLException e) {
            LOGGER.error(SQLEX, e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Failed to get conn from pool", e);
            throw new MySqlPoolException("Failed to borrow connection from the pool", e);
        } finally {
            safeClose(res);
            safeClose(st);
            safeClose(conn);
        }
    }

    public static void closeAll(Connection conn, Statement st, ResultSet rs) {
        safeClose(rs);
        safeClose(st);
        safeClose(conn);
    }

    private static void safeClose(Connection conn) {
        if (conn != null) {
            try {
                DBPool.pool.returnObject(conn);
            } catch (Exception e) {
                LOGGER.warn("Failed to return the connection to the pool", e);
            }
        }
    }

    private static void safeClose(ResultSet res) {
        if (res != null) {
            try {
                res.close();
            } catch (SQLException e) {
                LOGGER.warn("Failed to close databse resultset", e);
            }
        }
    }

    private static void safeClose(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                LOGGER.warn("Failed to close databse statement", e);
            }
        }
    }


}