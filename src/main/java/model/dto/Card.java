package model.dto;


import model.dao.AccountsDAO;
import model.dao.FactoryDAOImpl;
import model.dao.exceptions.MySqlPoolException;

import java.math.BigDecimal;


public class Card implements Entity {
    int id;
    String number;
    int clientId;
    boolean blocked;
    BigDecimal balance;

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






}
