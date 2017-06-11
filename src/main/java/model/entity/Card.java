package model.entity;


import java.math.BigDecimal;
import java.time.LocalDate;


public class Card extends Entity {
    private int id;
    private String number;

    private int clientId;
    private  int feeId;
    private int accountId;

    private Client client = null;
    private boolean blocked = false;
    private BigDecimal balance = BigDecimal.ZERO;
    private LocalDate expDate;

    public Card(){}

    public Card(Client client){ this.client = client; this.clientId = client.getId();}
    public Card(int clientId){  this.clientId = clientId;}

    public Card(Integer cid, String num) {
        this.id = cid;
        this.number = num;
    }

    public Card(Integer cardId, String num, LocalDate xDate) {
        this.id = cardId;
        this.number = num;
        this.expDate = xDate;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (accountId != card.accountId) return false;
        if (!number.equals(card.number)) return false;
        return expDate.equals(card.expDate);
    }

    @Override
    public int hashCode() {
        int result = number.hashCode();
        result = 31 * result + accountId;
        result = 31 * result + expDate.hashCode();
        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return number;
    }

    public void setName(String name) {
        this.number = name;
    }


    public void setExpDate(LocalDate expDate) {
        this.expDate = expDate;
    }
    public LocalDate getExpDate() {
        return expDate;
    }

    public void setFeeId(int feeId) {
        this.feeId = feeId;
    }
    public int  getFeeId() {
        return feeId;
    }
    public void setAccountId(int acctId) {
        this.accountId = acctId;
    }
    public int  getAccountId() {
        return accountId;
    }

    public String toString() {
        return "Card id:" + this.id + ". nr=" + this.number + "; exp:"+ this.expDate + "; BLK=" + this.blocked + "; clientId=" + this.clientId;
    }
}
