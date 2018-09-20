package edu.ilyav.api.dao;

import edu.ilyav.api.models.Image;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends CrudRepository<Image, Long> {
	List<Image> findAll();

	Optional<Image> findById(Long id);
}
