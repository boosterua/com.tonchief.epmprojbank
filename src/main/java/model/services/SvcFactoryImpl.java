package model.services;

/**
 * Created by tonchief on 05/25/2017.
 */
public class SvcFactoryImpl extends ServiceFactory {
    User user = null;
    Admin admin = null;

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
}
