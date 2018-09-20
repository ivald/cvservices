package edu.ilyav.api.dao;

import edu.ilyav.api.models.Education;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EducationRepository extends CrudRepository<Education, Long> {
	List<Education> findAll();

	Optional<Education> findById(Long id);
}
