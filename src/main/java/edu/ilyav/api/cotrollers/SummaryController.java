package edu.ilyav.api.cotrollers;

import edu.ilyav.api.models.Summary;
import edu.ilyav.api.service.SummaryService;
import edu.ilyav.api.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/rest/private/summary")
public class SummaryController extends BaseController {

	@Autowired
	private SummaryService summaryService;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Summary add(HttpServletRequest req, @RequestBody Summary summary) throws ResourceNotFoundException {
		if(isGuestMode(req))
			throw new ResourceNotFoundException("You do not have this privilege in Guest mode.");
		else
			return summaryService.saveOrUpdate(summary);
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public Summary update(HttpServletRequest req, @RequestBody Summary summary) throws ResourceNotFoundException {
		if(isGuestMode(req))
			throw new ResourceNotFoundException("You do not have this privilege in Guest mode.");
		else
			return summaryService.saveOrUpdate(summary);
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<Summary> getAll(HttpServletRequest req) throws ResourceNotFoundException {
		if(isGuestMode(req))
			throw new ResourceNotFoundException("You do not have this privilege in Guest mode.");
		else
			return summaryService.findAll();
	}


}
