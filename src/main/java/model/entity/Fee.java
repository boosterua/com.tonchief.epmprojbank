package model.entity;

public class Fee extends Entity {
    public int id = 0;
    private String name;
    private double transferFee;
    private double newCardFee;
    private double apr;



    public Fee(int fid, String fnamme) {
        this.id = fid;
        this.name = fnamme;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setName(String name) {
        this.name = name;
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
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public double getTransferFee() {
        return transferFee;
    }

    public double getNewCardFee() {
        return newCardFee;
    }

    public double getApr() {
        return apr;
    }

    @Override
    public String toString() {
        return String.format("%d. %s : trf=%.2f; ncf=%.2f; apr=%.2f <br>", id, name, transferFee, newCardFee, apr);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fee fee = (Fee) o;

        if (Double.compare(fee.transferFee, transferFee) != 0) return false;
        if (Double.compare(fee.newCardFee, newCardFee) != 0) return false;
        if (Double.compare(fee.apr, apr) != 0) return false;
        return name.equals(fee.name);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name.hashCode();
        temp = Double.doubleToLongBits(transferFee);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(newCardFee);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(apr);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
