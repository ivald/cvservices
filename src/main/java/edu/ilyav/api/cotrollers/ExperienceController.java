package edu.ilyav.api.cotrollers;

import edu.ilyav.api.models.Experience;
import edu.ilyav.api.models.ProfileContent;
import edu.ilyav.api.service.ExperienceService;
import edu.ilyav.api.service.ProfileContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Experience update(@RequestBody Experience experience) {
		return experienceService.saveOrUpdate(experience);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(@RequestBody Experience experience) throws Exception {
		return experienceService.delete(experience.getId());
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<Experience> getAll() {
		return experienceService.findAll();
	}


}
