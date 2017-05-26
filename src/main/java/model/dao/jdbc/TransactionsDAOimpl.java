package model.dao.jdbc;

import model.dao.connection.DataSource;
import model.dao.interfaces.TransactionsDAO;
import model.entity.Card;
import model.entity.Entity;
import model.entity.Transaction;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;


public class TransactionsDAOimpl  implements TransactionsDAO {

    private static TransactionsDAOimpl instance = null;
    private final ResourceBundle resBundle = ResourceBundle.getBundle("database.psqueries");
    private final Logger logger = Logger.getLogger(AccountsDAOimpl.class);
    private BasicDataSource pool = DataSource.getInstance().getBds();

    public static TransactionsDAOimpl getInstance() {
        if(instance==null)
            instance = new TransactionsDAOimpl();
        return instance;
    }

    public int insert(Entity transaction) {
        logger.info("Insert into [transaction]: " + transaction);

        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(BUNDLE.getString("cards.insert"), 1);
        ) {
            Transaction tr = (Transaction) transaction;
            logger.info("Params from account passed:(" + tr.toString() + ")");
            //  cards.insert=INSERT INTO cards (number, exp_date, fee_id, account_id) VALUES (?,TIMESTAMP(?),?,?);
//TODO: Fix this ps impl
/*
            ps.setString(1, tr.getName());
            ps.setDate  (2, java.sql.Date.valueOf((tr.getExpDate()  )));
            ps.setInt   (3, tr.getFeeId());
            ps.setInt   (4, tr.getAccountId());
*/
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

        return 0;
    }

    public boolean update(int id, Entity data) {
        return false;
    }

    public boolean delete(long id) {
        return false;
    }

    public Entity getById(int id) {
        return null;
    }


}
