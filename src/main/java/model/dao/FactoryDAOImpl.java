package model.dao;

/**
 * Created by p on 01/25/2017.
 */
public class FactoryDAOImpl extends FactoryDAO {
    private static FactoryDAOImpl instance = null;
    private UserDAO userDAO = new UserDAO();
    private UsersDAO adminDAO = new UsersDAO();
    //....

    private FactoryDAOImpl() {
    }

    public static FactoryDAOImpl getInstance() {
        if (instance == null){
            return new FactoryDAOImpl();
        }
        return instance;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public UsersDAO getAdminDAO() {
        return adminDAO;
    }

    public AccountsDAO getAccountsDAO() {
        return null;
    }

    public CardsDAO getCardsDAO() {
        return null;
    }

    public TransactionsDAO getTransactionsDAO() {
        return null;
    }
}
