package edu.ilyav.api.dao;

import edu.ilyav.api.models.Login;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoginRepository extends CrudRepository<Login, Long> {
	List<Login> findAll();

	Optional<Login> findById(Long id);
}
