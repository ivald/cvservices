package edu.ilyav.api.service;

import edu.ilyav.api.models.UserInfo;
import edu.ilyav.api.service.exceptions.ResourceNotFoundException;

import java.util.List;

public interface UserService {
	List<UserInfo> findAllUsers();

	UserInfo findByUserName(String userName) throws ResourceNotFoundException;

	UserInfo saveOrUpdate(UserInfo userInfo);

	UserInfo createNewOne(UserInfo userInfo);

}
