package edu.ilyav.api.cotrollers;

import edu.ilyav.api.models.Profile;
import edu.ilyav.api.models.ProfileContent;
import edu.ilyav.api.models.UserInfo;
import edu.ilyav.api.service.ProfileService;
import edu.ilyav.api.service.UserService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Created by ilyav on 17/08/17.
 */
@RestController
@RequestMapping("/rest")
public class HomeController {

    public static Boolean isChanged = Boolean.FALSE;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private UserService userService;

    private Profile profile;

    public HomeController() {
    }

    @RequestMapping(value = "/public/run", method = RequestMethod.GET)
    public Boolean isRunning() {
        return Boolean.TRUE;
    }

    @RequestMapping(value = "/public/main/sideBar/{userName}", method = RequestMethod.GET)
    public Profile sideBar(@PathVariable String userName) {
        return getProfile(userName);
    }

    @RequestMapping(value = "/public/sideBar/{id}", method = RequestMethod.GET)
    public Profile sideBar(@PathVariable Long id) {
        return getProfile(id);
    }

    @RequestMapping(value = "/public/home/{id}", method = RequestMethod.GET)
    public ProfileContent home(@PathVariable Long id) {
        return getProfile(id).getProfileContent();
    }

    @RequestMapping(value = "/public/main/home/{userName}", method = RequestMethod.GET)
    public ProfileContent home(@PathVariable String userName) {
        return getProfile(userName).getProfileContent();
    }

    public Profile getProfile(Long id) {
        synchronized (this) {
            if(this.profile == null || isChanged) {
                this.profile = profileService.findById(id);
                updateLazyFetch(this.profile);
            }
            return this.profile;
        }
    }

    public Profile getProfile(String userName) {
        synchronized (this) {
            if(this.profile == null || isChanged) {
                UserInfo userInfo = userService.findByUserName(userName);
                Optional<Profile> profile = Optional.ofNullable(userInfo.getProfile());
                if(!profile.isPresent()) {
                    this.profile = profileService.findById(userInfo.getProfileId());
                }
                updateLazyFetch(this.profile);
            }
            return this.profile;
        }
    }

    private void updateLazyFetch(Profile profile) {
        isChanged = Boolean.FALSE;
        Hibernate.initialize(profile.getProfileContent().getExperienceList());
        Hibernate.initialize(profile.getProfileContent().getEducationList());
        Hibernate.initialize(profile.getLanguageList());
    }
}