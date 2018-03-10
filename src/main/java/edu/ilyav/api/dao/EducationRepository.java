package edu.ilyav.api.dao;

import edu.ilyav.api.models.Education;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EducationRepository extends CrudRepository<Education, Long> {
	List<Education> findAll();

	Education findById(Long id);
}
