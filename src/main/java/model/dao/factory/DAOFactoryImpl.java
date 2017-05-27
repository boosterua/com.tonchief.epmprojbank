package model.dao.factory;

import jdk.nashorn.internal.ir.annotations.Ignore;
import model.dao.interfaces.*;
import model.dao.jdbc.AccountsDAOimpl;
import model.dao.jdbc.FeesDAOimpl;
import model.dao.jdbc.*;

import java.util.Optional;

@Ignore
public class DAOFactoryImpl extends DAOFactory {
    private static DAOFactoryImpl instance = null;


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

    private DAOFactoryImpl() {
    }

    public static synchronized DAOFactoryImpl getInstance() {
        instance = Optional.ofNullable(instance).orElse(new DAOFactoryImpl());
        return instance;
    }


    public AccountsDAO getAccountsDAO() {
        return AccountsDAOimpl.getInstance();
    }

    public FeesDAO getFeesDAO() {
        // feesDAO = Optional.ofNullable(feesDAO).orElse(new FeesDAOimpl()); return feesDAO;
/*      if(feesDAO==null)
            feesDAO = new FeesDAOimpl();
        return feesDAO; */
        return FeesDAOimpl.getInstance();
    }

    public UsersDAO getUsersDAO() {
        if(usersDAO==null)
            usersDAO = new UsersDAOimpl();
        return UsersDAOimpl.getInstance();
    }

    public CardsDAO getCardsDAO() {
        return CardsDAOimpl.getInstance();
    }

    public TransactionsDAO getTransactionsDAO() {
        return transactionsDAO;
    }


}

