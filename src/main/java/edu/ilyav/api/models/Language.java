package edu.ilyav.api.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * Created by ilyav on 17/10/17.
 */
public class Language {

    private String languageName;
    private String languageDescription;

    @JsonBackReference
    private Profile profile;

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public String getLanguageDescription() {
        return languageDescription;
    }

    public void setLanguageDescription(String languageDescription) {
        this.languageDescription = languageDescription;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
