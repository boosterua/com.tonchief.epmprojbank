package model.dao;


import java.util.List;

public interface CardsDAO extends EntityDAO {
    List listCardsOfType();
    List listClientsOfType();
    /*
    select * from clients where id_client in (select id_client from accounts where id_card in (select id_cards from cards where fees_id=1) )
    * */
    //TODO: get List Of Clients with Cards of Type - where should this go (Cards DAO or Clients DAO)?
}
