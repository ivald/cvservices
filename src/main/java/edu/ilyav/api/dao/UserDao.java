package edu.ilyav.api.dao;

import edu.ilyav.api.models.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends CrudRepository<UserInfo, Long> {
	List<UserInfo> findAll();

	UserInfo findByUserName(String userName);

	UserInfo findById(Long id);

	UserInfo save(UserInfo userInfo);
}
