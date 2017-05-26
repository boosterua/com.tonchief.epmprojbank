package model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Transaction implements Entity {

    private long crAccount;
    private Account dtAccount;
    private BigDecimal amount;
    private LocalDate trDate;
    private String description;

    public Transaction(long crAccount, Account dtAccount, BigDecimal amount,
                       LocalDate trDate, String pmntDescription) {
        this.crAccount = crAccount;
        this.dtAccount = dtAccount;
        this.amount = amount;
        this.trDate = trDate;
        this.description = pmntDescription;
    }


    @Override
    public int getId() {
        return 0;
    }

    @Override
    public void setId(int id) {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {

    }

    public String toString(){
        return "CR:"+ crAccount +"; DT:"+  dtAccount + "; SUM:"
                +  amount + "; Descr:" + trDate +":"+ description;
    }


}
