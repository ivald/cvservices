package edu.ilyav.api.cotrollers;

import edu.ilyav.api.models.Profile;
import edu.ilyav.api.models.ProfileContent;
import edu.ilyav.api.models.Role;
import edu.ilyav.api.models.UserInfo;
import edu.ilyav.api.service.ProfileService;
import edu.ilyav.api.service.UserService;
import edu.ilyav.api.service.exceptions.ResourceNotFoundException;
import edu.ilyav.api.util.Constants;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
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

    private Optional<Profile> profile = Optional.ofNullable(null);
    private Optional<UserInfo> userInfo = Optional.ofNullable(null);

    public HomeController() {
    }

    @RequestMapping(value = "/public/run", method = RequestMethod.GET)
    public Boolean isRunning() {
        return Boolean.TRUE;
    }

    @RequestMapping(value = "/public/sideBar/{id}", method = RequestMethod.GET)
    public Profile sideBar(@PathVariable Long id) throws ResourceNotFoundException {
        return getProfile(id);
    }

    @RequestMapping(value = "/public/home/{id}", method = RequestMethod.GET)
    public ProfileContent home(@PathVariable Long id) throws ResourceNotFoundException {
        return getProfile(id).getProfileContent();
    }

    @RequestMapping(value = "/public/main/home/{userName}", method = RequestMethod.GET)
    public Profile home(@PathVariable String userName) throws ResourceNotFoundException {
        return getProfile(userName);
    }

    public Profile getProfile(Long id) throws ResourceNotFoundException {
        synchronized (this) {
            if(this.profile == null || isChanged) {
                this.profile = Optional.ofNullable(profileService.findById(id));
                updateLazyFetch(this.profile.get());
            }
            return this.profile.get();
        }
    }

    @Transactional
    public Profile getProfile(String userName) throws ResourceNotFoundException {
        synchronized (this) {
            if(!this.profile.isPresent() || (this.userInfo.isPresent() && !this.userInfo.get().getUserName().equals(userName)) || isChanged) {
                this.userInfo = Optional.ofNullable(userService.findByUserName(userName));
                this.profile = Optional.ofNullable(userInfo.get().getProfile());
                if(!this.profile.isPresent()) {
                    this.profile = Optional.ofNullable(profileService.findById(userInfo.get().getProfileId()));
                }

                if(Constants.USERNAME_GUEST.equals(userName) && isGuestRole(this.userInfo.get())) {
                    this.profile.get().setUserInfoId(userInfo.get().getId());
                    this.profile.get().setLogin(userInfo.get().getLogin());
                } else {
                    this.profile.get().setLogin(userInfo.get().getLogin());
                }

                updateLazyFetch(this.profile.get());
            }
            return this.profile.get();
        }
    }

    private boolean isGuestRole(UserInfo userInfo) {
        if(!userInfo.getLogin().getRoles().isEmpty()) {
            for (Role role : userInfo.getLogin().getRoles()) {
                if(Constants.GUEST.equals(role.getRoleName())) {
                    return true;
                }
            }
        }
        return false;
    }

    private void updateLazyFetch(Profile profile) {
        isChanged = Boolean.FALSE;
        Hibernate.initialize(profile.getProfileContent().getExperienceList());
        Hibernate.initialize(profile.getProfileContent().getEducationList());
        Hibernate.initialize(profile.getLanguageList());
    }
}
