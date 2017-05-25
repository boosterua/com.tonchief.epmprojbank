package model.dao.jdbc;

import model.dao.interfaces.TransactionsDAO;
import model.entity.Entity;

import java.util.List;


public class TransactionsDAOimpl implements TransactionsDAO {

    private static TransactionsDAOimpl instance = null;

    public static TransactionsDAOimpl getInstance() {
        if(instance==null)
            instance = new TransactionsDAOimpl();
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
