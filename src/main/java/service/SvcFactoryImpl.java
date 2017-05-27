package service;

public class SvcFactoryImpl extends ServiceFactory {
    private User user = null;
    private Admin admin = null;
    private Login login;
    private static SvcFactoryImpl instance = null;


    private SvcFactoryImpl(){    }

    public static SvcFactoryImpl getInstance() {
        if(instance==null)
            instance = new SvcFactoryImpl();
        return instance;
    }

    @Override
    public User getUser() {
        if (user == null)
            user = new User();
        return null;
    }

    @Override
    public User getAdmin() {
        if (admin==null)
            admin = new Admin();
        return null;
    }

    public Login getLogin() {
        if(login==null)
            login = new Login();
        return login;
    }
}