package edu.ilyav.api.service;

import edu.ilyav.api.models.Image;

import java.util.List;

public interface ImageService {
	List<Image> findAll();

	Image findById(Long id);

	void delete(Long id);

	Image saveOrUpdate(Image image);

}
