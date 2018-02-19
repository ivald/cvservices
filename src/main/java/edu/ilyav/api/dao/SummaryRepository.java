package edu.ilyav.api.dao;

import edu.ilyav.api.models.Summary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SummaryRepository extends CrudRepository<Summary, Long> {
	List<Summary> findAll();

	Summary findById(Long id);
}
