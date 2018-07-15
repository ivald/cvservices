package edu.ilyav.api.service.impl;

import edu.ilyav.api.cotrollers.HomeController;
import edu.ilyav.api.cotrollers.ProfileController;
import edu.ilyav.api.dao.LanguageRepository;
import edu.ilyav.api.models.Language;
import edu.ilyav.api.models.Profile;
import edu.ilyav.api.service.LanguageService;
import edu.ilyav.api.service.ProfileService;
import edu.ilyav.api.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageServiceImpl implements LanguageService {

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
		return languageRepository.findById(id);
	}

	@Override
	public void delete(Long id){
		languageRepository.delete(id);
	}
	
	@Override
	public Language saveOrUpdate(Language language) {
		Profile profile = profileService.findById(language.getProfileId());
		language.setProfile(profile);
		Constants.updateHomeProfileObjects();
		return languageRepository.save(language);
	}
	
}
