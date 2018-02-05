package edu.ilyav.api.service;

import edu.ilyav.api.models.Profile;

import java.util.List;

public interface ProfileService {
	List<Profile> findAllProfiles();

	Profile findById(Long id);

	Profile save(Profile profile);

}
