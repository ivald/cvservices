package edu.ilyav.api.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import edu.ilyav.api.dao.EducationRepository;
import edu.ilyav.api.models.Education;
import edu.ilyav.api.models.Image;
import edu.ilyav.api.models.Profile;
import edu.ilyav.api.models.ProfileContent;
import edu.ilyav.api.service.EducationService;
import edu.ilyav.api.service.ProfileContentService;
import edu.ilyav.api.service.ProfileService;
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
public class EducationServiceImpl extends BaseServiceImpl implements EducationService {

	@Value("${cloudName}")
	private String cloudName;

	@Value("${apiKey}")
	private String apiKey;

	@Value("${apiSecret}")
	private String apiSecret;

	@Autowired
	private ProfileService profileService;

	@Autowired
	private ProfileContentService profileContentService;

	@Autowired
	private EducationRepository educationRepository;
	
	@Override
	public List<Education> findAll() {
		return educationRepository.findAll();
	}

	@Override
	public Education findById(Long id){
		return educationRepository.findById(id);
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
			Education education = educationRepository.findById(id);
			if (education != null) {
				List list = profileContentService.findById(education.getProfileContentId()).getEducationList();
				if (list.isEmpty()) {
					throw new Exception("Education object cannot be removed. Education list is empty.");
				} else {
					List<Image> images = education.getImageList();
					if (!images.isEmpty()) {
						for(Image image : images)
							result = cloudinary.uploader().destroy(image.getPublicId(), null);
					}
					ListIterator it = list.listIterator();
					checkBaseObjExist(education, it, Constants.EDUCATION);
					educationRepository.delete(id);
					updateHomeProfileObjects();
					result.put("result", "ok");
				}
			} else {
				throw new Exception("Education id does not exist.");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return result.toString();
	}
	
	@Override
	public Education saveOrUpdate(Education education) {
		ProfileContent profileContent = profileContentService.findById(education.getProfileContentId());
		education.setProfileContent(profileContent);
		updateHomeProfileObjects();
		return educationRepository.save(education);
	}
	
}
