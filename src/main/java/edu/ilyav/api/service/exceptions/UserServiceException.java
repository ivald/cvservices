package edu.ilyav.api.service.exceptions;

/**
 * Created by ivald on 2018-09-03.
 */
public class UserServiceException extends Exception {

    public UserServiceException() {
        super();
    }

    public UserServiceException(final String message) {
        super(message);
    }
}
