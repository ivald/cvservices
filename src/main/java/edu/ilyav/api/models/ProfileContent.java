package edu.ilyav.api.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Comparator;
import java.util.List;

/**
 * Created by ilyav on 22/10/17.
 */
@Entity
public class ProfileContent extends BaseModule {

    private Long profileId;
    private Long summaryId;

    @OneToOne
    @JsonBackReference
    private Profile profile;

    @OneToOne(mappedBy = "profileContent", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Summary summary;

    @OneToMany(mappedBy = "profileContent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Experience> experienceList;

    @OneToMany(mappedBy = "profileContent", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    @JsonManagedReference
    private List<Education> educationList;

    public Summary getSummary() {
        return summary;
    }

    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    public List<Experience> getExperienceList() {
        experienceList.sort((o1, o2) -> {
            if(o1.getToYearOrExpected() != null)
                return Integer.parseInt(Long.toString(o2.getFromYear() - o1.getFromYear()));
            else
                return -1;
        });
        return experienceList;
    }

    public void setExperienceList(List<Experience> experienceList) {
        this.experienceList = experienceList;
    }

    public List<Education> getEducationList() {
        educationList.sort((o1, o2) -> {
            if(o1.getToYearOrExpected() != null) {
                if(o2.getFromYear() == null || o1.getFromYear() == null)
                    return -1 ;
                else
                    return Integer.parseInt(Long.toString(o2.getFromYear() - o1.getFromYear()));
            } else
                return -1;
        });
        return educationList;

    }

    public void setEducationList(List<Education> educationList) {
        this.educationList = educationList;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public Long getSummaryId() {
        return summaryId;
    }

    public void setSummaryId(Long summaryId) {
        this.summaryId = summaryId;
    }
}
