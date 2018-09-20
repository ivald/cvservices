package edu.ilyav.api.dao;

import edu.ilyav.api.models.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserInfo, Long> {
	List<UserInfo> findAll();

	UserInfo findByUserName(String userName);

	Optional<UserInfo> findById(Long id);

	UserInfo save(UserInfo userInfo);
}
