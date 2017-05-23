package model.dao.jdbc;

import model.dao.CardsDAO;
import model.dto.Entity;

import java.util.List;

/**
 * Created by tonchief on 05/21/2017.
 */
public class CardsDAOimpl implements CardsDAO {
    public void createTableIfNotExist() {

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

    public List findAll() {
        return null;
    }
}
