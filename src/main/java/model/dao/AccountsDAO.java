package model.dao;

import model.dao.exceptions.MySqlPoolException;
import model.dto.Account;

import java.sql.SQLException;

/**
 * Created by tonchief on 05/20/2017.
 */
public interface AccountsDAO extends EntityDao {
    boolean isBlocked(Account entity) throws MySqlPoolException, SQLException;

    boolean setblock(Account account, boolean block) throws MySqlPoolException;
}
