package edu.ilyav.api.service.impl;

import edu.ilyav.api.dao.ProfileContentRepository;
import edu.ilyav.api.dao.ProfileRepository;
import edu.ilyav.api.dao.SummaryRepository;
import edu.ilyav.api.dao.UserRepository;
import edu.ilyav.api.models.*;
import edu.ilyav.api.service.UserService;
import edu.ilyav.api.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private SummaryRepository summaryRepository;

	@Autowired
	private ProfileContentRepository profileContentRepository;
	
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

	@Override
	public UserInfo createNewOne(UserInfo userInfo) {
		Summary summary = new Summary();
		summary = summaryRepository.save(summary);

		List<Education> educationList = new ArrayList<>();
		List<Experience> experienceList = new ArrayList<>();

		ProfileContent profileContent = new ProfileContent();
		profileContent.setSummaryId(summary.getId());
		profileContent.setSummary(summary);
		profileContent.setEducationList(educationList);
		profileContent.setExperienceList(experienceList);
		profileContent = profileContentRepository.save(profileContent);

		summary.setProfileContent(profileContent);
		summary.setProfileContentId(profileContent.getId());
		summaryRepository.save(summary);

		List<Language> languageList = new ArrayList<>();

		Profile profile = new Profile();
		profile.setProfileContent(profileContent);
		profile.setLanguageList(languageList);
		profile = profileRepository.save(profile);

		profileContent.setProfile(profile);
		profileContent.setProfileId(profile.getId());

		userInfo.setProfileId(profile.getId());
		userInfo.setProfile(profile);
		userInfo = userRepository.save(userInfo);

		profile.setUserInfoId(userInfo.getId());
		profile.setUserInfo(userInfo);
		profileRepository.save(profile);

		return userInfo;
	}
	
}
