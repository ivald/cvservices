package edu.ilyav.api.service.impl;

import edu.ilyav.api.dao.ProfileContentRepository;
import edu.ilyav.api.models.ProfileContent;
import edu.ilyav.api.service.ProfileContentService;
import edu.ilyav.api.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileContentServiceImpl implements ProfileContentService {

	@Autowired
	private ProfileContentRepository profileContentRepository;
	
	@Override
	public List<ProfileContent> findAll() {
		return profileContentRepository.findAll();
	}

	@Override
	@Transactional
	public ProfileContent findById(Long id) throws ResourceNotFoundException {
		Optional<ProfileContent> profileContent = Optional.ofNullable(profileContentRepository.findById(id));
		if(!profileContent.isPresent()) {
			throw new ResourceNotFoundException("ProfileContent id: " + id.toString() + " not found");
		}
		return profileContent.get();
	}

	@Override
	public ProfileContent saveOrUpdate(ProfileContent profileContent) {
		return profileContentRepository.save(profileContent);
	}
	
}
