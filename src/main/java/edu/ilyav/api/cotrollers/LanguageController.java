package edu.ilyav.api.cotrollers;

import edu.ilyav.api.models.Language;
import edu.ilyav.api.service.LanguageService;
import edu.ilyav.api.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/rest/private/language")
public class LanguageController extends BaseController {

	@Autowired
	private LanguageService languageService;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Language add(HttpServletRequest request, @RequestBody Language language) throws ResourceNotFoundException {
		if(isGuestMode(request))
			throw new ResourceNotFoundException("You do not have this privilege in Guest mode.");
		else {
			return languageService.saveOrUpdate(language);
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public Language update(HttpServletRequest request, @RequestBody Language language) throws ResourceNotFoundException {
		if(isGuestMode(request))
			throw new ResourceNotFoundException("You do not have this privilege in Guest mode.");
		else {
			return languageService.saveOrUpdate(language);
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public void delete(HttpServletRequest request, @RequestBody Language language) throws ResourceNotFoundException {
		if(isGuestMode(request))
			throw new ResourceNotFoundException("You do not have this privilege in Guest mode.");
		else {
			long i = language.getId();
			//return educationService.delete(id);
		}
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<Language> getAll(HttpServletRequest request) throws ResourceNotFoundException {
		if(isGuestMode(request))
			throw new ResourceNotFoundException("You do not have this privilege in Guest mode.");
		else {
			return languageService.findAll();
		}
	}


}
