package service;


import model.dao.exceptions.ExceptionDAO;
import model.dao.exceptions.MySqlPoolException;
import model.dao.factory.DAOFactoryImpl;
import model.entity.Card;
import model.entity.Client;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Random;

public class Login {
    private DAOFactoryImpl DAO = DAOFactoryImpl.getInstance();

    public static String getMessage() {
        return "*** LOGIN FORM in JSP ****";
    }


    public boolean checkCredentials(Client client)  {
        return getUserIdOnAuth(client)!=null;
    }
    public boolean checkCredentials(String login, String pwd)  {
        return getUserIdOnAuth(login, pwd)!=null;
    }

    public Integer getUserIdOnAuth(Client client)  {
        return getUserIdOnAuth(client.getEmail(), client.getPassword());
    }

    public Integer getUserIdOnAuth(String login, String pwd)  {
        /* Check for error or hack attempts in the web-form. No empty fields. */
        if(login==null || login.isEmpty() || pwd==null || pwd.isEmpty())
            return null;
        //TODO throw Error - empty field - to View ??
        return DAO.getUsersDAO().authenticateUser(login, pwd);
    }

    public Client getClientById(Integer uid) throws ExceptionDAO, MySqlPoolException {
        if(uid==null) return null;
        return (Client)DAO.getUsersDAO().getById(uid);
    }


    public Card issueNewCard(int clientId) throws Exception {
        String cardNum;
        final String BANKUID = "4444" + "5555";
            String cardRandomPart = ("0000000" + new Random().nextInt(9000000));
            cardNum = BANKUID + cardRandomPart.substring(cardRandomPart.length() - 7);


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
}
