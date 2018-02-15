package edu.ilyav.api.service.impl;

import edu.ilyav.api.dao.UserRepository;
import edu.ilyav.api.models.UserInfo;
import edu.ilyav.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<UserInfo> findAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public UserInfo findByUserName(String userName) {
		return userRepository.findByUserName(userName);
	}

	@Override
	public UserInfo save(UserInfo userInfo) {
		return userRepository.save(userInfo);
	}
	
}
