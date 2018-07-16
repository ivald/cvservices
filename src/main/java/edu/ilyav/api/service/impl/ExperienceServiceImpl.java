package edu.ilyav.api.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import edu.ilyav.api.cotrollers.HomeController;
import edu.ilyav.api.cotrollers.ProfileController;
import edu.ilyav.api.dao.ExperienceRepository;
import edu.ilyav.api.models.Experience;
import edu.ilyav.api.models.Image;
import edu.ilyav.api.models.ProfileContent;
import edu.ilyav.api.service.ExperienceService;
import edu.ilyav.api.service.ImageService;
import edu.ilyav.api.service.PhotoService;
import edu.ilyav.api.service.ProfileContentService;
import edu.ilyav.api.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

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
	public String delete(Long id) throws Exception {
		Map result = new HashMap<>();

		Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
				"cloud_name", this.cloudName,
				"api_key", this.apiKey,
				"api_secret", this.apiSecret));

		try {
			Experience experience = experienceRepository.findById(id);
			if (experience != null) {
				List list = profileContentService.findById(experience.getProfileContentId()).getExperienceList();
				if (list.isEmpty()) {
					throw new Exception("Experience object cannot be removed. Experience list is empty.");
				} else {
					List<Image> images = experience.getImageList();
					if (!images.isEmpty()) {
						for(Image image : images)
							result = cloudinary.uploader().destroy(image.getPublicId(), null);
					}
					ListIterator it = list.listIterator();
					checkBaseObjExist(experience, it, Constants.EXPERIENCE);
					experienceRepository.delete(id);
					updateHomeProfileObjects();
					result.put("result", "ok");
				}
			} else {
				throw new Exception("Experience id does not exist.");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return result.toString();
	}
	
	@Override
	public Experience saveOrUpdate(Experience experience) {
		ProfileContent profileContent = profileContentService.findById(experience.getProfileContentId());
		experience.setProfileContent(profileContent);
		updateHomeProfileObjects();
		return experienceRepository.save(experience);
	}

}
