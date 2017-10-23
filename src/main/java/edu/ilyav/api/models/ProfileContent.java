package edu.ilyav.api.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;

/**
 * Created by ilyav on 22/10/17.
 */
public class ProfileContent {

    @JsonManagedReference
    private Summary summary;

    @JsonManagedReference
    private List<Experience> experienceList;

    public Summary getSummary() {
        return summary;
    }

    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    public List<Experience> getExperienceList() {
        return experienceList;
    }

    public void setExperienceList(List<Experience> experienceList) {
        this.experienceList = experienceList;
    }
}
