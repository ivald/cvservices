package edu.ilyav.api.service.impl;

import edu.ilyav.api.dao.ProfileContentRepository;
import edu.ilyav.api.models.ProfileContent;
import edu.ilyav.api.service.ProfileContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
	public ProfileContent findById(Long id){
		return profileContentRepository.findById(id);
	}

	@Override
	public ProfileContent saveOrUpdate(ProfileContent profileContent) {
		return profileContentRepository.save(profileContent);
	}
	
}
