package edu.ilyav.api.service.impl;

import edu.ilyav.api.dao.UserDao;
import edu.ilyav.api.models.UserInfo;
import edu.ilyav.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;
	
	@Override
	public List<UserInfo> findAllUsers() {
		return userDao.findAll();
	}

	@Override
	public UserInfo findByUserName(String userName) {
		return userDao.findByUserName(userName);
	}

	@Override
	public UserInfo save(UserInfo userInfo) {
		return userDao.save(userInfo);
	}
	
}
