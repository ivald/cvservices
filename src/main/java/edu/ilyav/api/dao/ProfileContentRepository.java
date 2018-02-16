package edu.ilyav.api.dao;

import edu.ilyav.api.models.ProfileContent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileContentRepository extends CrudRepository<ProfileContent, Long> {
	List<ProfileContent> findAll();

	ProfileContent findById(Long id);

	ProfileContent save(ProfileContent profileContent);
}
