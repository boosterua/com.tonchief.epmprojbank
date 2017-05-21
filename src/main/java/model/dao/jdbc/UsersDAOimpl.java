package model.dao.jdbc;

import model.dao.UsersDAO;
import model.dto.Entity;

import java.util.List;

/**
 * Created by tonchief on 05/21/2017.
 */
public class UsersDAOimpl implements UsersDAO {
    public void createTableIfNotExist() {

    }

    public boolean insert(Entity tdata) {
        return false;
    }

    public boolean update(int id, Entity data) {
        return false;
    }

    public boolean delete(int id) {
        return false;
    }

    public Entity getById(int id) {
        return null;
    }

    public List findAll() {
        return null;
    }
}
