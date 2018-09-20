package edu.ilyav.api.dao;

import edu.ilyav.api.models.Experience;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExperienceRepository extends CrudRepository<Experience, Long> {
	List<Experience> findAll();

	Optional<Experience> findById(Long id);
}
