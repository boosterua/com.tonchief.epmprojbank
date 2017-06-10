package model.dao.interfaces;

import model.dao.exceptions.MySqlPoolException;
import model.entity.Entity;
import model.entity.Transaction;

import java.util.List;

public interface TransactionsDAO extends EntityDAO {

    List<Transaction> getListByAccountId(Integer accountId, boolean forDebit) throws MySqlPoolException;

    /**
     *
     * @param transaction
     * @return
     * 3 queries are performed in a single transaction: update balance on credit account, debit account, and enter payment into transactions table
     * Expected SQL exception when new transaction being of bigger amount than available balance
     * com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException: Column 'account_id' cannot be null
     */
    Integer insert(Object transaction);

    boolean update(int id, Entity data);

    boolean delete(long id);

    Entity getById(Integer id);
}
