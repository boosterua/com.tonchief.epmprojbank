package model.dao.factory;


import model.dao.interfaces.*;

/**
 * Created by ton chief on 05/15/2017.
 * tonchief@gmail.com
 * http://tonchief.com/
 */

public abstract class DAOFactory {

    public abstract FeesDAO getFeesDAO();

    public abstract UsersDAO getUsersDAO();

    public abstract AccountsDAO getAccountsDAO();

    public abstract CardsDAO getCardsDAO();

    public abstract TransactionsDAO getTransactionsDAO();

}
