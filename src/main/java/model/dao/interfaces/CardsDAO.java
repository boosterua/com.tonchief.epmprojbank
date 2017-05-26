package model.dao.interfaces;


import java.util.List;

public interface CardsDAO extends EntityDAO {
    List listCardsOfType();
    List listClientsOfType();

    int getByCardNumber(long i);

}
