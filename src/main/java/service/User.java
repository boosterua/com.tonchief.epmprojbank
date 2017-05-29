package service;

import model.dao.exceptions.MySqlPoolException;
import model.dao.factory.DAOFactoryImpl;
import model.entity.Account;
import model.entity.Entity;
import model.entity.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;

import static java.time.LocalDate.now;

//    -> makePayment(details: from, to, amount, descr);
//    -> replenishAccount(acctId, amount, source[anotherAccount|cash]);
//    -> blockAccount(acctId);

public class User {

    private DAOFactoryImpl DAO = DAOFactoryImpl.getInstance();
    private String name;
    private String email;
    private String password;
    private Long role;

    public User(){}

    /* The default constructor. Used in service - upon receiving application from web-user.*/
    public User(String name, String email, String password, Long role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }


    /**
     * process the account replenishment
     * @param acctId Account being replenished
     * @param sourceAcct Source account (0 for cash-in)
     * @return boolean result - success or not
     * */
    public boolean replenishAccount(int acctId, BigDecimal amount, long sourceAcct)
            throws Exception {
        Account account = new Account(); account.setId(1);
        makePayment(Long.getLong(DAO.getAccountsDAO().getById(acctId).getName()), account, amount, "Cash Replenishment via Terminal");
        return false;
    }


    public synchronized int makePayment(Long crAccount, Account dtAcctId,
                           BigDecimal amount, String description) throws Exception {
        Transaction transaction = new Transaction(crAccount, dtAcctId, amount, now(), description);
        DAO.getTransactionsDAO().insert(transaction);
        //TODO: SQL transaction: new transaction + update balance

        /*
        Ex

        dbConnection.setAutoCommit(false); //transaction block start

String insertTableSQL = "INSERT INTO DBUSER"
			+ "(USER_ID, USERNAME, CREATED_BY, CREATED_DATE) VALUES"
			+ "(?,?,?,?)";

String updateTableSQL = "UPDATE DBUSER SET USERNAME =? "
			+ "WHERE USER_ID = ?";

preparedStatementInsert = dbConnection.prepareStatement(insertTableSQL);
preparedStatementInsert.setInt(1, 999);
preparedStatementInsert.setString(2, "mkyong101");
preparedStatementInsert.setString(3, "system");
preparedStatementInsert.setTimestamp(4, getCurrentTimeStamp());
preparedStatementInsert.executeUpdate(); //data IS NOT commit yet

preparedStatementUpdate = dbConnection.prepareStatement(updateTableSQL);
preparedStatementUpdate.setString(1, "A very very long string caused DATABASE ERROR");
preparedStatementUpdate.setInt(2, 999);
preparedStatementUpdate.executeUpdate(); //Error, rollback, including the first insert statement.

dbConnection.commit(); //transaction block end
        * */
         return 1;
    }

    public static String getMessage() {
        return "Authorised User. Welcome.";
    }

    public boolean blockAccount(int acctId){
        return DAO.getAccountsDAO().setBlock(acctId);
    }

    public boolean blockAccount(Account account) throws MySqlPoolException {
        return DAO.getAccountsDAO().setBlock(account);
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public long getRole() {
        return role;
    }

    @Override
    public String toString(){
        return String.format("%S <%s> **** ROLE=%d", name, email, role );
    }

    public int register(User user) {
        if (user.isValid())
            try {
                return DAO.getUsersDAO().insert(user);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                return  -1;
            }
        return -1;
    }

    public boolean isValid() {

        return ((name != null) && !name.equals("") &&
                (email != null) && !email.equals("") &&
                (password != null) && !password.equals(""));
    }
}
