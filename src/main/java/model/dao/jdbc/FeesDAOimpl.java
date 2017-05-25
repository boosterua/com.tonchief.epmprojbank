package model.dao.jdbc;

import model.dao.interfaces.FeesDAO;
import model.entity.Entity;

import java.util.List;

/**
 * Created by tonchief on 05/21/2017.
 */
public class FeesDAOimpl implements FeesDAO {
    private static FeesDAOimpl instance = null; // Lazy instantiation

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
