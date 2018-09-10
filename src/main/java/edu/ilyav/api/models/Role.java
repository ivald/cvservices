package edu.ilyav.api.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Created by ilyav on 17/10/17.
 */
@Entity
public class Role extends BaseModule {

    private String roleName;

    @ManyToOne
    @JsonBackReference(value="login-role")
    private Login login;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }
}
