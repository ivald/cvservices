package edu.ilyav.api.cotrollers;

import edu.ilyav.api.models.Education;
import edu.ilyav.api.models.WebResponse;
import edu.ilyav.api.service.EducationService;
import edu.ilyav.api.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/rest/private/education")
public class EducationController extends BaseController {

	@Autowired
	private EducationService educationService;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Education add(HttpServletRequest request, @RequestBody Education education) throws ResourceNotFoundException {
		if(isGuestMode(request))
			throw new ResourceNotFoundException("You do not have this privilege in Guest mode.");
		else {
			return educationService.saveOrUpdate(education);
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public Education update(HttpServletRequest request, @RequestBody Education education) throws ResourceNotFoundException {
		if(isGuestMode(request))
			throw new ResourceNotFoundException("You do not have this privilege in Guest mode.");
		else {
			return educationService.saveOrUpdate(education);
		}
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public WebResponse delete(HttpServletRequest request, @PathVariable Long id) throws Exception {
		if(isGuestMode(request))
			throw new ResourceNotFoundException("You do not have this privilege in Guest mode.");
		else {
			return educationService.delete(id);
		}
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<Education> getAll(HttpServletRequest request) throws ResourceNotFoundException {
		if(isGuestMode(request))
			throw new ResourceNotFoundException("You do not have this privilege in Guest mode.");
		else {
			return educationService.findAll();
		}
	}


}
