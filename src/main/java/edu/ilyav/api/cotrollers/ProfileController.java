package edu.ilyav.api.cotrollers;

import edu.ilyav.api.models.Profile;
import edu.ilyav.api.models.UserInfo;
import edu.ilyav.api.service.exceptions.ResourceNotFoundException;
import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/private/profile")
public class ProfileController extends BaseController {

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Profile add(HttpServletRequest req, @RequestBody Profile profile) throws ResourceNotFoundException {
		if(isGuestMode(req))
			throw new ResourceNotFoundException("You do not have this privilege in Guest mode.");
		else
			return getProfileService().saveOrUpdate(profile);
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public Profile update(HttpServletRequest req, @RequestBody Profile profile) throws ResourceNotFoundException {
		if(isGuestMode(req))
			throw new ResourceNotFoundException("You do not have this privilege in Guest mode.");
		else
			return getProfileService().saveOrUpdate(profile);
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<Profile> getAllUsers(HttpServletRequest req) throws ResourceNotFoundException {
		if(isGuestMode(req))
			throw new ResourceNotFoundException("You do not have this privilege in Guest mode.");
		else
			return getProfileService().findAllProfiles();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Profile getProfileById(@PathVariable Long id) throws ResourceNotFoundException {
		return getProfile(id);
	}

	@RequestMapping(method = RequestMethod.GET)
	public Profile getProfile(HttpServletRequest req) throws ResourceNotFoundException, ServletException {
		return getProfile(req, getUserNameFromToken(req));
	}

}
