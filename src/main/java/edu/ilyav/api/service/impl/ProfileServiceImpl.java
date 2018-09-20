package edu.ilyav.api.service.impl;

import edu.ilyav.api.dao.ProfileRepository;
import edu.ilyav.api.models.Profile;
import edu.ilyav.api.service.ProfileService;
import edu.ilyav.api.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileServiceImpl extends BaseServiceImpl implements ProfileService{

	@Autowired
	private ProfileRepository profileDao;
	
	@Override
	public List<Profile> findAllProfiles() {
		return profileDao.findAll();
	}

	@Override
	@Transactional
	public Profile findById(Long id) throws ResourceNotFoundException {
		Optional<Profile> profile = profileDao.findById(id);
		if(!profile.isPresent()) {
			throw new ResourceNotFoundException("Profile id: " + id.toString() + " not found");
		}
		return profile.get();
	}

	@Override
	public Profile findByPrimaryEmail(String primaryEmail){
		return profileDao.findByPrimaryEmail(primaryEmail);
	}

	@Override
	public Profile saveOrUpdate(Profile profile) {
		updateHomeProfileObjects();
		return profileDao.save(profile);
	}
	
}
