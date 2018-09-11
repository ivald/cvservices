package edu.ilyav.api.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

/**
 * Created by ilyav on 17/10/17.
 */
@Entity
public class Profile extends BaseModule {

    private String firstName;
    private String lastName;
    private String occupation;
    private String primaryEmail;
    private String linkedInUrl;
//    private String mobile;
    private String imageUrl;
    @Lob
    private Byte[] imageBytes;
    private String publicId;
    private Long userInfoId;
    @Transient
    private Login login;

    @OneToOne
    @JsonBackReference
    private UserInfo userInfo;

    @OneToOne(mappedBy = "profile", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private ProfileContent profileContent;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    @JsonManagedReference
    private List<Language> languageList;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getPrimaryEmail() {
        return primaryEmail;
    }

    public void setPrimaryEmail(String primaryEmail) {
        this.primaryEmail = primaryEmail;
    }

    public String getLinkedInUrl() {
        return linkedInUrl;
    }

    public void setLinkedInUrl(String linkedInUrl) {
        this.linkedInUrl = linkedInUrl;
    }

    public List<Language> getLanguageList() {
        return languageList;
    }

    public void setLanguageList(List<Language> languageList) {
        this.languageList = languageList;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ProfileContent getProfileContent() {
        return profileContent;
    }

    public void setProfileContent(ProfileContent profileContent) {
        this.profileContent = profileContent;
    }

    public Long getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(Long userInfoId) {
        this.userInfoId = userInfoId;
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public Byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(Byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

//    public String getMobile() {
//        return mobile;
//    }
//
//    public void setMobile(String mobile) {
//        this.mobile = mobile;
//    }
}
