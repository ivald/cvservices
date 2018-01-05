package edu.ilyav.api.service.impl;

import edu.ilyav.api.dao.UserDao;
import edu.ilyav.api.models.Profile;
import edu.ilyav.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

//	@Autowired
	private UserDao userDao;
	
	@Override
	public List<Profile> findAllUsers() {
		return userDao.findAll();
	}

	@Override
	public Profile findByUserName(String userName) {
		return userDao.findByUserName(userName);
	}

	@Override
	public Profile save(Profile user) {
		return userDao.save(user);
	}
	
}
