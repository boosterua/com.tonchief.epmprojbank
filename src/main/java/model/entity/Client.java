package model.entity;


/*

     Client-> Cards[] <-Accounts[]

     Client
     -> makePayment(details);
     -> replenishAccount(acctId, amount, source[anotherAccount|cash]);
     -> blockAccount(acctId);

     Administrator
     -> removeAccountBlock();
     -> raiseCreditCardLimit();
     -> blockAccount();
*/

public class Client extends Entity {
    private int id;
    private String name;
    private int accountId;
    private Account account  ;
    private String email;
    private Fee fee;
    private String feeName;
    private Integer feeId = 0;
    private String password;
    private Integer role;
    private Boolean accountBlock;

    public Client(){}

    public Client(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public Client(int cid, String nm, String eml, Integer rol) {
        this(cid, nm);
        this.email = eml;
        this.role = rol;
    }
    public Client(int cid, String nm, String eml, Integer rol, int acctId) {
        this(cid,nm,eml,rol);
        this.accountId = acctId;
    }



    public Account getAccount() {
        return account;
    }
    public String getPassword() {
        return password;
    }
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public int getAccountId() {
        return accountId;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setFeeId(Integer feeId) {
        this.feeId = feeId;
    }
    public Integer getFeeId() {
        return feeId;
    }

    public Fee getFee() {
        return fee;
    }
    public void setFee(Fee fee) {
        this.fee = fee;
    }

    public void setFeeName(String feeName) {
        this.feeName = feeName;
    }
    public String getFeeName() {
        return feeName;
    }

    public Integer getRole() {
        return role;
    }
    public void setRole(Integer role) {
        this.role = role;
    }

    public void setAccountBlock(Boolean accountBlock) {
        this.accountBlock = accountBlock;
    }

    public String toString(){
        return (String.format("id:%s : name:%s : acctId:%s : email:%s : feeId:%s : acct=", id, name, accountId, email, feeId, feeName, account));
    }

}
