package model.dao;


/**
 * Created by ton chief on 01/25/2017.
 * tonchief@gmail.com
 * http://tonchief.com/
 */

public abstract class FactoryDAO {

    public abstract UserDAO getUserDAO();

    public abstract AdminDAO getAdminDAO();

    public abstract AccountsDAO getAccountsDAO();

    public abstract CardsDAO getCardsDAO();

    public abstract TransactionsDAO getTransactionsDAO();

}
