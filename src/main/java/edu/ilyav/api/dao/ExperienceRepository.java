package edu.ilyav.api.dao;

import edu.ilyav.api.models.Experience;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExperienceRepository extends CrudRepository<Experience, Long> {
	List<Experience> findAll();

	Experience findById(Long id);
}
