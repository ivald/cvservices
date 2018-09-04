package edu.ilyav.api.service.exceptions;

/**
 * Created by ivald on 2018-09-03.
 */
public class PhotoServiceException extends Exception {

    public PhotoServiceException() {
        super();
    }

    public PhotoServiceException(final String message) {
        super(message);
    }
}
