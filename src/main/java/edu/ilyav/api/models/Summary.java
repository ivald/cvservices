package edu.ilyav.api.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;

/**
 * Created by ilyav on 19/10/17.
 */
@Entity
public class Summary extends BaseModule {

    @Column(length = 2048)
    private String description;
    private Long profileContentId;

    @OneToOne
    @JsonBackReference
    private ProfileContent profileContent;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
