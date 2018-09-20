package edu.ilyav.api.service.impl;

import edu.ilyav.api.dao.LanguageRepository;
import edu.ilyav.api.models.Language;
import edu.ilyav.api.models.Profile;
import edu.ilyav.api.service.LanguageService;
import edu.ilyav.api.service.ProfileService;
import edu.ilyav.api.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LanguageServiceImpl extends BaseServiceImpl implements LanguageService {

	@Autowired
	private ProfileService profileService;

	@Autowired
	private LanguageRepository languageRepository;
	
	@Override
	public List<Language> findAll() {
		return languageRepository.findAll();
	}

	@Override
	public Language findById(Long id){
		return languageRepository.findById(id).get();
	}

	@Override
	public void delete(Long id){
		languageRepository.deleteById(id);
	}
	
	@Override
	public Language saveOrUpdate(Language language) throws ResourceNotFoundException {
		Optional<Profile> profile = Optional.ofNullable(profileService.findById(language.getProfileId()));
		if(!profile.isPresent()) {
			throw new ResourceNotFoundException("Profile not found");
		}
		updateHomeProfileObjects();
		language.setProfile(profile.get());
		return languageRepository.save(language);
	}
	
}
