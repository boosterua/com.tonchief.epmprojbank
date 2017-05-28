package model.entity;

/**
 * Created by tonchief on 05/26/2017.
 */
public class Fee implements Entity {
    private double transferFee;
    private double newCardFee;
    private double apr;

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

    public void setTransferFee(double transferFee) {
        this.transferFee = transferFee;
    }

    public void setNewCardFee(double newCardFee) {
        this.newCardFee = newCardFee;
    }

    public void setAPR(double APR) {
        this.apr = APR;
    }
}
