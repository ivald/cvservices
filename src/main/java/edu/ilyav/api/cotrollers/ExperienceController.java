package edu.ilyav.api.cotrollers;

import edu.ilyav.api.models.Experience;
import edu.ilyav.api.models.ProfileContent;
import edu.ilyav.api.models.WebResponse;
import edu.ilyav.api.service.ExperienceService;
import edu.ilyav.api.service.ProfileContentService;
import edu.ilyav.api.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/rest/private/experience")
public class ExperienceController extends BaseController {

	@Autowired
	private ExperienceService experienceService;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Experience add(HttpServletRequest request, @RequestBody Experience experience) throws ResourceNotFoundException {
		if(isGuestMode(request))
			throw new ResourceNotFoundException("You do not have this privilege in Guest mode.");
		else {
			return experienceService.saveOrUpdate(experience);
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public Experience update(HttpServletRequest request, @RequestBody Experience experience) throws ResourceNotFoundException {
		if(isGuestMode(request))
			throw new ResourceNotFoundException("You do not have this privilege in Guest mode.");
		else {
			return experienceService.saveOrUpdate(experience);
		}
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public WebResponse delete(HttpServletRequest request, @PathVariable Long id) throws Exception {
		if(isGuestMode(request))
			throw new ResourceNotFoundException("You do not have this privilege in Guest mode.");
		else {
			return experienceService.delete(id);
		}
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<Experience> getAll(HttpServletRequest request) throws ResourceNotFoundException {
		if(isGuestMode(request))
			throw new ResourceNotFoundException("You do not have this privilege in Guest mode.");
		else {
			return experienceService.findAll();
		}
	}


}
