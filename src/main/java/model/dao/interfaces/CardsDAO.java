package model.dao.interfaces;


import model.entity.Card;

import java.util.List;

public interface CardsDAO extends EntityDAO {
    List listCardsOfType();
    List listClientsOfType();

    int getByCardNumber(long i);
    List<Card> getByAccountId(Integer id);

    Integer getNumCardsByAccountId(Integer accountId);
}
