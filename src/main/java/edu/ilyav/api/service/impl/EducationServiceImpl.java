package edu.ilyav.api.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import edu.ilyav.api.dao.EducationRepository;
import edu.ilyav.api.models.Education;
import edu.ilyav.api.models.Image;
import edu.ilyav.api.models.ProfileContent;
import edu.ilyav.api.service.EducationService;
import edu.ilyav.api.service.exceptions.CloudinaryException;
import edu.ilyav.api.service.exceptions.EducationServiceException;
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
public class EducationServiceImpl extends BaseServiceImpl implements EducationService {

    @Value("${cloudName}")
    private String cloudName;

    @Value("${apiKey}")
    private String apiKey;

    @Value("${apiSecret}")
    private String apiSecret;

    @Autowired
    private EducationRepository educationRepository;

    @Override
    public List<Education> findAll() {
        return educationRepository.findAll();
    }

    @Override
    public Education findById(Long id) {
        return educationRepository.findById(id).get();
    }

    @Override
    @Transactional
    public String delete(Long id) throws EducationServiceException, CloudinaryException, ResourceNotFoundException {
        Map result = new HashMap<>();

        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", this.cloudName,
                "api_key", this.apiKey,
                "api_secret", this.apiSecret));

        Optional<Education> education = educationRepository.findById(id);
        if (education.isPresent()) {
            List list = getProfileContentService().findById(education.get().getProfileContentId()).getEducationList();
            if (list.isEmpty()) {
                throw new EducationServiceException("Education object cannot be removed. Education list is empty.");
            } else {
                List<Image> images = education.get().getImageList();
                result = removeImageFromCloudinary(result, cloudinary, images);
                ListIterator it = list.listIterator();
                checkBaseObjExist(education.get(), it, Constants.EDUCATION);
                educationRepository.deleteById(id);
                updateHomeProfileObjects();
                result.put("result", "ok");
            }
        } else {
            throw new EducationServiceException("Education id: " + id.toString() + " does not exist.");
        }

        return result.toString();
    }

    @Override
    public Education saveOrUpdate(Education education) throws ResourceNotFoundException {
        Optional<ProfileContent> profileContent = getProfileContent(education.getProfileContentId());
        education.setProfileContent(profileContent.get());
        updateHomeProfileObjects();
        return educationRepository.save(education);
    }

}
