package model.dao.jdbc;

import model.dao.CardsDAO;
import model.dao.connection.DataSource;
import model.dto.Entity;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.ResourceBundle;


public class CardsDAOimpl implements CardsDAO {

    private static CardsDAOimpl instance = null;
    private final ResourceBundle resBundle = ResourceBundle.getBundle("database.psqueries");
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

    public int insert(Entity tdata) {
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
}
