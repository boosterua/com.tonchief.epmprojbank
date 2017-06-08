package model.dao.interfaces;

import model.dao.exceptions.ExceptionDAO;
import model.entity.Client;
import model.entity.Entity;

import java.util.List;

/**
 *
 */
public interface UsersDAO extends EntityDAO {
    Integer authenticateUser(String name, String password);
    List<Client> getUsersByRole(Long role);
    List<Client> getClientsByRoleOrBlockedSt(Long role, Boolean block);
    Entity getDetailedById(Integer clientId);
    Boolean setUserRole(Integer uid, Long role) throws ExceptionDAO;
    Client getClientWithAccounts (Integer clientId);
}
