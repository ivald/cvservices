package edu.ilyav.api.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import edu.ilyav.api.dao.ExperienceRepository;
import edu.ilyav.api.models.Experience;
import edu.ilyav.api.models.Image;
import edu.ilyav.api.models.ProfileContent;
import edu.ilyav.api.service.ExperienceService;
import edu.ilyav.api.service.ProfileContentService;
import edu.ilyav.api.service.exceptions.CloudinaryException;
import edu.ilyav.api.service.exceptions.ExperienceServiceException;
import edu.ilyav.api.service.exceptions.ResourceNotFoundException;
import edu.ilyav.api.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
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
	private ProfileContentService profileContentService;

	@Autowired
	private ExperienceRepository experienceRepository;

	@Override
	public List<Experience> findAll() {
		return experienceRepository.findAll();
	}

	@Override
	public Experience findById(Long id){
		return experienceRepository.findById(id);
	}

	@Override
	@Transactional
	public String delete(Long id) throws ExperienceServiceException, CloudinaryException, ResourceNotFoundException {
		Map result = new HashMap<>();

		Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
				"cloud_name", this.cloudName,
				"api_key", this.apiKey,
				"api_secret", this.apiSecret));

		Optional<Experience> experience = Optional.ofNullable(experienceRepository.findById(id));
		if (experience.isPresent()) {
			List list = profileContentService.findById(experience.get().getProfileContentId()).getExperienceList();
			if (list.isEmpty()) {
				throw new ExperienceServiceException("Experience object cannot be removed. Experience list is empty.");
			} else {
				List<Image> images = experience.get().getImageList();
				if (!images.isEmpty()) {
					for(Image image : images)
						try {
							result = cloudinary.uploader().destroy(image.getPublicId(), null);
						} catch (IOException e) {
							e.printStackTrace();
							throw new CloudinaryException("Cloudinary delete image: " + e.getMessage());
						}
				}
				ListIterator it = list.listIterator();
				checkBaseObjExist(experience.get(), it, Constants.EXPERIENCE);
				experienceRepository.delete(id);
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
		Optional<ProfileContent> profileContent = Optional.ofNullable(profileContentService.findById(experience.getProfileContentId()));
		if(!profileContent.isPresent()) {
			throw new ResourceNotFoundException("ProfileContent not found");
		}
		experience.setProfileContent(profileContent.get());
		updateHomeProfileObjects();
		return experienceRepository.save(experience);
	}

}
