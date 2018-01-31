package edu.ilyav.api.service;

import edu.ilyav.api.models.UserInfo;

import java.util.List;

public interface UserService {
	List<UserInfo> findAllUsers();

	UserInfo findByUserName(String userName);

	UserInfo save(UserInfo userInfo);

}
