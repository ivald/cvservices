package edu.ilyav.api.service.impl;

import edu.ilyav.api.cotrollers.HomeController;
import edu.ilyav.api.cotrollers.PhotoController;
import edu.ilyav.api.cotrollers.ProfileController;
import edu.ilyav.api.dao.ExperienceRepository;
import edu.ilyav.api.models.Experience;
import edu.ilyav.api.models.ProfileContent;
import edu.ilyav.api.service.ExperienceService;
import edu.ilyav.api.service.ProfileContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExperienceServiceImpl implements ExperienceService {

	@Autowired
	private ProfileContentService profileContentService;

	@Autowired
	private ExperienceRepository experienceRepository;
	
	@Override
	public List<Experience> findAll() {
		return experienceRepository.findAll();
	}

	@Override
	public Experience findById(Long id){
		return experienceRepository.findById(id);
	}
	
	@Override
	public Experience saveOrUpdate(Experience experience) {
		ProfileContent profileContent = profileContentService.findById(experience.getProfileContentId());
		experience.setProfileContent(profileContent);
		HomeController.isChanged = Boolean.TRUE;
		ProfileController.isChanged = Boolean.TRUE;
		return experienceRepository.save(experience);
	}
	
}
