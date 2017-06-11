package model.dao.factory;


import model.dao.interfaces.*;


public abstract class DAOFactory {

    public abstract FeesDAO getFeesDAO();

    public abstract UsersDAO getUsersDAO();

    public abstract AccountsDAO getAccountsDAO();

    public abstract CardsDAO getCardsDAO();

    public abstract TransactionsDAO getTransactionsDAO();

}
