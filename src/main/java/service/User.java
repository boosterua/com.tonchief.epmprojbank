package service;

import model.dao.exceptions.ExceptionDAO;
import model.dao.exceptions.MySqlPoolException;
import model.dao.factory.DAOFactoryImpl;
import model.entity.Account;
import model.entity.Transaction;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class User {

    private DAOFactoryImpl DAO = DAOFactoryImpl.getInstance();
    private String name;
    private String email;
    private String passwordHash;
    private Long role;
    private int feeId;

    private final Logger logger = Logger.getLogger(User.class);


    public User(){
    }


    /**
     * The default constructor. Used in service - upon receiving application from web-user.
     * @param name
     * @param email
     * @param passwordHash
     * @param role used to determine if user is approved, is admin, or newly registered
     */
    public User(String name, String email, String passwordHash, Long role) {
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
    }


    /**
     * process the account replenishment
     * @param acctId Int Account being replenished
     * @param sourceAcct Source account (0 for cash-in)
     * @return boolean result - success or not
     * */
    public int replenishAccount(int acctId, BigDecimal amount, Integer sourceAcct) throws ExceptionDAO, MySqlPoolException {
        Account account = new Account(); account.setId(sourceAcct==null || sourceAcct==0 ? 1 : sourceAcct);
        return makePayment(account, DAO.getAccountsDAO().getById(acctId).getName(),
                amount, "Cash Replenishment via Terminal");
    }

    /**
     *
     * @param dtAccount debitAccount:Account
     * @param crAccount creditAccount:Long
     * @param amount    sum:BigDecimal
     * @param description String
     * @return transactionId:int
     */
    public synchronized int makePayment(Account dtAccount, String crAccount,
                                        BigDecimal amount, String description) {
        LocalDate date = LocalDate.now();
        Transaction transaction = new Transaction(dtAccount, crAccount, amount, date, description);
        logger.debug(transaction);
        try {
            return DAO.getTransactionsDAO().insert(transaction); // Tr.Id:Int
        } catch (Exception e) {
            logger.error(e);
        }
        return 0;
    }

    /* Gets list of transactions per account and Converts it to list of table entities */
    public List<List> getTransactionsList(Integer accountId, Boolean isForDebit) throws MySqlPoolException {
        List<Transaction> transactionList = DAO.getTransactionsDAO().getListByAccountId(accountId, isForDebit);
        if(transactionList==null) return null;
        List<List> listOfList = new ArrayList<>();
        for(Transaction tr : transactionList){
            listOfList.add( Arrays.asList(tr.getId()+"", tr.getCreditAccount(),
                    tr.getAmount().toString(), tr.getDate().toString(), tr.getDescription()) );
        }
        return listOfList;
    }

    public Account getAccountById(int id) throws MySqlPoolException, ExceptionDAO {
        return (Account)DAO.getAccountsDAO().getById(id);
    }

//TODO: check if client is not in db yet
//TODO:!user vs client; + regAcct-set to user, then insert new user           user.set
    public Integer register(User user) { // Client vs user
        if (user.fieldsAreValid())
            try {
                /* Here's where magic happens: turn user into client ;) */
                Integer clientId = DAO.getUsersDAO().insert(user);
                if(clientId==-23) //Constraint Violation = user email already in DB
                    return -23;

                generateNewAccount(clientId, false);
                return clientId;

            } catch (Exception e) {
                logger.error(e);
            } finally {
            }
        return -1;
    }

    public void generateNewAccount(Integer clientId, Boolean blocked) {
        // TODO: userReg: generate acct based on user's choice of card (set prefix);
        // TODO: add prefix field to fees tbl
        new Thread(new Runnable(){
            public void run(){
                try {
                    Integer newUsrAcctId =
                            DAO.getAccountsDAO().generate(clientId, "2625%", blocked);
                    logger.info("Separate Thread: Generated new account id:"+newUsrAcctId);
                } catch (ExceptionDAO exceptionDAO) {
                    logger.error(exceptionDAO);
                }
            }
        }).start();
    }

    private boolean fieldsAreValid() {
        return ((name != null) && !name.equals("") &&
                (email != null) && !email.equals("") &&
                (passwordHash != null) && !passwordHash.equals(""));
    }

    public static String getMessage() {
        return "Authorised User. Welcome.";
    }

    public boolean blockAccount(int acctId){
        return DAO.getAccountsDAO().setBlock(acctId,true);
    }

    @Deprecated
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
        return passwordHash;
    }

    public long getRole() {
        return role;
    }

    @Override
    public String toString(){
        return String.format("%S <%s> **** ROLE=%d", name, email, role );
    }

    public int getFeeId() {
        return feeId;
    }

    public void setFeeId(int feeId) {
        this.feeId = feeId;
    }
    public Map<Integer, Account> getUserAccountsAsMap(Integer uid) throws ExceptionDAO {
        return DAO.getAccountsDAO().findAccountsByClientId(uid);
    }
    @Deprecated // in favor of getUserAccountsAsMap
    public List<Account> getUserAccounts(Integer uid) throws ExceptionDAO {
        return DAO.getAccountsDAO().findAllByClientId(uid);
    }

}
