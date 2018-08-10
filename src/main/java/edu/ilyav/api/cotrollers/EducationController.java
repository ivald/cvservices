package edu.ilyav.api.cotrollers;

import edu.ilyav.api.models.Education;
import edu.ilyav.api.service.EducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/rest/private/education")
public class EducationController {

	@Autowired
	private EducationService educationService;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Education add(@RequestBody Education education) {
		return educationService.saveOrUpdate(education);
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public Education update(@RequestBody Education education) {
		return educationService.saveOrUpdate(education);
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable Long id) throws Exception {
		return educationService.delete(id);
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<Education> getAll() {
		return educationService.findAll();
	}


}
