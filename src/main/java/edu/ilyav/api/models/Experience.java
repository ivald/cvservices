package edu.ilyav.api.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

/**
 * Created by ilyav on 20/10/17.
 */
@Entity
public class Experience extends BaseModule {

    private String title;
    private String company;
    private String location;
    private Long fromYear;
    private Long toYearOrExpected;
    private String startDate;
    private String endDate;
    private Boolean currentlyWorkHere;
    @Column(length = 2048)
    private String description;
    private String imageName;
    private String link;
    private String colorTag;
    private Long profileContentId;

    @ManyToOne
    @JsonBackReference
    private ProfileContent profileContent;

    @OneToMany(mappedBy = "experience", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    @JsonManagedReference(value="experience-image")
    private List<Image> imageList;

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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Long getFromYear() {
        return fromYear;
    }

    public void setFromYear(Long fromYear) {
        this.fromYear = fromYear;
    }

    public Long getToYearOrExpected() {
        return toYearOrExpected;
    }

    public void setToYearOrExpected(Long toYearOrExpected) {
        this.toYearOrExpected = toYearOrExpected;
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

    public String getColorTag() {
        return colorTag;
    }

    public void setColorTag(String colorTag) {
        this.colorTag = colorTag;
    }

    public ProfileContent getProfileContent() {
        return profileContent;
    }

    public void setProfileContent(ProfileContent profileContent) {
        this.profileContent = profileContent;
    }

    public Long getProfileContentId() {
        return profileContentId;
    }

    public void setProfileContentId(Long profileContentId) {
        this.profileContentId = profileContentId;
    }

    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }
}
