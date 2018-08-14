package edu.ilyav.api.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

/**
 * Created by ilyav on 17/10/17.
 */
@Entity
public class Education extends BaseModule {

    private String schoolName;
    private String degreeName;
    private String fieldOfStudy;
    private String grade;
    private String activitiesAndSocieties;
    private Long fromYear;
    private Long toYearOrExpected;
    @Column(length = 10240)
    private String description;
    private String imageName;
    private String link;
    private String colorTag;
    private String location;
    private Boolean isProfessionalCourse;
    private Long profileId;
    private Long profileContentId;

    @ManyToOne
    @JsonBackReference
    private ProfileContent profileContent;

    @OneToMany(mappedBy = "education", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    @JsonManagedReference(value="education-image")
    private List<Image> imageList;

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getDegreeName() {
        return degreeName;
    }

    public void setDegreeName(String degreeName) {
        this.degreeName = degreeName;
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public void setFieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getActivitiesAndSocieties() {
        return activitiesAndSocieties;
    }

    public void setActivitiesAndSocieties(String activitiesAndSocieties) {
        this.activitiesAndSocieties = activitiesAndSocieties;
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

    public Boolean getIsProfessionalCourse() {
        return isProfessionalCourse;
    }

    public void setIsProfessionalCourse(Boolean isProfessionalCourse) {
        this.isProfessionalCourse = isProfessionalCourse;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public String getColorTag() {
        return colorTag;
    }

    public void setColorTag(String colorTag) {
        this.colorTag = colorTag;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
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
}
