package service;


public abstract class ServiceFactory {

    public abstract User getUser();
    public abstract Admin getAdmin();
    public abstract Login getLogin();
    public abstract Fees getFees();


//    public abstract Transactions getTransactions();

}
