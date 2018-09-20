package edu.ilyav.api.dao;

import edu.ilyav.api.models.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends CrudRepository<Profile, Long> {
	List<Profile> findAll();

	Optional<Profile> findById(Long id);

	Profile findByPrimaryEmail(String primaryEmail);

	Profile save(Profile profile);
}
