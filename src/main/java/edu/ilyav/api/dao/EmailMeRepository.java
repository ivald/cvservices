package edu.ilyav.api.dao;

import edu.ilyav.api.models.EmailMe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmailMeRepository extends CrudRepository<EmailMe, Long> {
	List<EmailMe> findAll();

	Optional<EmailMe> findById(Long id);
}
