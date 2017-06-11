package model.entity;

import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Transaction extends Entity {


    private String crAccountStr;

    private Account dtAccount;
    private int dtAcctId;
    private BigDecimal amount;
    private LocalDate trDate;
    private String description;
    private static final Logger LOGGER = Logger.getLogger(Transaction.class);
    private int id;

    public Transaction() {
    }

    public Transaction(Account dtAccount, String crAccountStr, BigDecimal amount, LocalDate trDate, String
            pmntDescription) {
        this.dtAccount = dtAccount;
        this.crAccountStr = crAccountStr;
        this.amount = amount;
        this.trDate = trDate;
        this.description = pmntDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        if (dtAcctId != that.dtAcctId) return false;
        if (crAccountStr != null ? !crAccountStr.equals(that.crAccountStr) : that.crAccountStr != null) return false;
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        if (trDate != null ? !trDate.equals(that.trDate) : that.trDate != null) return false;
        return description != null ? description.equals(that.description) : that.description == null;
    }

    @Override
    public int hashCode() {
        int result = crAccountStr != null ? crAccountStr.hashCode() : 0;
        result = 31 * result + dtAcctId;
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (trDate != null ? trDate.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    public void setCrAccountStr(String crAccountStr) {
        this.crAccountStr = crAccountStr;
    }

    public Account getDtAccount() {
        return dtAccount;
    }

    public void setDtAccount(Account dtAccount) {
        this.dtAccount = dtAccount;
    }

    @Override
    public int getId() {
        return this.id;
    }


    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {
    }

    public LocalDate getDate() {
        return trDate;
    }

    public void setTrDate(LocalDate trDate) {
        this.trDate = trDate;
    }

    public String getCreditAccount() {
        return crAccountStr;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDtAccountID() {
        return dtAcctId;
    }

    public String toString() {
        return "CR:" + crAccountStr + "; DT:[" + dtAccount + "]; SUM:" + amount + "; Descr:" + trDate + ":" +
                description;
    }

}
