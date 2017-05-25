package model.dao.jdbc;

import model.dao.interfaces.CardsDAO;
import model.dao.connection.DataSource;
import model.entity.Card;
import model.entity.Entity;
import model.services.Admin;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;
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











    public static void main(String[] args) throws Exception {
        CardsDAOimpl cd = CardsDAOimpl.getInstance();
        Card card = new Card();
        System.out.println("BEG");
        card.setClientId(1);
        card = new Admin().issueNewCard(1); // goes directly down to insert
        card.setAccountId(1);
        System.out.println(card);
        System.exit(0);

        cd.insert(card);
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
//System.exit(0);
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
}
