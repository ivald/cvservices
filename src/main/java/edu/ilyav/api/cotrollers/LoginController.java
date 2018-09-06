package edu.ilyav.api.cotrollers;

import edu.ilyav.api.models.Login;
import edu.ilyav.api.service.LoginService;
import edu.ilyav.api.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/private/login")
public class LoginController {

	@Autowired
	private LoginService loginService;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Login add(@RequestBody Login login) throws ResourceNotFoundException {
		return loginService.saveOrUpdate(login);
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public Login update(@RequestBody Login login) throws ResourceNotFoundException {
		return loginService.saveOrUpdate(login);
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable Long id) throws Exception {
		loginService.delete(id);
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<Login> getAll() {
		return loginService.findAll();
	}


}
