package edu.ilyav.api.cotrollers;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import edu.ilyav.api.models.*;
import edu.ilyav.api.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * Created by ilyav on 17/08/17.
 */
@RestController
@RequestMapping("/rest")
@PropertySource(ignoreResourceNotFound = true, value = "classpath:application.properties")
public class PhotoController {

    public final static String EDUCATION = "Education";
    public final static String EXPERIENCE = "Experience";

    @Value("${cloudName}")
    private String cloudName;

    @Value("${apiKey}")
    private String apiKey;

    @Value("${apiSecret}")
    private String apiSecret;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ExperienceService experienceService;

    @Autowired
    private EducationService educationService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private PhotoService photoService;

    @RequestMapping(value = "/private/photo/profile/{id}", method = RequestMethod.POST)
    public String uploadProfileImage(HttpServletRequest request, @PathVariable Long id) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Iterator<String> it = multipartRequest.getFileNames();
        MultipartFile multipartFile = multipartRequest.getFile(it.next());

        PhotoUpload photoUpload = new PhotoUpload();
        photoUpload.setFile(multipartFile);
        photoUpload.setProfileId(id);
        return uploadImage(photoUpload);
    }

    @RequestMapping(value = "/private/experience/link/{id}", method = RequestMethod.POST)
    public String uploadExpLinkImage(HttpServletRequest request, @PathVariable Long id) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Image image = new Image();

        try {
            if (multipartRequest != null && multipartRequest.getParameter("url") != null) {
                image = photoService.uploadExperienceLink(multipartRequest.getParameter("url"), id);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image.getImageUrl() + "@" +
                image.getPublicId() + "@" +
                image.getId() + "@" +
                image.getTitle() + "@" +
                image.getSourceUrl() + "@" +
                image.getDescription();
    }

    @RequestMapping(value = "/private/education/link/{id}", method = RequestMethod.POST)
    public String uploadEduLinkImage(HttpServletRequest request, @PathVariable Long id) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Image image = new Image();

        try {
            if (multipartRequest != null && multipartRequest.getParameter("url") != null) {
                image = photoService.uploadEducationLink(multipartRequest.getParameter("url"), id);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image.getImageUrl() + "@" +
                image.getPublicId() + "@" +
                image.getId() + "@" +
                image.getTitle() + "@" +
                image.getSourceUrl() + "@" +
                image.getDescription();
    }

    @RequestMapping(value = "/private/photo/experience/{id}/{desc}", method = RequestMethod.POST)
    public String uploadExperienceImage(HttpServletRequest request, @PathVariable Long id, @PathVariable String desc) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Iterator<String> it = multipartRequest.getFileNames();
        MultipartFile multipartFile = multipartRequest.getFile(it.next());

        PhotoUpload photoUpload = new PhotoUpload();
        photoUpload.setFile(multipartFile);
        photoUpload.setExperienceId(id);
        photoUpload.setTitle(desc);
        return photoService.uploadExperienceImage(photoUpload);
    }

    @RequestMapping(value = "/private/photo/education/{id}/{desc}", method = RequestMethod.POST)
    public String uploadEducationImage(HttpServletRequest request, @PathVariable Long id, @PathVariable String desc) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Iterator<String> it = multipartRequest.getFileNames();
        MultipartFile multipartFile = multipartRequest.getFile(it.next());

        PhotoUpload photoUpload = new PhotoUpload();
        photoUpload.setFile(multipartFile);
        photoUpload.setEducationId(id);
        photoUpload.setTitle(desc);
        return photoService.uploadEducationImage(photoUpload);
    }

    @RequestMapping(value = "/private/photo/upload", method = RequestMethod.POST)
    public String uploadImage(@ModelAttribute PhotoUpload photoUpload) {
        Profile profile = null;
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", this.cloudName,
                "api_key", this.apiKey,
                "api_secret", this.apiSecret));
        Map uploadResult = null;
        try {
            uploadResult = cloudinary.uploader().upload(photoUpload.getFile().getBytes(), ObjectUtils.emptyMap());
            System.out.print(uploadResult);

            profile = profileService.findById(photoUpload.getProfileId());

            if(profile.getPublicId() != null && !profile.getPublicId().isEmpty()) {
                Map result = cloudinary.uploader().destroy(profile.getPublicId(), null);
            }

            profile.setImageUrl(uploadResult.get("secure_url").toString());
            profile.setPublicId(uploadResult.get("public_id").toString());

            profile = profileService.saveOrUpdate(profile);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return profile.getImageUrl();
    }

    @RequestMapping(value = "/private/image/education/delete", method = RequestMethod.POST)
    public String deleteEducationImage(@RequestBody Image image) {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", this.cloudName,
                "api_key", this.apiKey,
                "api_secret", this.apiSecret));
        Map result = null;
        try {
            result = cloudinary.uploader().destroy(image.getPublicId(), null);
            ListIterator it;
            if(!"not found".equals(result.get("result")) && image.getEducationId() != null) {
                it = educationService.findById(image.getEducationId()).getImageList().listIterator();
                checkImgExist(image, it, EDUCATION);
                imageService.delete(image.getId());
            } else if(imageService.findById(image.getId()) != null) {
                if(image.getEducationId() != null) {
                    it = educationService.findById(image.getEducationId()).getImageList().listIterator();
                    checkImgExist(image, it, EDUCATION);
                } else {
                    it = educationService.findAll().listIterator();
                    checkExpEduImgExist(image, it, EDUCATION);
                }
                imageService.delete(image.getId());
                result.put("result", "ok");
            }
            updateHomeProfileObjects();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    private void updateHomeProfileObjects() {
        HomeController.isChanged = Boolean.TRUE;
        ProfileController.isChanged = Boolean.TRUE;
    }

    @RequestMapping(value = "/private/image/experience/delete", method = RequestMethod.POST)
    public String deleteExperienceImage(@RequestBody Image image) {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", this.cloudName,
                "api_key", this.apiKey,
                "api_secret", this.apiSecret));
        Map result = null;
        try {
            result = cloudinary.uploader().destroy(image.getPublicId(), null);
            ListIterator it;
            if(!"not found".equals(result.get("result")) && image.getExperienceId() != null) {
                it = experienceService.findById(image.getExperienceId()).getImageList().listIterator();
                checkImgExist(image, it, EXPERIENCE);
                imageService.delete(image.getId());
            } else if(imageService.findById(image.getId()) != null) {
                if(image.getExperienceId() != null) {
                    it = experienceService.findById(image.getExperienceId()).getImageList().listIterator();
                    checkImgExist(image, it, EXPERIENCE);
                } else {
                    it = experienceService.findAll().listIterator();
                    checkExpEduImgExist(image, it, EXPERIENCE);
                }
                imageService.delete(image.getId());
                result.put("result", "ok");
            }
            updateHomeProfileObjects();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    private void checkExpEduImgExist(Image image, ListIterator it, String type) {
        ListIterator itImg;
        while (it.hasNext()) {
            itImg = ("Experience".equals(type) ? ((Experience) it.next()).getImageList().listIterator() :
                    ((Education) it.next()).getImageList().listIterator());
            checkImgExist(image, itImg, type);
        }
    }

    private void checkImgExist(Image image, ListIterator it, String type) {
        while (it.hasNext()) {
            Image img = (Image) it.next();
            if (image.getId().equals(img.getId())) {
                it.remove();
                System.out.println(type + " Image: " + img.getId() + " was removed.");
            }
        }
    }
}