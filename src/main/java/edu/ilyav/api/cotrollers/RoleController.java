package edu.ilyav.api.cotrollers;

import edu.ilyav.api.models.Role;
import edu.ilyav.api.service.RoleService;
import edu.ilyav.api.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/private/role")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Role add(@RequestBody Role role) throws ResourceNotFoundException {
		return roleService.saveOrUpdate(role);
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public Role update(@RequestBody Role role) throws ResourceNotFoundException {
		return roleService.saveOrUpdate(role);
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable Long id) throws Exception {
		roleService.delete(id);
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<Role> getAll() {
		return roleService.findAll();
	}


}
