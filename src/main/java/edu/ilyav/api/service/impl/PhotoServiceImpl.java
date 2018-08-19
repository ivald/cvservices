package edu.ilyav.api.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import edu.ilyav.api.models.*;
import edu.ilyav.api.service.*;
import edu.ilyav.api.util.Constants;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ilyav on 19/05/18.
 */
@Service
@PropertySource(ignoreResourceNotFound = true, value = "classpath:application.properties")
public class PhotoServiceImpl extends BaseServiceImpl implements PhotoService {

    @Value("${cloudName}")
    private String cloudName;

    @Value("${apiKey}")
    private String apiKey;

    @Value("${apiSecret}")
    private String apiSecret;

    @Autowired
    private ExperienceService experienceService;

    @Autowired
    private EducationService educationService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ProfileService profileService;

    public String uploadExperienceImage(@ModelAttribute PhotoUpload photoUpload) {
        Experience experience = null;
        List<Image> images = null;
        Image image = null;
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", this.cloudName,
                "api_key", this.apiKey,
                "api_secret", this.apiSecret));

        Map uploadResult;
        try {
            uploadResult = cloudinary.uploader().upload(photoUpload.getFile().getBytes(), ObjectUtils.emptyMap());
            System.out.print(uploadResult);

            experience = experienceService.findById(photoUpload.getExperienceId());

            if(experience.getImageList() == null || experience.getImageList().isEmpty())
                images = new ArrayList<>();
            else
                images = experience.getImageList();

            image = new Image();
            image.setImageUrl(uploadResult.get("secure_url").toString());
            image.setPublicId(uploadResult.get("public_id").toString());
            image.setExperienceId(experience.getId());
            image.setDescription(photoUpload.getTitle());
            image.setExperience(experience);
            image = imageService.saveOrUpdate(image);
            images.add(image);

            experience.setImageList(images);
            experienceService.saveOrUpdate(experience);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return image.getImageUrl() + "@" + image.getPublicId() + "@" + image.getId();
    }

    public String uploadEducationImage(@ModelAttribute PhotoUpload photoUpload) {
        Education education;
        List<Image> images;
        Image image = null;
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", this.cloudName,
                "api_key", this.apiKey,
                "api_secret", this.apiSecret));

        Map uploadResult;
        try {
            uploadResult = cloudinary.uploader().upload(photoUpload.getFile().getBytes(), ObjectUtils.emptyMap());
            System.out.print(uploadResult);

            education = educationService.findById(photoUpload.getEducationId());

            if(education.getImageList() == null || education.getImageList().isEmpty())
                images = new ArrayList<>();
            else
                images = education.getImageList();

            image = new Image();
            image.setImageUrl(uploadResult.get("secure_url").toString());
            image.setPublicId(uploadResult.get("public_id").toString());
            image.setEducationId(education.getId());
            image.setEducation(education);
            image.setDescription(photoUpload.getTitle());
            image = imageService.saveOrUpdate(image);
            images.add(image);

            education.setImageList(images);
            educationService.saveOrUpdate(education);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return image.getImageUrl() + "@" + image.getPublicId() + "@" + image.getId();
    }

    public Image uploadExperienceLink(String url, Long id) throws IOException {
        Experience experience;
        List<Image> images;
        Image image;

        experience = experienceService.findById(id);

        if(experience.getImageList() == null || experience.getImageList().isEmpty())
            images = new ArrayList<>();
        else
            images = experience.getImageList();

        image = urlAnalise(url);
        image.setExperienceId(experience.getId());
        image.setExperience(experience);
        image = imageService.saveOrUpdate(image);
        images.add(image);

        experience.setImageList(images);
        experienceService.saveOrUpdate(experience);

        return image;
    }

    public Image uploadEducationLink(String url, Long id) throws IOException {
        Education education;
        List<Image> images;
        Image image;

        education = educationService.findById(id);

        if(education.getImageList() == null || education.getImageList().isEmpty())
            images = new ArrayList<>();
        else
            images = education.getImageList();

        image = urlAnalise(url);
        image.setEducationId(education.getId());
        image.setEducation(education);
        image = imageService.saveOrUpdate(image);
        images.add(image);

        education.setImageList(images);
        educationService.saveOrUpdate(education);

        return image;
    }

    private Image urlAnalise(String url) throws IOException {
        Document doc;
        Image image = new Image();
        try {
            doc = Jsoup.connect(url).get();

            Elements meta = doc.select("meta[content]");
            Elements title = doc.select("title");
            Elements media = doc.select("[src]");

            for (Element src : meta) {
                String s = src.attr("property");
                if (!"".equals(s)) {
                    Pattern p = Pattern.compile("image");   // the pattern to search for
                    Matcher m = p.matcher(s);
                    if (m.find()) {
                        image.setImageUrl(src.attr("content"));
                    }
                }

                s = src.attr("name");
                if ("description".equals(s)) {
                    image.setDescription(src.attr("content"));
                }
            }

            for (Element src : title) {
                Optional<String> t = Optional.ofNullable(src.html().toString().replaceAll("amp;", ""));
                if(t.isPresent()) image.setTitle(t.get());
            }

            Optional<String> imageUrl = Optional.ofNullable(image.getImageUrl());
            if(!imageUrl.isPresent()) {
                media.stream().filter(src -> src.tagName().equals("img")).forEach(src -> {
                    if (src.attr("abs:src").split("logo").length > 1 || src.attr("abs:src").split("card").length > 1) {
                        image.setImageUrl(src.attr("abs:src"));
                    }
                });
            }

            imageUrl = Optional.ofNullable(image.getImageUrl());
            if(imageUrl.isPresent()) {
                image.setSourceUrl(url);
                createImagePublicId(image);
            } else {
                image.setImageUrl(Constants.WHITE_IMAGE);
                createImagePublicId(image);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return image;
    }

    private void createImagePublicId(Image image) {
        try {

            //Initialize SecureRandom: NativePRNG, SHA1PRNG
            SecureRandom prng = new SecureRandom();

            //generate a random number
            String randomNum = new Integer(prng.nextInt()).toString();

            //get its digest
            //Algorithm Name: MD2, MD5, SHA-1, SHA-256, SHA-384, SHA-512
            MessageDigest sha = MessageDigest.getInstance("MD2");
            byte[] result = sha.digest(randomNum.getBytes());

            image.setPublicId(hexEncode(result));
        } catch (NoSuchAlgorithmException ex) {
            System.err.println(ex);
        }
    }

    /**
     * The byte[] returned by MessageDigest does not have a nice
     * textual representation, so some form of encoding is usually performed.
     *
     * This implementation follows the example of David Flanagan's book
     * "Java In A Nutshell", and converts a byte array into a String
     * of hex characters.
     *
     * Another popular alternative is to use a "Base64" encoding.
     */
    static private String hexEncode(byte[] aInput){
        StringBuilder result = new StringBuilder();
        char[] digits = {'0', '1', '2', '3', '4','5','6','7','8','9','a','b','c','d','e','f'};
        for (int idx = 0; idx < aInput.length; ++idx) {
            byte b = aInput[idx];
            result.append(digits[ (b&0xf0) >> 4 ]);
            result.append(digits[ b&0x0f]);
        }
        return result.toString();
    }

    private void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

    public String uploadImage(@ModelAttribute PhotoUpload photoUpload) throws Exception {
        Profile profile = null;
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", this.cloudName,
                "api_key", this.apiKey,
                "api_secret", this.apiSecret));
        Map uploadResult;
        try {
            uploadResult = cloudinary.uploader().upload(photoUpload.getFile().getBytes(), ObjectUtils.emptyMap());
            System.out.print(uploadResult);

            profile = profileService.findById(photoUpload.getProfileId());
            Optional<String> publicId = Optional.ofNullable(profile.getPublicId());

            if(publicId.isPresent() && !publicId.get().isEmpty()) {
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

    public String deleteImage(Long id, String objectType) throws Exception {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", this.cloudName,
                "api_key", this.apiKey,
                "api_secret", this.apiSecret));
        Map result;
        try {
            Optional<Image> image = imageService.findById(id);
            if(!image.isPresent()) {
                throw new Exception("Image id does not exist.");
            }
            Optional<String> publicId = Optional.ofNullable(image.get().getPublicId());
            if(publicId.isPresent()) {
                result = cloudinary.uploader().destroy(image.get().getPublicId(), null);
            } else {
                result = new HashMap();
                result.put("result", "empty");
            }
            ListIterator it;
            Optional<Long> objectId = Optional.ofNullable(getObjectIdByObjectType(image.get(), objectType));
            if(!"not found".equals(result.get("result")) && objectId.isPresent()) {
                it = getListIteratorByObjectType(image.get(), objectType);
                checkBaseObjExist(image.get(), it, objectType);
                imageService.delete(image.get().getId());
            } else if (image.isPresent()) {
                if (objectId.isPresent()) {
                    it = getListIteratorByObjectType(image.get(), objectType);
                    checkBaseObjExist(image.get(), it, objectType);
                } else {
                    it = getAllListIteratorByObjectType(objectType);
                    checkExpEduImgExist(image.get(), it, objectType);
                }
                imageService.delete(image.get().getId());
                result.put("result", "ok");
            }
            updateHomeProfileObjects();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }

        return result.toString();
    }

    private ListIterator<Image> getListIteratorByObjectType(Image image, String objectType) {
        if(Constants.EXPERIENCE.equals(objectType)) {
            Optional<Experience> exp = Optional.ofNullable(image.getExperience());
            if(exp.isPresent())
                return exp.get().getImageList().listIterator();
            else
                return experienceService.findById(image.getExperienceId()).getImageList().listIterator();
        } else {
            Optional<Education> edu = Optional.ofNullable(image.getEducation());
            if(edu.isPresent())
                return edu.get().getImageList().listIterator();
            else
                return educationService.findById(image.getEducationId()).getImageList().listIterator();
        }
    }

    private ListIterator getAllListIteratorByObjectType(String objectType) {
        return (Constants.EXPERIENCE.equals(objectType) ? experienceService.findAll().listIterator() :
                educationService.findAll().listIterator());
    }

    private Long getObjectIdByObjectType(Image image, String objectType) {
        if(Constants.EXPERIENCE.equals(objectType)) {
            Optional<Experience> exp = Optional.ofNullable(image.getExperience());
            if(exp.isPresent())
                return exp.get().getId();
            else
                return experienceService.findById(image.getExperienceId()).getId();
        } else {
            Optional<Education> edu = Optional.ofNullable(image.getEducation());
            if(edu.isPresent())
                return edu.get().getId();
            else
                return educationService.findById(image.getEducationId()).getId();
        }
    }

    private void checkExpEduImgExist(Image image, ListIterator it, String type) {
        ListIterator itImg;
        while (it.hasNext()) {
            itImg = ("Experience".equals(type) ? ((Experience) it.next()).getImageList().listIterator() :
                    ((Education) it.next()).getImageList().listIterator());
            checkBaseObjExist(image, itImg, type);
        }
    }
}
