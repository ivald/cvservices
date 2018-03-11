package edu.ilyav.api.service.impl;

import edu.ilyav.api.cotrollers.HomeController;
import edu.ilyav.api.cotrollers.ProfileController;
import edu.ilyav.api.dao.ProfileRepository;
import edu.ilyav.api.models.Profile;
import edu.ilyav.api.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileServiceImpl implements ProfileService{

	@Autowired
	private ProfileRepository profileDao;
	
	@Override
	public List<Profile> findAllProfiles() {
		return profileDao.findAll();
	}

	@Override
	public Profile findById(Long id){
		return profileDao.findById(id);
	}

	@Override
	public Profile findByPrimaryEmail(String primaryEmail){
		return profileDao.findByPrimaryEmail(primaryEmail);
	}

	@Override
	public Profile saveOrUpdate(Profile profile) {
		HomeController.isChanged = Boolean.TRUE;
		ProfileController.isChanged = Boolean.TRUE;
		return profileDao.save(profile);
	}
	
}
