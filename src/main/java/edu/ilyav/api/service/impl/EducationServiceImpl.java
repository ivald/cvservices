package edu.ilyav.api.service.impl;

import edu.ilyav.api.cotrollers.HomeController;
import edu.ilyav.api.cotrollers.ProfileController;
import edu.ilyav.api.dao.EducationRepository;
import edu.ilyav.api.dao.ExperienceRepository;
import edu.ilyav.api.models.Education;
import edu.ilyav.api.models.Experience;
import edu.ilyav.api.models.Profile;
import edu.ilyav.api.models.ProfileContent;
import edu.ilyav.api.service.EducationService;
import edu.ilyav.api.service.ProfileContentService;
import edu.ilyav.api.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EducationServiceImpl implements EducationService {

	@Autowired
	private ProfileService profileService;

	@Autowired
	private EducationRepository educationRepository;
	
	@Override
	public List<Education> findAll() {
		return educationRepository.findAll();
	}

	@Override
	public Education findById(Long id){
		return educationRepository.findById(id);
	}

	@Override
	public void delete(Long id){
		educationRepository.delete(id);
	}
	
	@Override
	public Education saveOrUpdate(Education education) {
		Profile profile = profileService.findById(education.getProfileId());
		education.setProfileContent(profile.getProfileContent());
		HomeController.isChanged = Boolean.TRUE;
		ProfileController.isChanged = Boolean.TRUE;
		return educationRepository.save(education);
	}
	
}
