package edu.ilyav.api.cotrollers;

import edu.ilyav.api.models.Profile;
import edu.ilyav.api.models.Role;
import edu.ilyav.api.models.UserInfo;
import edu.ilyav.api.service.ProfileService;
import edu.ilyav.api.service.UserService;
import edu.ilyav.api.service.exceptions.ResourceNotFoundException;
import edu.ilyav.api.util.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Created by ivald on 2018-09-11.
 */
public class BaseController {

    public static Boolean isChanged = Boolean.FALSE;

    private Optional<Profile> profile = Optional.ofNullable(null);
    private Optional<UserInfo> userInfo = Optional.ofNullable(null);

    @Autowired
    private UserService userService;

    @Autowired
    private ProfileService profileService;

    protected Claims getUserNameFromToken(HttpServletRequest req) throws ServletException {

        final String authHeader = req.getHeader("authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ServletException("Missing or invalid Authorization header");
        }

        final String token = authHeader.substring(7);

        return Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody();
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

                this.profile.get().setLogin(userInfo.get().getLogin());

                if(Constants.USERNAME_GUEST.equals(userName) && isGuestRole(this.userInfo.get())) {
                    this.profile.get().setUserInfoId(userInfo.get().getId());
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
        this.isChanged = Boolean.FALSE;
        Hibernate.initialize(profile.getProfileContent().getExperienceList());
        Hibernate.initialize(profile.getProfileContent().getEducationList());
        Hibernate.initialize(profile.getLanguageList());
    }

    public ProfileService getProfileService() {
        return profileService;
    }

    public UserService getUserService() {
        return userService;
    }
}
