package edu.ilyav.api.cotrollers;

import edu.ilyav.api.models.Image;
import edu.ilyav.api.models.PhotoUpload;
import edu.ilyav.api.models.WebResponse;
import edu.ilyav.api.service.PhotoService;
import edu.ilyav.api.service.exceptions.CloudinaryException;
import edu.ilyav.api.service.exceptions.ResourceNotFoundException;
import edu.ilyav.api.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Optional;

/**
 * Created by ilyav on 17/08/17.
 */
@RestController
@RequestMapping("/rest")
@PropertySource(ignoreResourceNotFound = true, value = "classpath:application.properties")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @RequestMapping(value = "/private/photo/profile/{id}", method = RequestMethod.POST)
    public Image uploadProfileImage(HttpServletRequest request, @PathVariable Long id) throws Exception {
        PhotoUpload photoUpload = parseMultipartReqToPhotoUpload(request);
        photoUpload.setProfileId(id);
        String url = uploadImage(photoUpload);

        Image image = new Image();
        image.setImageUrl(url);

        return image;
    }

    @RequestMapping(value = "/private/experience/link/{id}", method = RequestMethod.POST)
    public Image uploadExpLinkImage(HttpServletRequest request, @PathVariable Long id) throws ResourceNotFoundException, NoSuchAlgorithmException {
        Optional<MultipartHttpServletRequest> multipartRequest = Optional.of((MultipartHttpServletRequest) request);
        Image image = new Image();

        try {
            if (multipartRequest.isPresent() && multipartRequest.get().getParameter("url") != null) {
                image = photoService.uploadExperienceLink(multipartRequest.get().getParameter("url"), id);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    @RequestMapping(value = "/private/education/link/{id}", method = RequestMethod.POST)
    public Image uploadEduLinkImage(HttpServletRequest request, @PathVariable Long id) throws ResourceNotFoundException, NoSuchAlgorithmException {
        Optional<MultipartHttpServletRequest> multipartRequest = Optional.of((MultipartHttpServletRequest) request);
        Image image = new Image();

        try {
            if (multipartRequest.isPresent() && multipartRequest.get().getParameter("url") != null) {
                image = photoService.uploadEducationLink(multipartRequest.get().getParameter("url"), id);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    @RequestMapping(value = "/private/photo/experience/{id}/{desc}", method = RequestMethod.POST)
    public Image uploadExperienceImage(HttpServletRequest request, @PathVariable Long id, @PathVariable String desc) throws ResourceNotFoundException {
        PhotoUpload photoUpload = parseMultipartReqToPhotoUpload(request);
        photoUpload.setExperienceId(id);
        photoUpload.setTitle(desc);
        return photoService.uploadExperienceImage(photoUpload);
    }

    @RequestMapping(value = "/private/photo/education/{id}/{desc}", method = RequestMethod.POST)
    public Image uploadEducationImage(HttpServletRequest request, @PathVariable Long id, @PathVariable String desc) throws ResourceNotFoundException, CloudinaryException {
        PhotoUpload photoUpload = parseMultipartReqToPhotoUpload(request);
        photoUpload.setEducationId(id);
        photoUpload.setTitle(desc);
        return photoService.uploadEducationImage(photoUpload);
    }

    private PhotoUpload parseMultipartReqToPhotoUpload(HttpServletRequest request) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Iterator<String> it = multipartRequest.getFileNames();
        MultipartFile multipartFile = multipartRequest.getFile(it.next());

        PhotoUpload photoUpload = new PhotoUpload();
        photoUpload.setFile(multipartFile);

        return photoUpload;
    }

    @RequestMapping(value = "/private/photo/upload", method = RequestMethod.POST)
    public String uploadImage(@ModelAttribute PhotoUpload photoUpload) throws Exception {
        return photoService.uploadImage(photoUpload);
    }

    @RequestMapping(value = "/private/image/education/delete/{id}", method = RequestMethod.DELETE)
    public WebResponse deleteEducationImage(@PathVariable Long id) throws Exception {
        return photoService.deleteImage(id, Constants.EDUCATION);
    }

    @RequestMapping(value = "/private/image/experience/delete/{id}", method = RequestMethod.DELETE)
    public WebResponse deleteExperienceImage(@PathVariable Long id) throws Exception {
        return photoService.deleteImage(id, Constants.EXPERIENCE);
    }

}