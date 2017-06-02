package model.dao.interfaces;

import model.dao.exceptions.ExceptionDAO;
import model.dao.exceptions.MySqlPoolException;
import model.entity.Account;

import java.sql.SQLException;
import java.util.List;

public interface AccountsDAO extends EntityDAO {
    boolean isBlocked(Account account) throws MySqlPoolException, SQLException;
    boolean setBlock(Account account) throws MySqlPoolException;
    boolean setBlock(int accId);
    Long getMaxNumByAccountNum(String like) throws ExceptionDAO;
    Integer generate(int clientId, String acctPrefix) throws ExceptionDAO ;
    List<Account> findAllByClientId(Integer uid) throws ExceptionDAO;
}
