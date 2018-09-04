package edu.ilyav.api.service.exceptions;

/**
 * Created by ivald on 2018-09-03.
 */
public class ResourceNotFoundException extends Exception {

    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(final String message) {
        super(message);
    }
}
