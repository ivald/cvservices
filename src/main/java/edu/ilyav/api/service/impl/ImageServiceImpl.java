package edu.ilyav.api.service.impl;

import edu.ilyav.api.dao.ImageRepository;
import edu.ilyav.api.models.Experience;
import edu.ilyav.api.models.Image;
import edu.ilyav.api.service.ExperienceService;
import edu.ilyav.api.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImpl extends BaseServiceImpl implements ImageService {

	@Autowired
	private ExperienceService experienceService;

	@Autowired
	private ImageRepository imageRepository;
	
	@Override
	public List<Image> findAll() {
		return imageRepository.findAll();
	}

	@Override
	public Optional<Image> findById(Long id){
		Optional<Image> image = Optional.ofNullable(imageRepository.findById(id));
		return image;
	}

	@Override
	public void delete(Long id){
		imageRepository.delete(id);
	}
	
	@Override
	public Image saveOrUpdate(Image image) {
		Experience experience = experienceService.findById(image.getExperienceId());
		image.setExperience(experience);
		updateHomeProfileObjects();
		return imageRepository.save(image);
	}
	
}
