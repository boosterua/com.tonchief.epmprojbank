package service;


import model.dao.exceptions.MySqlPoolException;
import model.dao.factory.DAOFactoryImpl;
import model.entity.Account;
import model.entity.Card;
import model.entity.Client;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;
import java.util.Random;


public class Admin {

    private DAOFactoryImpl DAO = DAOFactoryImpl.getInstance();

    public static String getMessage() {
        return "#EPMPROJBANK_TC plain String Upd";
    }

    public boolean blockAccount(Account account) throws MySqlPoolException {
        return DAO.getAccountsDAO().setBlock(account);
    }

    public boolean removeAccountBlock(Account account) throws MySqlPoolException {
        account.setBlock(false);
        return DAO.getAccountsDAO().setBlock(account);
    }

    public Card issueNewCard(int clientId) throws Exception {
        String cardNum;
        final String BANKUID = "4444" + "5555";
        /* Loop to check if newly generated card exists in database: while newCard is found - regenerate new number */
        do {
            /* Generate card number (Always 16 digits).
                First 8 digits are type of card, country and issuer(bank).
                For this "educational" project we simply generate a string of random numbers and add them
                to card.country.bank pre-defined constant. Real check for card validity will pass as we use
                the real checksum algo here (last digit is a hashsum, so called luhn-code).
             */
            String cardRandomPart = ("0000000" + new Random().nextInt(9000000));
            cardNum = BANKUID + cardRandomPart.substring(cardRandomPart.length() - 7);

            int luhn = 0;
            for (int i = cardNum.length(); i > 0; i--) {
                int dig = cardNum.charAt(i - 1) - 48;
                if (i % 2 == 0) {
                    int dig2 = dig << 1;
                    luhn += dig2 - ((dig2 > 9) ? 9 : 0);
                } else luhn += dig;
            }
            cardNum += luhn % 10;
        } while(DAO.getCardsDAO().getByCardNumber(1) > 0);

        Card card = new Card(clientId);
        card.setName(cardNum);
        card.setClientId(clientId);
        card.setAccountId(2);
        card.setFeeId(2);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new java.util.Date());
        calendar.add(Calendar.MONTH, 24);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        LocalDate lDate = calendar.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        card.setExpDate(lDate);        //.... java.sql.Date jsd = java.time.LocalDate().....;
        DAO.getCardsDAO().insert(card);
        return card;
    }

    public Card issueNewCard(Client client) throws Exception {
        return issueNewCard(client.getId());
    }

    public List<Client> getClientsByRole(Long role){
        if(role==null) role=0L;
        return DAO.getUsersDAO().getUsersByRole(role);
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
