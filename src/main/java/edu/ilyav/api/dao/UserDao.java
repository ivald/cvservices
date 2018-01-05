package edu.ilyav.api.dao;

import edu.ilyav.api.models.Profile;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
//	extends CrudRepository<Profile, Long> {
	List<Profile> findAll();

	Profile findByUserName(String userName);

	Profile findByUserId(Long userId);

	Profile save(Profile user);
}
