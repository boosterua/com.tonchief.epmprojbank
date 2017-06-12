
package service;

import model.dao.exceptions.ExceptionDAO;
import model.dao.exceptions.MySqlPoolException;
import model.entity.Account;
import org.junit.*;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class UserIT {

    public UserIT() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of replenishAccount method, of class User.
     *//*
    @Test
    public void testReplenishAccount() throws Exception {
        System.out.println("replenishAccount");
        int acctId = 0;
        BigDecimal amount = null;
        Integer sourceAcct = null;
        User instance = new User();
        int expResult = 0;
        int result = instance.replenishAccount(acctId, amount, sourceAcct);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
*/
    /**
     * Test of makePayment method, of class User.
     */
    @Test
    public void testMakePayment() throws MySqlPoolException, ExceptionDAO {
        final int CR_ACCT_ID = 2;
        final int DT_ACCT_ID = 1;
        System.out.println("makePayment");
        SvcFactoryImpl SERVICE = SvcFactoryImpl.getInstance();
        User user = SERVICE.getUser();        //User user = new User("System.test", "cash@bank.com", "N/A", 999L);
        Account dtAccount = user.getAccountById(DT_ACCT_ID); //        Account dtAccount = new Account(1, "26006430000000", false);
        Account crAccount = user.getAccountById(CR_ACCT_ID); //        String crAccountNum = "26003654789987";
        BigDecimal begBalDtAcct = dtAccount.getBalance();
        BigDecimal begBalCrAcct = crAccount.getBalance();

        BigDecimal amount = new BigDecimal("100.00");
        String description = "UnitTest Transfer of the amount 100.00, b/w cash and comission accounts.";


        int transactionId = user.makePayment(dtAccount, crAccount.getNumber(), amount, description);

        Account dtAccountAfter = user.getAccountById(DT_ACCT_ID);
        Account crAccountAfter = user.getAccountById(CR_ACCT_ID);

        BigDecimal resultAcctDt = dtAccountAfter.getBalance();
        BigDecimal resultAcctCr = crAccountAfter.getBalance();
        System.out.println( " : " + amount + " : " + " : " +  resultAcctCr.subtract(begBalCrAcct) + " : " );
        assertEquals(amount, resultAcctCr.subtract(begBalCrAcct));
        assertEquals(amount.negate(), resultAcctDt.subtract(begBalDtAcct));
    }



    /**
     * Test of getTransactionsList method, of class User.
     */
  /*  @Test
    public void testGetTransactionsList() throws Exception {
        System.out.println("getTransactionsList");
        Integer accountId = null;
        Boolean isForDebit = null;
        User instance = new User();
        List<List> expResult = null;
        List<List> result = instance.getTransactionsList(accountId, isForDebit);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
*/
//    /**
//     * Test of getAccountById method, of class User.
//     */
//    @Test
//    public void testGetAccountById() throws Exception {
//        System.out.println("getAccountById");
//        int id = 0;
//        User instance = new User();
//        Account expResult = null;
//        Account result = instance.getAccountById(id);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of register method, of class User.
//     */
//    @Test
//    public void testRegister() {
//        System.out.println("register");
//        User user = null;
//        User instance = new User();
//        Integer expResult = null;
//        Integer result = instance.register(user);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of generateNewAccount method, of class User.
//     */
//    @Test
//    public void testGenerateNewAccount() {
//        System.out.println("generateNewAccount");
//        Integer clientId = null;
//        Boolean blocked = null;
//        User instance = new User();
//        instance.generateNewAccount(clientId, blocked);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getMessage method, of class User.
//     */
//    @Test
//    public void testGetMessage() {
//        System.out.println("getMessage");
//        String expResult = "";
//        String result = User.getMessage();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of blockAccount method, of class User.
//     */
//    @Test
//    public void testBlockAccount_int() {
//        System.out.println("blockAccount");
//        int acctId = 0;
//        User instance = new User();
//        boolean expResult = false;
//        boolean result = instance.blockAccount(acctId);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of blockAccount method, of class User.
//     */
//    @Test
//    public void testBlockAccount_Account() throws Exception {
//        System.out.println("blockAccount");
//        Account account = null;
//        User instance = new User();
//        boolean expResult = false;
//        boolean result = instance.blockAccount(account);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getName method, of class User.
//     */
//    @Test
//    public void testGetName() {
//        System.out.println("getName");
//        User instance = new User();
//        String expResult = "";
//        String result = instance.getName();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getEmail method, of class User.
//     */
//    @Test
//    public void testGetEmail() {
//        System.out.println("getEmail");
//        User instance = new User();
//        String expResult = "";
//        String result = instance.getEmail();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getPassword method, of class User.
//     */
//    @Test
//    public void testGetPassword() {
//        System.out.println("getPassword");
//        User instance = new User();
//        String expResult = "";
//        String result = instance.getPassword();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getRole method, of class User.
//     */
//    @Test
//    public void testGetRole() {
//        System.out.println("getRole");
//        User instance = new User();
//        long expResult = 0L;
//        long result = instance.getRole();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of toString method, of class User.
//     */
//    @Test
//    public void testToString() {
//        System.out.println("toString");
//        User instance = new User();
//        String expResult = "";
//        String result = instance.toString();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getFeeId method, of class User.
//     */
//    @Test
//    public void testGetFeeId() {
//        System.out.println("getFeeId");
//        User instance = new User();
//        int expResult = 0;
//        int result = instance.getFeeId();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setFeeId method, of class User.
//     */
//    @Test
//    public void testSetFeeId() {
//        System.out.println("setFeeId");
//        int feeId = 0;
//        User instance = new User();
//        instance.setFeeId(feeId);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getUserAccountsAsMap method, of class User.
//     */
//    @Test
//    public void testGetUserAccountsAsMap() throws Exception {
//        System.out.println("getUserAccountsAsMap");
//        Integer uid = null;
//        User instance = new User();
//        Map<Integer, Account> expResult = null;
//        Map<Integer, Account> result = instance.getUserAccountsAsMap(uid);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getUserAccounts method, of class User.
//     */
//    @Test
//    public void testGetUserAccounts() throws Exception {
//        System.out.println("getUserAccounts");
//        Integer uid = null;
//        User instance = new User();
//        List<Account> expResult = null;
//        List<Account> result = instance.getUserAccounts(uid);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
}
