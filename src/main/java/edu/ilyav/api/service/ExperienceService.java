package edu.ilyav.api.service;

import edu.ilyav.api.models.Experience;

import java.util.List;

public interface ExperienceService {
	List<Experience> findAllProfiles();

	Experience findById(Long id);

	Experience saveOrUpdate(Experience experience);

}
