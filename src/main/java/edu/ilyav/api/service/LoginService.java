package edu.ilyav.api.service;

import edu.ilyav.api.models.Login;
import edu.ilyav.api.service.exceptions.ResourceNotFoundException;

import java.util.List;

public interface LoginService {
	List<Login> findAll();

	Login findById(Long id);

	void delete(Long id) throws Exception;

	Login saveOrUpdate(Login login) throws ResourceNotFoundException;

}
