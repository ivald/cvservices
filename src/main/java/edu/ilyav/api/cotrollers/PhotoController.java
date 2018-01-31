package edu.ilyav.api.cotrollers;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import edu.ilyav.api.models.PhotoUpload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by ilyav on 17/08/17.
 */
@RestController
@RequestMapping("/rest")
@PropertySource(ignoreResourceNotFound = true, value = "classpath:application.properties")
public class PhotoController {

    @Value("${cloudName}")
    private String cloudName;

    @Value("${apiKey}")
    private String apiKey;

    @Value("${apiSecret}")
    private String apiSecret;

    @RequestMapping(value = "/private/photo/profile", method = RequestMethod.POST)
    public Map uploadProfileImage(HttpServletRequest request) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Iterator<String> it = multipartRequest.getFileNames();
        MultipartFile multipartFile = multipartRequest.getFile(it.next());

        PhotoUpload photoUpload = new PhotoUpload();
        photoUpload.setFile(multipartFile);

        return uploadImage(photoUpload);
    }

    @RequestMapping(value = "/private/photo/upload", method = RequestMethod.POST)
    public Map uploadImage(@ModelAttribute PhotoUpload photoUpload) {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", this.cloudName,
                "api_key", this.apiKey,
                "api_secret", this.apiSecret));

        Map uploadResult = null;
        try {
            uploadResult = cloudinary.uploader().upload(photoUpload.getFile().getBytes(), ObjectUtils.emptyMap());
            System.out.print(uploadResult);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return uploadResult;
    }

}