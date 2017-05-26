package model.dao.jdbc;

import model.dao.interfaces.CardsDAO;
import model.dao.connection.DataSource;
import model.entity.Card;
import model.entity.Entity;
import model.services.Admin;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;


public class CardsDAOimpl implements CardsDAO {



    private static CardsDAOimpl instance = null;
    private final ResourceBundle resBundle = ResourceBundle.getBundle("database.psqueries2");
    private final Logger logger = Logger.getLogger(AccountsDAOimpl.class);
    private BasicDataSource pool = DataSource.getInstance().getBds();
    private static final int ID = 1;
    private static final int NUM = 2;
    private static final int EXP = 3;
    private static final int FID = 4;
    private static final int AID = 5;

    private CardsDAOimpl() { }

    public static synchronized CardsDAOimpl getInstance() {
        if (instance == null)
            instance = new CardsDAOimpl();
        return instance;
    }


    public int insert(Entity eCard) {
        logger.info("Insert into [cards]: " + eCard);

        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(BUNDLE.getString("cards.insert"), 1);
        ) {
            Card card = (Card) eCard;
            logger.info("Params from account passed:(" + card.toString() + ")");
                //  cards.insert=INSERT INTO cards (number, exp_date, fee_id, account_id) VALUES (?,TIMESTAMP(?),?,?);

            ps.setString(1, card.getName());
            ps.setDate  (2, java.sql.Date.valueOf((card.getExpDate()  )));
            ps.setInt   (3, card.getFeeId());
            ps.setInt   (4, card.getAccountId());

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
        throw new UnsupportedOperationException();
    }

    public Entity getById(int id) {
        try(ResultSet rs = UtilDAO.getRsById(id, BUNDLE.getString("cards.getById"))){
            rs.next();
            Card card= new Card();
            card.setId(rs.getInt(1));
            card.setName("" + rs.getLong(2));
            card.setExpDate(rs.getDate(3).toLocalDate());
            card.setFeeId(rs.getInt(4));
            card.setClientId(rs.getInt(5));
            rs.close();
            return card;
        } catch (Exception e) {
            logger.error("getRsById." + e.toString());
        }
        return null;
    }

    @Override
    public List listCardsOfType() {
        return null;
    }

    @Override
    public List listClientsOfType() {
        return null;
    }

    @Override
    public int getByCardNumber(long cardNum) {
        try(ResultSet rs = UtilDAO.getRsById(cardNum, BUNDLE.getString("cards.getByCardNumber"))){
            return rs.next() ? rs.getInt(1) : 0;
        } catch (Exception e) {
            logger.error("getRsById." + e.toString());
        }
        return 0;
    }
}
