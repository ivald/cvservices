package edu.ilyav.api.dao;

import edu.ilyav.api.models.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileDao extends CrudRepository<Profile, Long> {
	List<Profile> findAll();

	Profile findById(Long id);

	Profile save(Profile profile);
}
