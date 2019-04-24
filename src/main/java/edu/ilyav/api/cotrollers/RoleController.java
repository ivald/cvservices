package edu.ilyav.api.cotrollers;

import edu.ilyav.api.models.Role;
import edu.ilyav.api.service.RoleService;
import edu.ilyav.api.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/rest/private/role")
public class RoleController extends BaseController {

	@Autowired
	private RoleService roleService;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Role add(HttpServletRequest req, @RequestBody Role role) throws ResourceNotFoundException {
		if(isGuestMode(req))
			throw new ResourceNotFoundException("You do not have this privilege in Guest mode.");
		else
			return roleService.saveOrUpdate(role);
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public Role update(HttpServletRequest req, @RequestBody Role role) throws ResourceNotFoundException {
		if(isGuestMode(req))
			throw new ResourceNotFoundException("You do not have this privilege in Guest mode.");
		else
			return roleService.saveOrUpdate(role);
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public void delete(HttpServletRequest req, @PathVariable Long id) throws Exception {
		if(isGuestMode(req))
			throw new ResourceNotFoundException("You do not have this privilege in Guest mode.");
		else
			roleService.delete(id);
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<Role> getAll(HttpServletRequest req) throws ResourceNotFoundException {
		if(isGuestMode(req))
			throw new ResourceNotFoundException("You do not have this privilege in Guest mode.");
		else
			return roleService.findAll();
	}


}
