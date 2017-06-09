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

    private static final Logger logger = Logger.getLogger(UtilDAO.class);
    private static BasicDataSource pool = DataSource.getInstance().getBds();

    static ResultSet getRsById(Long id, String preparedStatementWithIdFirst) throws ExceptionDAO {
        logger.info("fetching ... Entity for id:" + id);
        try (
            Connection conn = pool.getConnection();
            PreparedStatement ps = conn.prepareStatement (preparedStatementWithIdFirst);
        ){
            logger.info("Got connection from pool.");
            ps.setLong(1, id);
            logger.info("Trying PS:" + ps);
            ResultSet rs = null;
            try {
                rs = ps.executeQuery();
//                model.utils.PrintResultSet.printDump(rs);
                return rs;
            } catch (SQLException e) {
                logger.error("SQLex." + e.toString());
            }
        } catch (SQLException e) {
            logger.error("SQL exception.", e);
        } catch (Exception e) {
            logger.error("Fatal General Exception.", e);
        }
        logger.error("NULLLLLLLL.");

        return null;
    }

    public static String encodeUTF8KillCharsNotBMP(String str){
        return str.replaceAll("[^\u0000-\uFFFF]", "");
    }

}
