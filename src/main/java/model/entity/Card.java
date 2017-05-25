package model.entity;


import model.dao.interfaces.AccountsDAO;
import model.dao.factory.DAOFactoryImpl;
import model.dao.interfaces.CardsDAO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Calendar;


public class Card implements Entity {
    private int id;
    private String number;

    private int clientId;
    private  int feeId;
    private int accountId;

    private Client client = null;
    private boolean blocked = false;
    private BigDecimal balance = BigDecimal.ZERO;
    private LocalDate expDate;

//    private CardsDAO accountsDAO = DAOFactoryImpl.getInstance().getCardsDAO();
    public Card(){}

    public Card(Client client){ this.client = client; this.clientId = client.getId();}
    public Card(int clientId){  this.clientId = clientId;}

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
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

    public String toString() {
        return "" + this.id + ". nr=" + this.number + "; BLK=" + this.blocked + "; clientId=" + this.clientId;
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
    public int getFeeId() {
        return feeId;
    }


    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int acctId) {
        this.accountId = acctId;
    }

}
