package model.dao.jdbc;

import model.dao.interfaces.UsersDAO;
import model.entity.Entity;

import java.util.List;

/**
 * Created by tonchief on 05/21/2017.
 */
public class UsersDAOimpl implements UsersDAO {

    private static UsersDAOimpl instance = null;

    public static UsersDAOimpl getInstance() {
        if(instance==null)
            instance = new UsersDAOimpl();
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
