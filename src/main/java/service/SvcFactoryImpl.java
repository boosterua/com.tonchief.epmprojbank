package service;

import model.entity.Fee;

import java.util.HashMap;
import java.util.List;

public class SvcFactoryImpl extends ServiceFactory {
    private User user = null;
    private Admin admin = null;
    private Login login;
    private static SvcFactoryImpl instance = null;
    private Fees fee;


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
        return user;
    }

    @Override
    public Admin getAdmin() {
        if (admin==null)
            admin = new Admin();
        return admin;
    }

    public Login getLogin() {
        if(login==null)
            login = new Login();
        return login;
    }

    public Fees getFees(){
        if(fee==null)
            fee = new Fees();
        return fee;
    }

//    public HashMap<Integer, String> getFeesSchedule() {
//        return null;
//    }
}