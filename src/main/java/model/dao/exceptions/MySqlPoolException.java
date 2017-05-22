package model.dao.exceptions;


@SuppressWarnings("serial")

public class MySqlPoolException extends Exception {
    public MySqlPoolException(final String msg, Exception e) {
        super(msg, e);
    }
}