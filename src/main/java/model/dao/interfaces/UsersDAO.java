package model.dao.interfaces;

import model.dao.exceptions.ExceptionDAO;
import model.entity.Client;
import model.entity.Entity;

import java.util.List;

/**
 * Created by tonchief on 05/20/2017.
 */
public interface UsersDAO extends EntityDAO {
    Integer authenticateUser(String name, String password);
    List<Client> getUsersByRole(Long role);

    Entity getDetailedById(Integer clientId);

    Boolean setUserRole(Integer uid, Long role) throws ExceptionDAO;
}
