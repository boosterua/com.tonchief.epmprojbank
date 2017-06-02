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

public class Client implements Entity {
    private int id;
    private String name;
    private int accountId;
    private String email;
    private Integer feeId = 0;
    private Fee fee;
    private String feeName;
    private String account;
    private String password;

    public Client(){}

    public Client(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public Client(int cid, String nm, String eml, int acctId) {
        this(cid,nm);
        this.accountId = acctId;
        this.email = eml;
    }


    public String getAccount() {
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

    public void setName() {
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

    public void setAccount(String account) {
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

    public String toString(){
        return (String.format("%s : %s : %s : %s : %s", id, name, accountId, email, feeId, feeName));
    }

}
