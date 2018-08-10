package edu.ilyav.api.cotrollers;

import edu.ilyav.api.models.Experience;
import edu.ilyav.api.models.ProfileContent;
import edu.ilyav.api.service.ExperienceService;
import edu.ilyav.api.service.ProfileContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/rest/private/experience")
public class ExperienceController {

	@Autowired
	private ExperienceService experienceService;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Experience add(@RequestBody Experience experience) {
		return experienceService.saveOrUpdate(experience);
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public Experience update(@RequestBody Experience experience) {
		return experienceService.saveOrUpdate(experience);
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable Long id) throws Exception {
		return experienceService.delete(id);
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<Experience> getAll() {
		return experienceService.findAll();
	}


}
