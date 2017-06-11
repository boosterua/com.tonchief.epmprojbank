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

import java.util.ArrayList;
import java.util.List;

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
    private List<Account> accountList = new ArrayList<>();

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (!name.equals(client.name)) return false;
        if (!email.equals(client.email)) return false;
        return password.equals(client.password);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }

    public void setAccountBlock(Boolean accountBlock) {
        this.accountBlock = accountBlock;
    }

    public String toString(){
        return (String.format("id:%s : name:%s : acctId:%s : email:%s : feeId:%s %s : acct=%s : role:%s",
                id, name, accountId, email, feeId, feeName, account, role));
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public void addAccount(Account account) {
        accountList.add(account);
    }


}
