package edu.ilyav.api.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * Created by ilyav on 20/10/17.
 */
    public class Experience {

    private String title;
    private String company;
    private String location;
    private Long fromYear;
    private String fromMonth;
    private Long toYearOrExpected;
    private String toMonth;
    private Boolean currentlyWorkHere;
    private String description;
    private String imageName;
    private String link;

    @JsonBackReference
    private ProfileContent homeContent;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getFromYear() {
        return fromYear;
    }

    public void setFromYear(Long fromYear) {
        this.fromYear = fromYear;
    }

    public String getFromMonth() {
        return fromMonth;
    }

    public void setFromMonth(String fromMonth) {
        this.fromMonth = fromMonth;
    }

    public Long getToYearOrExpected() {
        return toYearOrExpected;
    }

    public void setToYearOrExpected(Long toYearOrExpected) {
        this.toYearOrExpected = toYearOrExpected;
    }

    public String getToMonth() {
        return toMonth;
    }

    public void setToMonth(String toMonth) {
        this.toMonth = toMonth;
    }

    public Boolean getCurrentlyWorkHere() {
        return currentlyWorkHere;
    }

    public void setCurrentlyWorkHere(Boolean currentlyWorkHere) {
        this.currentlyWorkHere = currentlyWorkHere;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public ProfileContent getHomeContent() {
        return homeContent;
    }

    public void setHomeContent(ProfileContent homeContent) {
        this.homeContent = homeContent;
    }
}
