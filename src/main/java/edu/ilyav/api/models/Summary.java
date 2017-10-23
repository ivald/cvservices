package edu.ilyav.api.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.List;

/**
 * Created by ilyav on 19/10/17.
 */
public class Summary {

    private String firstDescription;
    private List<String> attributesList;
    private String secondDescription;

    @JsonBackReference
    private ProfileContent homeContent;

    public String getFirstDescription() {
        return firstDescription;
    }

    public void setFirstDescription(String firstDescription) {
        this.firstDescription = firstDescription;
    }

    public List<String> getAttributesList() {
        return attributesList;
    }

    public void setAttributesList(List<String> attributesList) {
        this.attributesList = attributesList;
    }

    public String getSecondDescription() {
        return secondDescription;
    }

    public void setSecondDescription(String secondDescription) {
        this.secondDescription = secondDescription;
    }

    public ProfileContent getHomeContent() {
        return homeContent;
    }

    public void setHomeContent(ProfileContent homeContent) {
        this.homeContent = homeContent;
    }
}
