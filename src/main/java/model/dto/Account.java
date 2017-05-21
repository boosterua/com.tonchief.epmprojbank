package model.dto;


/**
 * Created by p on 05/20/2017.
 */
public class Account implements Entity {
    int id;
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return null;
    }

    public void setName() {
    }

    boolean isBlocked() {
        return false;
    }

    ;

    public boolean getBlockedStatus() {
        return false;
    }

    public int getClientId() {
        return 0;
    }
}
