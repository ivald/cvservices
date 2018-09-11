package edu.ilyav.api.service.exceptions;

/**
 * Created by ivald on 2018-09-03.
 */
public class LoginServiceException extends Exception {

    public LoginServiceException() {
        super();
    }

    public LoginServiceException(final String message) {
        super(message);
    }
}
