package model.dao;

import model.dao.exceptions.ExceptionDAO;
import model.dto.Entity;

import java.util.List;

public interface EntityDao<T extends Entity> {

    //void createTableIfNotExist();

    boolean insert(T tdata) throws Exception;

    boolean update(int id, T data) throws ExceptionDAO;

    boolean delete(int id) throws ExceptionDAO;

    T getById(int id) throws ExceptionDAO;

    List<T> findAll() throws ExceptionDAO;

}
