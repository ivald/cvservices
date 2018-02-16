package edu.ilyav.api.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

/**
 * Created by ilyav on 17/10/17.
 */
@Entity
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String schoolName;
    private String degreeName;
    private String fieldOfStudy;
    private String grade;
    private String activitiesAndSocieties;
    private Long fromYear;
    private Long toYearOrExpected;
    private String description;
    private String imageName;
    private String link;
    private Boolean isProfessionalCourse;
    private Long profileId;

    @ManyToOne
    @JsonBackReference
    private Profile profile;

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

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }
}
