package edu.ilyav.api.service;

import edu.ilyav.api.models.ProfileContent;
import edu.ilyav.api.service.exceptions.ResourceNotFoundException;

import java.util.List;

public interface ProfileContentService {
	List<ProfileContent> findAll();

	ProfileContent findById(Long id) throws ResourceNotFoundException;

	ProfileContent saveOrUpdate(ProfileContent profileContent);

}
