package edu.ilyav.api.service.impl;

import edu.ilyav.api.dao.RoleRepository;
import edu.ilyav.api.models.Role;
import edu.ilyav.api.service.RoleService;
import edu.ilyav.api.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl extends BaseServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public List<Role> findAll() {
		return roleRepository.findAll();
	}

	@Override
	public Role findById(Long id){
		return roleRepository.findById(id);
	}

	@Override
	public void delete(Long id) {
		roleRepository.delete(id);
	}
	
	@Override
	public Role saveOrUpdate(Role role) throws ResourceNotFoundException {
		return roleRepository.save(role);
	}
	
}
