package model.dao;

/**
 * Created by p on 05/20/2017.
 */

import jdk.nashorn.internal.ir.annotations.Ignore;

@Ignore
public class FactoryDAOImpl extends FactoryDAO {
    private static FactoryDAOImpl instance = null;


//    private FeesDAO feesDAO = new FeesDAO();
//    private UsersDAO usersDAO = new UsersDAO();
//    private AccountsDAO accountsDAO = new AccountsDAO();
//    private CardsDAO cardsDAO = new CardsDAO();
//    private TransactionsDAO transactionsDAO = new TransactionsDAO();

    private FeesDAO feesDAO;
    private UsersDAO usersDAO;
    private AccountsDAO accountsDAO;
    private CardsDAO cardsDAO;
    private TransactionsDAO transactionsDAO;

    private FactoryDAOImpl() {
    }

    public static FactoryDAOImpl getInstance() {
        if (instance == null){
            return new FactoryDAOImpl();
        }
        return instance;
    }

    public FeesDAO getFeesDAO() {
        return feesDAO;
    }

    public UsersDAO getUsersDAO() {

        return usersDAO;
    }

    public AccountsDAO getAccountsDAO() {
        return accountsDAO;
    }

    public CardsDAO getCardsDAO() {
        return cardsDAO;
    }

    public TransactionsDAO getTransactionsDAO() {
        return transactionsDAO;
    }


}

