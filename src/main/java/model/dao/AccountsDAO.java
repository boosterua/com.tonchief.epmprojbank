package model.dao;

import model.dto.Account;

/**
 * Created by tonchief on 05/20/2017.
 */
public interface AccountsDAO extends EntityDao {
    boolean isBlocked(Account entity);

    boolean setblock(Account account, boolean block);
}
