package model.dao;

/**
 * Created by p on 01/25/2017.
 */
public class FactoryDAOImpl extends FactoryDAO {
    private static FactoryDAOImpl instance = null;
    private UserDAO userDAO = new UserDAO();
    private AdminDAO adminDAO = new AdminDAO();
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

    public AdminDAO getAdminDAO() {
        return adminDAO;
    }
}
