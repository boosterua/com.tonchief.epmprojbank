package main.java.model.dto;


import model.dto.Entity;

/**
 * Created by p on 05/20/2017.
 */
public class Client implements Entity {
    private int id;
    private String name;
    private int cardId;
    //...


    public Client(int id, String name) {
        this.id = id;
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName() {
    }

    public int getCardId() {
        return cardId;
    }

    public int getId() {
        return id;
    }

    public void setId() {
    }

    public void setId(int id) {
        this.id = id;
    }


}
