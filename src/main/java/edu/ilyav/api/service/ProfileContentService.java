package edu.ilyav.api.service;

import edu.ilyav.api.models.ProfileContent;

import java.util.List;

public interface ProfileContentService {
	List<ProfileContent> findAll();

	ProfileContent findById(Long id);

	ProfileContent saveOrUpdate(ProfileContent profileContent);

}
