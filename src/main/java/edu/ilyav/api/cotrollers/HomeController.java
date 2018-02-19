package edu.ilyav.api.cotrollers;

import edu.ilyav.api.models.Profile;
import edu.ilyav.api.models.ProfileContent;
import edu.ilyav.api.service.ProfileService;
import edu.ilyav.api.service.UserService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping("/public/run")
    public Boolean isRunning() {
        return Boolean.TRUE;
    }

    @RequestMapping("/public/main/sideBar/{userName}")
    public Profile sideBar(@PathVariable String userName) {
        return getProfile(userName);
    }

    @RequestMapping("/public/sideBar/{id}")
    public Profile sideBar(@PathVariable Long id) {
        return getProfile(id);
    }

    @RequestMapping("/public/home/{id}")
    public ProfileContent home(@PathVariable Long id) {
        return getProfile(id).getProfileContent();
    }

    @RequestMapping("/public/main/home/{userName}")
    public ProfileContent home(@PathVariable String userName) {
        return getProfile(userName).getProfileContent();
    }

    public Profile getProfile(Long id) {
        synchronized (this) {
            if(this.profile == null || isChanged) {
                this.profile = profileService.findById(id);
                isChanged = Boolean.FALSE;
                Hibernate.initialize(profile.getEducationList());
                Hibernate.initialize(profile.getLanguageList());
            }
            return this.profile;
        }
    }

    public Profile getProfile(String userName) {
        synchronized (this) {
            if(this.profile == null || isChanged) {
                this.profile = userService.findByUserName(userName).getProfile();
                isChanged = Boolean.FALSE;
                Hibernate.initialize(profile.getEducationList());
                Hibernate.initialize(profile.getLanguageList());
            }
            return this.profile;
        }
    }
}