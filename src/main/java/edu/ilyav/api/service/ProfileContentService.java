package edu.ilyav.api.service;

import edu.ilyav.api.models.Profile;

import java.util.List;

public interface ProfileContentService {
	List<Profile> findAllProfiles();

	Profile findById(Long id);

	Profile findByPrimaryEmail(String primaryEmail);

	Profile save(Profile profile);

}
