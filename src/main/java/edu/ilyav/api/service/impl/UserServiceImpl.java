package edu.ilyav.api.service.impl;

import edu.ilyav.api.dao.UserRepository;
import edu.ilyav.api.models.Profile;
import edu.ilyav.api.models.UserInfo;
import edu.ilyav.api.service.UserService;
import edu.ilyav.api.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<UserInfo> findAllUsers() {
		return userRepository.findAll();
	}

	@Override
	@Transactional
	public UserInfo findByUserName(String userName) throws ResourceNotFoundException {
		Optional<UserInfo> user = Optional.ofNullable(userRepository.findByUserName(userName));
		if(!user.isPresent()) {
			throw new ResourceNotFoundException("UserInfo user name: " + userName + " not found");
		}
		return user.get();
	}

	@Override
	public UserInfo saveOrUpdate(UserInfo userInfo) {
		return userRepository.save(userInfo);
	}
	
}
