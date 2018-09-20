package edu.ilyav.api.dao;

import edu.ilyav.api.models.Summary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SummaryRepository extends CrudRepository<Summary, Long> {
	List<Summary> findAll();

	Optional<Summary> findById(Long id);
}
