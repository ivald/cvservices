package edu.ilyav.api.cotrollers;

import edu.ilyav.api.models.Image;
import edu.ilyav.api.models.PhotoUpload;
import edu.ilyav.api.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Iterator;

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
    public String uploadProfileImage(HttpServletRequest request, @PathVariable Long id) throws Exception {
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
    public String uploadImage(@ModelAttribute PhotoUpload photoUpload) throws Exception {
        return photoService.uploadImage(photoUpload);
    }

    @RequestMapping(value = "/private/image/education/delete/{id}", method = RequestMethod.DELETE)
    public String deleteEducationImage(@PathVariable Long id) throws Exception {
        return photoService.deleteEducationImage(id);
    }

    @RequestMapping(value = "/private/image/experience/delete/{id}", method = RequestMethod.DELETE)
    public String deleteExperienceImage(@PathVariable Long id) throws Exception {
        return photoService.deleteExperienceImage(id);
    }

}