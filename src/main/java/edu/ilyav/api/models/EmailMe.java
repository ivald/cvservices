package edu.ilyav.api.models;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by ivald on 2018-08-11.
 */
@Entity
public class EmailMe extends BaseModule {

    private String name;
    private String email;
    @Column(length = 10240)
    private String message;
    private String error;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
