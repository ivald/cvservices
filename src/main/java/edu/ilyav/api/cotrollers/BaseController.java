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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

/**
 * Created by ivald on 2018-09-11.
 */
@PropertySource(ignoreResourceNotFound = true, value = "classpath:application.properties")
public class BaseController {

    @Value("${secret.key}")
    protected String SECRET_KEY;

    public static Boolean isChanged = Boolean.FALSE;

    private Optional<Profile> profile = Optional.ofNullable(null);
    private Optional<UserInfo> userInfo = Optional.ofNullable(null);

    @Autowired
    private UserService userService;

    @Autowired
    private ProfileService profileService;

    protected Claims getUserNameFromToken(HttpServletRequest req) throws ServletException, UnsupportedEncodingException {

        final String authHeader = req.getHeader("authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ServletException("Missing or invalid Authorization header");
        }

        final String token = authHeader.substring(7);

        return Jwts.parser().setSigningKey("MyWebSiteToken".getBytes("UTF-8")).parseClaimsJws(token).getBody();
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

    public Profile getProfile(HttpServletRequest req, String userName) throws ResourceNotFoundException {
        return getProfileByParameter(req, userName);
    }

    public Profile getProfile(HttpServletRequest req, Claims claims) throws ResourceNotFoundException {
        return getProfileByParameter(req, claims);
    }

    @Transactional
    public Profile getProfileByParameter(HttpServletRequest req, Object parameter) throws ResourceNotFoundException {
        synchronized (this) {
            Optional<UserInfo> user = Optional.ofNullable((UserInfo) req.getAttribute("currentUser"));
            Role role = new Role();
            String userName = null;
            if(user.isPresent()) {
                userName = user.get().getUserName();
            }

            if(!this.profile.isPresent() || (this.userInfo.isPresent() && !this.userInfo.get().getUserName().equals(userName)) || isChanged) {
                if(parameter instanceof String) {
                    user = Optional.ofNullable(userService.findByUserName((String)parameter));
                } else if( parameter instanceof Claims) {
                    user = Optional.ofNullable(userService.findByUserName(((Claims)parameter).getSubject()));
                    if(!user.get().getLogin().getRoles().isEmpty()) {
                        for (Role r : user.get().getLogin().getRoles()) {
                            role.setRoleName(r.getRoleName());
                            break;
                        }
                    }
                } else {
                    throw new ResourceNotFoundException("Missing or invalid input parameter");
                }

                this.userInfo = user;
                this.profile = Optional.ofNullable(userInfo.get().getProfile());
                if(!this.profile.isPresent()) {
                    this.profile = Optional.ofNullable(profileService.findById(userInfo.get().getProfileId()));
                }

                this.profile.get().setRole(role);

                if(Constants.USERNAME_GUEST.equals(user.get().getUserName()) && isGuestRole(this.userInfo.get())) {
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

    protected boolean isGuestMode(HttpServletRequest req) {
        return isGuestRole((UserInfo)req.getAttribute("currentUser"));
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
