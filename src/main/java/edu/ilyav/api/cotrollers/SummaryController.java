package edu.ilyav.api.cotrollers;

import edu.ilyav.api.models.Summary;
import edu.ilyav.api.service.SummaryService;
import edu.ilyav.api.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/private/summary")
public class SummaryController {

	@Autowired
	private SummaryService summaryService;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Summary add(@RequestBody Summary summary) throws ResourceNotFoundException {
		return summaryService.saveOrUpdate(summary);
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public Summary update(@RequestBody Summary summary) throws ResourceNotFoundException {
		return summaryService.saveOrUpdate(summary);
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<Summary> getAll() {
		return summaryService.findAll();
	}


}
