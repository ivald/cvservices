package edu.ilyav.api.service.impl;

import edu.ilyav.api.dao.ExperienceRepository;
import edu.ilyav.api.models.Experience;
import edu.ilyav.api.service.ExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExperienceServiceImpl implements ExperienceService {

	@Autowired
	private ExperienceRepository experienceRepository;
	
	@Override
	public List<Experience> findAllProfiles() {
		return experienceRepository.findAll();
	}

	@Override
	public Experience findById(Long id){
		return experienceRepository.findById(id);
	}
	
	@Override
	public Experience saveOrUpdate(Experience experience) {
		return experienceRepository.save(experience);
	}
	
}
