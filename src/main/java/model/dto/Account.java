package model.dto;


/**
 * Created by p on 05/20/2017.
 */
public class Account implements Entity {
    int id;
    String number;
    int clientId;
    boolean blocked;

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

    public boolean getBlockedStatus() {
        return blocked;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public void setBlock(boolean block) {
        this.blocked = block;
    }

    public String toString() {
        return String.join(";", this.id + "", "" + this.number, "" + this.blocked, "" + this.clientId);
    }
}
