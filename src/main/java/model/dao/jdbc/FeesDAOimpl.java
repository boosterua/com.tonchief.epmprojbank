package model.dao.jdbc;

import model.dao.interfaces.FeesDAO;
import model.entity.Entity;

import java.util.List;

public class FeesDAOimpl implements FeesDAO {
//TODO full instantiation
    private static FeesDAOimpl instance = null; // Lazy instantiation
    private static final int ID = 1;
    private static final int NAM = 2;
    private static final int TRF = 3;
    private static final int NCF = 4;
    private static final int APR = 5;
    //Checked for fields equality b/w dao and db(v2), 2017-05-27

    public static FeesDAO getInstance() {
        if (instance==null)
            instance = new FeesDAOimpl();
        return instance;
    }


    public int insert(Entity tdata) {
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
