package edu.ilyav.api.cotrollers;

import edu.ilyav.api.models.Profile;
import edu.ilyav.api.models.ProfileContent;
import edu.ilyav.api.service.ProfileService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/private/profile")
public class ProfileController {

	public static Boolean isChanged = Boolean.FALSE;

	private Profile profile;

	@Autowired
	private ProfileService profileService;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Profile add(@RequestBody Profile profile) {
		return profileService.saveOrUpdate(profile);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Profile update(@RequestBody Profile profile) {
		return profileService.saveOrUpdate(profile);
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<Profile> getAllUsers() {
		return profileService.findAllProfiles();
	}

	@RequestMapping("/{id}")
	public ProfileContent profile(@PathVariable Long id) {
		return getProfile(id).getProfileContent();
	}

	public Profile getProfile(Long id) {
		synchronized (this) {
			if(this.profile == null || isChanged) {
				this.profile = profileService.findById(id);
				ProfileController.isChanged = Boolean.FALSE;
				Hibernate.initialize(profile.getProfileContent().getExperienceList());
				Hibernate.initialize(profile.getProfileContent().getEducationList());
				Hibernate.initialize(profile.getLanguageList());
			}
			return this.profile;
		}
	}
}
