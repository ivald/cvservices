package edu.ilyav.api.cotrollers;

import edu.ilyav.api.models.Login;
import edu.ilyav.api.service.LoginService;
import edu.ilyav.api.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/rest/private/login")
public class LoginController extends BaseController {

	@Autowired
	private LoginService loginService;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Login add(HttpServletRequest request, @RequestBody Login login) throws ResourceNotFoundException {
		if(isGuestMode(request))
			throw new ResourceNotFoundException("You do not have this privilege in Guest mode.");
		else {
			return loginService.saveOrUpdate(login);
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public Login update(HttpServletRequest request, @RequestBody Login login) throws ResourceNotFoundException {
		if(isGuestMode(request))
			throw new ResourceNotFoundException("You do not have this privilege in Guest mode.");
		else {
			return loginService.saveOrUpdate(login);
		}
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public void delete(HttpServletRequest request, @PathVariable Long id) throws Exception {
		if(isGuestMode(request))
			throw new ResourceNotFoundException("You do not have this privilege in Guest mode.");
		else {
			loginService.delete(id);
		}
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<Login> getAll(HttpServletRequest request) throws ResourceNotFoundException {
		if(isGuestMode(request))
			throw new ResourceNotFoundException("You do not have this privilege in Guest mode.");
		else {
			return loginService.findAll();
		}
	}


}
