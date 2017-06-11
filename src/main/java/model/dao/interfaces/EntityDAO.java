package model.dao.interfaces;

import model.dao.exceptions.ExceptionDAO;
import model.dao.exceptions.MySqlPoolException;

import java.util.ResourceBundle;

public interface EntityDAO<T extends model.entity.Entity> {
   ResourceBundle BUNDLE = ResourceBundle.getBundle("database.psqueries");

    Integer insert(Object tdata) throws ExceptionDAO;
    boolean update(int id, T data) throws ExceptionDAO;
    boolean delete(long id) throws ExceptionDAO;
    T getById(Integer id) throws ExceptionDAO, MySqlPoolException;
}

