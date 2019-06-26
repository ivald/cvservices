package edu.ilyav.api.service;

import edu.ilyav.api.models.Profile;
import edu.ilyav.api.service.exceptions.ResourceNotFoundException;

import java.util.List;

public interface ProfileService {
	List<Profile> findAllProfiles();

	Profile findById(Long id) throws ResourceNotFoundException;

	Profile findByPrimaryEmail(String primaryEmail);

	Profile saveOrUpdate(Profile profile) throws ResourceNotFoundException;

}
