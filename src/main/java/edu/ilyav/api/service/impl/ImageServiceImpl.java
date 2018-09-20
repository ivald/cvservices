package edu.ilyav.api.service.impl;

import edu.ilyav.api.dao.ImageRepository;
import edu.ilyav.api.models.Experience;
import edu.ilyav.api.models.Image;
import edu.ilyav.api.service.ExperienceService;
import edu.ilyav.api.service.ImageService;
import edu.ilyav.api.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImpl extends BaseServiceImpl implements ImageService {

	@Autowired
	private ImageRepository imageRepository;
	
	@Override
	public List<Image> findAll() {
		return imageRepository.findAll();
	}

	@Override
	@Transactional
	public Optional<Image> findById(Long id){
		Optional<Image> image = imageRepository.findById(id);
		return image;
	}

	@Override
	public void delete(Long id){
		imageRepository.deleteById(id);
	}
	
	@Override
	public Image saveOrUpdate(Image image) {
		updateHomeProfileObjects();
		return imageRepository.save(image);
	}
	
}
