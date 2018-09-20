package edu.ilyav.api.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import edu.ilyav.api.dao.ExperienceRepository;
import edu.ilyav.api.models.Experience;
import edu.ilyav.api.models.Image;
import edu.ilyav.api.models.ProfileContent;
import edu.ilyav.api.service.ExperienceService;
import edu.ilyav.api.service.exceptions.CloudinaryException;
import edu.ilyav.api.service.exceptions.ExperienceServiceException;
import edu.ilyav.api.service.exceptions.ResourceNotFoundException;
import edu.ilyav.api.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@PropertySource(ignoreResourceNotFound = true, value = "classpath:application.properties")
public class ExperienceServiceImpl extends BaseServiceImpl implements ExperienceService {

	@Value("${cloudName}")
	private String cloudName;

	@Value("${apiKey}")
	private String apiKey;

	@Value("${apiSecret}")
	private String apiSecret;

	@Autowired
	private ExperienceRepository experienceRepository;

	@Override
	public List<Experience> findAll() {
		return experienceRepository.findAll();
	}

	@Override
	public Experience findById(Long id){
		return experienceRepository.findById(id).get();
	}

	@Override
	@Transactional
	public String delete(Long id) throws ExperienceServiceException, CloudinaryException, ResourceNotFoundException {
		Map result = new HashMap<>();

		Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
				"cloud_name", this.cloudName,
				"api_key", this.apiKey,
				"api_secret", this.apiSecret));

		Optional<Experience> experience = experienceRepository.findById(id);
		if (experience.isPresent()) {
			List list = getProfileContentService().findById(experience.get().getProfileContentId()).getExperienceList();
			if (list.isEmpty()) {
				throw new ExperienceServiceException("Experience object cannot be removed. Experience list is empty.");
			} else {
				List<Image> images = experience.get().getImageList();
				result = removeImageFromCloudinary(result, cloudinary, images);
				ListIterator it = list.listIterator();
				checkBaseObjExist(experience.get(), it, Constants.EXPERIENCE);
				experienceRepository.deleteById(id);
				updateHomeProfileObjects();
				result.put("result", "ok");
			}
		} else {
			throw new ExperienceServiceException("Experience id: " + id.toString() + " does not exist.");
		}

		return result.toString();
	}
	
	@Override
	public Experience saveOrUpdate(Experience experience) throws ResourceNotFoundException {
		Optional<ProfileContent> profileContent = getProfileContent(experience.getProfileContentId());
		experience.setProfileContent(profileContent.get());
		updateHomeProfileObjects();
		return experienceRepository.save(experience);
	}

}
