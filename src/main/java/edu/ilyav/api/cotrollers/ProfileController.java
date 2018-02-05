package edu.ilyav.api.cotrollers;

import edu.ilyav.api.models.Profile;
import edu.ilyav.api.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/profile")
public class ProfileController {

	@Autowired
	private ProfileService profileService;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Profile save(@RequestBody Profile profile) {
		return profileService.save(profile);
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<Profile> getAllUsers() {
		return profileService.findAllProfiles();
	}
}
