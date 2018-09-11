package edu.ilyav.api.service;

import edu.ilyav.api.models.Role;
import edu.ilyav.api.service.exceptions.ResourceNotFoundException;

import java.util.List;

public interface RoleService {
	List<Role> findAll();

	Role findById(Long id);

	void delete(Long id) throws Exception;

	Role saveOrUpdate(Role role) throws ResourceNotFoundException;

}
