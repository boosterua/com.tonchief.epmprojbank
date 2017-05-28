package model.entity;

/**
 * Created by tonchief on 05/26/2017.
 */
public class Fee implements Entity {
    private double transferFee;
    private double newCardFee;
    private double apr;
    private String name;
    public int id = 0;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name=name;
    }

    public void setTransferFee(double transferFee) {
        this.transferFee = transferFee;
    }

    public void setNewCardFee(double newCardFee) {
        this.newCardFee = newCardFee;
    }

    public void setAPR(double APR) {
        this.apr = APR;
    }

    @Override
    public String toString(){
        return String.format("%d. %s : trf=%.2f; ncf=%.2f; apr=%.2f <br>",
                id, name, transferFee, newCardFee, apr);
    }
}
