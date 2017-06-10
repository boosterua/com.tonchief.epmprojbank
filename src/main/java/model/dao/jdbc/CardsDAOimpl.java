package model.dao.jdbc;

import model.dao.connection.DataSource;
import model.dao.exceptions.ExceptionDAO;
import model.dao.exceptions.MySqlPoolException;
import model.dao.interfaces.CardsDAO;
import model.entity.Card;
import model.entity.Entity;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CardsDAOimpl implements CardsDAO {


    private static CardsDAOimpl instance = null;
    private static final Logger LOGGER = Logger.getLogger(CardsDAOimpl.class);
    private BasicDataSource pool = DataSource.getInstance().getBds();
    private static final String ID = "id_card";
    private static final String NUM = "number";
    private static final String EXP = "exp_date";
    private static final String FID = "fee_id";
    private static final String AID = "account_id";
    //Checked for fields equality b/w dao and db(v2), 2017-05-27

    private CardsDAOimpl() {
    }

    public static synchronized CardsDAOimpl getInstance() {
        if (instance == null)
            instance = new CardsDAOimpl();
        return instance;
    }


    public Integer insert(Object oCard) throws ExceptionDAO {
        LOGGER.info("Insert into [cards]: " + oCard);

        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(BUNDLE.getString("cards.insert"), 1);
        ) {
            Card card = (Card) oCard;
            LOGGER.info("Params from account passed:(" + card.toString() + ")");
            //  INSERT INTO cards (number, exp_date, fee_id, account_id) VALUES (?, ?, ?, ?);

            ps.setString(1, card.getName());
            ps.setDate(2, java.sql.Date.valueOf((card.getExpDate())));
            ps.setInt(3, card.getFeeId());
            ps.setInt(4, card.getAccountId());

            LOGGER.info("PS: " + ps.toString());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                rs.next();
                return rs.getInt(1); //rs.getLong(1)
            } finally {
                ps.close();
            }
        } catch (SQLException e) {
            LOGGER.error("SQL exception", e);
        }
        return 0;

    }

    public boolean update(int id, Entity data) {
        return false;
    }

    public boolean delete(long id) {
        throw new UnsupportedOperationException();
    }

    public Entity getById(Integer id) throws MySqlPoolException {
        try (Connection conn = pool.getConnection();
        ) {
            try (ResultSet rs = UtilDAO.getRsById(id.longValue(), BUNDLE.getString("cards.getById"))) {
                rs.next();
                Card card = new Card();
                card.setId(rs.getInt(1));
                card.setName("" + rs.getLong(2));
                card.setExpDate(rs.getDate(3).toLocalDate());
                card.setFeeId(rs.getInt(4));
                card.setClientId(rs.getInt(5));
                rs.close();
                return card;
            } catch (Exception e) {
                LOGGER.error("getRs By Id", e);
            }
            return null;
        } catch (SQLException e) {
            throw new MySqlPoolException("Pool Expt CardsDAO.getById",e);
        }
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
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(BUNDLE.getString("cards.getByCardNumber")) )
        {
            ps.setLong(1, cardNum);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        } catch (Exception e) {
            LOGGER.error("getRsById.", e);
        }
        return 0;
    }

    @Override
    public List<Card> getByAccountId(Integer aId) {
        List<Card> cards = new ArrayList<>();
        LOGGER.info("getByAccountId(int " + aId + ")");


        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     BUNDLE.getString("cards.getListByAccountId"));
        ) {
            ps.setLong(1, aId);
            LOGGER.info("Trying PS:" + ps);
            try (ResultSet rs = ps.executeQuery();){
                while (rs.next()) {
                    cards.add(new Card(rs.getInt(ID), rs.getString(NUM), rs.getDate(EXP).toLocalDate()  ));
                    //LocalDate.from(Instant.ofEpochMilli(rs.getDate(EXP).getTime()))
                }
                return cards;
            } catch (SQLException e) {
                LOGGER.error(e);
            }
        } catch (Exception e) {
            LOGGER.error("getRsById." + e.toString());
        }
        return null;

    }

    public Integer getNumCardsByClientId (Integer clId) {
        LOGGER.info("getNumCardsByClientId " + clId + " ");

        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(BUNDLE.getString("cards.getNumByClientId"));
        ) {
            ps.setInt(1, clId);
            LOGGER.info("PS:" + ps);
            try (ResultSet rs = ps.executeQuery();){
                rs.next();
                return rs.getInt(1);
            } catch (SQLException e) {
                LOGGER.error(e);
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return null;

    }

    public Integer getNumCardsByAccountId (Integer acctId) {
        LOGGER.info("getNumCardsByAccountId " + acctId + " ");

        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(BUNDLE.getString("cards.getNumByAccountId"));
        ) {
            ps.setInt(1, acctId);
            LOGGER.info("PS:" + ps);
            try (ResultSet rs = ps.executeQuery();){
                rs.next();
                return rs.getInt(1);
            } catch (SQLException e) {
                LOGGER.error(e);
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return null;
    }
}
/*
                try{
            ResultSet rs = UtilDAO.getRsById(aId.longValue(),
                    BUNDLE.getString("cards.getListByAccountId"));

            model.utils.PrintResultSet.printDump(rs);

            while (rs.next()){

                cards.add(new Card(rs.getInt(ID), rs.getString(NUM),
                        LocalDate.from(Instant.ofEpochMilli(rs.getDate(EXP).getTime()))));
            }
            rs.close();
            return cards;
*/

