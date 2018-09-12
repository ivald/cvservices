package edu.ilyav.api.cotrollers;

import edu.ilyav.api.models.Profile;
import edu.ilyav.api.models.ProfileContent;
import edu.ilyav.api.models.Role;
import edu.ilyav.api.models.UserInfo;
import edu.ilyav.api.service.ProfileService;
import edu.ilyav.api.service.UserService;
import edu.ilyav.api.service.exceptions.ResourceNotFoundException;
import edu.ilyav.api.util.Constants;
import io.jsonwebtoken.Claims;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/private/profile")
public class ProfileController extends BaseController {

	public static Boolean isChanged = Boolean.FALSE;

	private Optional<Profile> profile = Optional.ofNullable(null);
	private Optional<UserInfo> userInfo = Optional.ofNullable(null);

	@Autowired
	private UserService userService;

	@Autowired
	private ProfileService profileService;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Profile add(@RequestBody Profile profile) {
		return profileService.saveOrUpdate(profile);
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public Profile update(@RequestBody Profile profile) {
		return profileService.saveOrUpdate(profile);
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<Profile> getAllUsers() {
		return profileService.findAllProfiles();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Profile getProfileById(@PathVariable Long id) throws ResourceNotFoundException {
		return getProfile(id);
	}

	@RequestMapping(method = RequestMethod.GET)
	public Profile getProfile(HttpServletRequest req) throws ResourceNotFoundException, ServletException {
		Optional<UserInfo> user = Optional.ofNullable(userService.findByUserName(getUserNameFromToken(req).getSubject()));
		return getProfile(user.get().getUserName());
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
		this.isChanged = Boolean.FALSE;
		Hibernate.initialize(profile.getProfileContent().getExperienceList());
		Hibernate.initialize(profile.getProfileContent().getEducationList());
		Hibernate.initialize(profile.getLanguageList());
	}

}
