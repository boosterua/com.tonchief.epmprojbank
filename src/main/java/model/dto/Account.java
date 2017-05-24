package model.dto;


import model.dao.AccountsDAO;
import model.dao.FactoryDAOImpl;
import model.dao.exceptions.MySqlPoolException;

import java.math.BigDecimal;


public class Account implements Entity {
    private int id;
    private String number; /* number is also a name of account. Always a number [Ususally 10..16 digits]. String data type in dto, Long in dao. */
    private int clientId;
    private boolean blocked;
    private BigDecimal balance;


    private AccountsDAO accountsDAO = FactoryDAOImpl.getInstance().getAccountsDAO();

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
        return "" + this.id + "; " + this.number + "; " + this.blocked + "; " + this.clientId;
    }


    public boolean getBlockedStatus() {
        return blocked;
    }

    public void setBlock(boolean block) throws MySqlPoolException {
        this.blocked = block;
        accountsDAO.setBlock(this);
    }

    public int getClientId() {
        return clientId;
    }
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
    public void replenish(BigDecimal amt){
        this.balance = balance.add(amt);
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }


}
