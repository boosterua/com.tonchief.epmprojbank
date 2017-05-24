package model.dto;


import model.dto.Entity;

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
    private int cardId;
    //...


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

    public int getCardId() {
        return cardId;
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
