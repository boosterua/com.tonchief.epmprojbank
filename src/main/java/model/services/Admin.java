package model.services;


import model.dao.exceptions.MySqlPoolException;
import model.dao.factory.DAOFactoryImpl;
import model.entity.Account;
import model.entity.Card;
import model.entity.Client;

import java.time.LocalDate;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.sql.Date;


public class Admin {
    private DAOFactoryImpl dao = DAOFactoryImpl.getInstance();

    public boolean blockAccount(Account account) throws MySqlPoolException {
        return dao.getAccountsDAO().setBlock(account);
    }

    public boolean removeAccountBlock(Account account) throws MySqlPoolException {
        account.setBlock(false);
        return dao.getAccountsDAO().setBlock(account);
    }

    public Card issueNewCard(int clientId) throws Exception {

        Card card = new Card(clientId);
        //TODO: Generate card code + luhn
        card.setName("4444555566667777");
        card.setClientId(clientId);
        card.setAccountId(2);
        card.setFeeId(2);

        //TODO: Current date + 2Years - last day of month
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new java.util.Date());
        calendar.add(Calendar.MONTH, 24);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        LocalDate lDate = calendar.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        card.setExpDate(lDate);
        dao.getCardsDAO().insert(card);
        java.sql.Date jsd = java.util.Date.from()
        return card;
    }

    public Card issueNewCard(Client client) throws Exception {
        return issueNewCard(client.getId());
    }

}


/*
     Administrator
     -> blockAccount();
     -> removeAccountBlock();
     -> + issueNewCard();
     -> listClientsByCardType(VisaClassic); list all clients with Visa Classic Cards
     -> listClientsWithBlockedAccounts();
     -> approveClient(); // incl.issue new account and card.
     -> listCardsOfType;
*/
