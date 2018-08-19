package edu.ilyav.api.service;

import edu.ilyav.api.models.Image;

import java.util.List;
import java.util.Optional;

public interface ImageService {
	List<Image> findAll();

	Optional<Image> findById(Long id);

	void delete(Long id);

	Image saveOrUpdate(Image image);

}
