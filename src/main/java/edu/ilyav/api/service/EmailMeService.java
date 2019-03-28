package edu.ilyav.api.service;

import edu.ilyav.api.models.EmailMe;
import edu.ilyav.api.service.exceptions.ResourceNotFoundException;

import java.util.List;

public interface EmailMeService {
	List<EmailMe> findAll();

	EmailMe findById(Long id);

	void delete(Long id) throws Exception;

	EmailMe saveOrUpdate(EmailMe role) throws ResourceNotFoundException;

}
