package edu.ilyav.api.service.impl;

import edu.ilyav.api.cotrollers.HomeController;
import edu.ilyav.api.cotrollers.ProfileController;
import edu.ilyav.api.dao.ImageRepository;
import edu.ilyav.api.models.Experience;
import edu.ilyav.api.models.Image;
import edu.ilyav.api.service.ExperienceService;
import edu.ilyav.api.service.ImageService;
import edu.ilyav.api.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {

	@Autowired
	private ExperienceService experienceService;

	@Autowired
	private ImageRepository imageRepository;
	
	@Override
	public List<Image> findAll() {
		return imageRepository.findAll();
	}

	@Override
	public Image findById(Long id){
		return imageRepository.findById(id);
	}

	@Override
	public void delete(Long id){
		imageRepository.delete(id);
	}
	
	@Override
	public Image saveOrUpdate(Image image) {
		Experience experience = experienceService.findById(image.getExperienceId());
		image.setExperience(experience);
		Constants.updateHomeProfileObjects();
		return imageRepository.save(image);
	}
	
}
