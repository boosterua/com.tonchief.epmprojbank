package service;


import model.dao.exceptions.ExceptionDAO;
import model.dao.exceptions.MySqlPoolException;
import model.dao.factory.DAOFactoryImpl;
import model.entity.Account;
import model.entity.Card;
import model.entity.Client;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;


public class Admin {

    private DAOFactoryImpl DAO = DAOFactoryImpl.getInstance();
    private final Logger logger = Logger.getLogger(Admin.class);
    static final ResourceBundle RB_BANK = ResourceBundle.getBundle("banksettings");

    private static final String BANKUID = RB_BANK.getString("VISA_SYS_PREFIX") + RB_BANK.getString("BANK_CARD_ID");


    public static String getMessage() {
        return "#EPMPROJBANK_TC plain String Upd";
    }

    public boolean blockAccount(Account account) throws MySqlPoolException {
        return DAO.getAccountsDAO().setBlock(account);
    }

    public boolean removeAccountBlock(Integer accountId) throws MySqlPoolException {
//        account.setBlock(false);
        return DAO.getAccountsDAO().setBlock(accountId, false);
    }

    public Card issueNewCard(int accountId, Integer feeId)  {
        String cardNum;
        if( DAO.getCardsDAO().getNumCardsByAccountId(accountId) >= Integer.parseInt(RB_BANK.getString("MAX_NUM_CARDS_PER_CLIENT")) ){
            return null;
        }





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
        } while(DAO.getCardsDAO().getByCardNumber(Long.parseLong(cardNum)) > 0);

        Card card = new Card();
        card.setName(cardNum);
        card.setAccountId(accountId);
        card.setFeeId(feeId);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new java.util.Date());
        calendar.add(Calendar.MONTH, 24);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        LocalDate lDate = calendar.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        card.setExpDate(lDate);        //.... java.sql.Date jsd = java.time.LocalDate().....;
        try {
            Integer cardId = DAO.getCardsDAO().insert(card);
            card.setId(cardId);
        } catch (Exception e) {
            logger.error(e);
        }
        return card;
    }

    public Boolean setRole(Integer uid, Long role) throws ExceptionDAO {
        return DAO.getUsersDAO().setUserRole(uid, role);
    }

    public Card issueNewCard(Client client)  {
        return issueNewCard(client.getId(), client.getFeeId());
    }




    public Client getClientById(int clientId) throws ExceptionDAO {
        Client client = (Client) DAO.getUsersDAO().getById(clientId);
        return client;
    }


    public List<Client> getClientsByRole(Long role){
        if(role==null) role=0L;

//        List<Client> list = DAO.getUsersDAO().getUsersByRole(role);
        List<Client> list = DAO.getUsersDAO().getUsersByRoleOrBlockedSt(role, null);
//        logger.info("From Amdin.getClientsByRole:" + list.size());
        return list;
//        logger.info();
//        return DAO.getUsersDAO().getUsersByRole(role);
    }

    public List<Client> getClientsWithBlockedAccts(){
        List<Client> list = DAO.getUsersDAO().getUsersByRoleOrBlockedSt(0L, true);
        return list;
    }

    public List<Client> getClientsAll(){
        List<Client> list = DAO.getUsersDAO().getUsersByRoleOrBlockedSt(-100L, null);
        return list;
    }


    public Client getClientDetailedById(Integer clientId) throws ExceptionDAO {
        Client client = (Client) DAO.getUsersDAO().getDetailedById(clientId);
        logger.info("Got client:"+client);
        if(client==null)
            return null;
        List<Card> cards = DAO.getCardsDAO().getByAccountId(client.getAccount().getId());
        client.getAccount().setCards(cards);
//        logger.info(client.getAccount().getName());
//        logger.info(client.getAccount().getCards().get(0));
        return client;
    }
}


/*
     Administrator
     -> blockAccount();
     -> removeAccountBlock();
     -> + issueNewCard();
     -> listClientsByCardType(VisaClassic); list all clients with Visa Classic Cards
     -> listClientsWithBlockedAccounts();
     -> getClientById(); // incl.issue new account and card.
     -> listCardsOfType;
*/
