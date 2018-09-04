package edu.ilyav.api.cotrollers;

import edu.ilyav.api.models.Language;
import edu.ilyav.api.service.LanguageService;
import edu.ilyav.api.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/private/language")
public class LanguageController {

	@Autowired
	private LanguageService languageService;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Language add(@RequestBody Language language) throws ResourceNotFoundException {
		return languageService.saveOrUpdate(language);
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public Language update(@RequestBody Language language) throws ResourceNotFoundException {
		return languageService.saveOrUpdate(language);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public void delete(@RequestBody Language language) {
		long i = language.getId();
//		return educationService.delete(id);
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<Language> getAll() {
		return languageService.findAll();
	}


}
