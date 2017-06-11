package model.dao.jdbc;

import model.dao.connection.DataSource;
import model.dao.exceptions.ExceptionDAO;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtilDAO {

    private static final Logger LOGGER = Logger.getLogger(UtilDAO.class);
    private static BasicDataSource pool = DataSource.getInstance().getBds();

    static ResultSet getRsById(Long id, String preparedStatementWithIdFirst) throws ExceptionDAO {
        LOGGER.info("fetching ... Entity for id:" + id);
        try (Connection conn = pool.getConnection(); PreparedStatement ps = conn.prepareStatement
                (preparedStatementWithIdFirst);) {
            LOGGER.info("Got connection from pool.");
            ps.setLong(1, id);
            LOGGER.info("Trying PS:" + ps);
            ResultSet rs = null;
            try {
                rs = ps.executeQuery();
//                model.utils.PrintResultSet.printDump(rs);
                return rs;
            } catch (SQLException e) {
                LOGGER.error("SQLex." + e.toString());
            }
        } catch (SQLException e) {
            LOGGER.error("SQL exception.", e);
        } catch (Exception e) {
            LOGGER.error("Fatal General Exception.", e);
        }
        LOGGER.error("NULL.");

        return null;
    }

    public static String encodeUTF8KillCharsNotBMP(String str) {
        return str.replaceAll("[^\u0000-\uFFFF]", "");
    }

}
