package edu.ilyav.api.dao;

import edu.ilyav.api.models.Image;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends CrudRepository<Image, Long> {
	List<Image> findAll();

	Image findById(Long id);
}
