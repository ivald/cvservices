package edu.ilyav.api.dao;

import edu.ilyav.api.models.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
	List<Role> findAll();

	Role findById(Long id);
}
