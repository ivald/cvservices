package edu.ilyav.api.dao;

import edu.ilyav.api.models.Login;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginRepository extends CrudRepository<Login, Long> {
	List<Login> findAll();

	Login findById(Long id);
}
