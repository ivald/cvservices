package edu.ilyav.api.service;

import edu.ilyav.api.models.Profile;

import java.util.List;

public interface UserService {
	List<Profile> findAllUsers();

	Profile findByUserName(String userName);

	Profile save(Profile user);

}
