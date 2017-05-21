package model.dao;

/**
 * Created by tonchief on 05/21/2017.
 */
public class ExceptionDAO extends Exception {
    public ExceptionDAO(String message) {
        super(message);
    }

    public ExceptionDAO(String message, Throwable cause) {
        super(message, cause);
    }

    public ExceptionDAO(Throwable cause) {
        super(cause);
    }
}

