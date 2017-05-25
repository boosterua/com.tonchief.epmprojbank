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


    public Client(int id, String name) {
        this.id = id;
        this.name = name;
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

    public void setId() {
    }

    public void setId(int id) {
        this.id = id;
    }


}
