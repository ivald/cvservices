package edu.ilyav.api.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.List;

/**
 * Created by ilyav on 19/10/17.
 */
public class Summary {

    private String description;

    @JsonBackReference
    private ProfileContent homeContent;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProfileContent getHomeContent() {
        return homeContent;
    }

    public void setHomeContent(ProfileContent homeContent) {
        this.homeContent = homeContent;
    }
}
