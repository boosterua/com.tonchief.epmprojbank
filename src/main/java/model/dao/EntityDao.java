package model.dao;


import model.dto.Entity;

import java.util.List;

public interface EntityDao<T extends Entity> {

    void createTableIfNotExist();

    boolean insert(T tdata);

    boolean update(int id, T data);

    boolean delete(int id);

    T getById(int id);

    List<T> findAll();

}
