package edu.ilyav.api.service;

import edu.ilyav.api.models.Image;
import edu.ilyav.api.models.PhotoUpload;
import edu.ilyav.api.models.WebResponse;
import edu.ilyav.api.service.exceptions.CloudinaryException;
import edu.ilyav.api.service.exceptions.ResourceNotFoundException;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by ilyav on 19/05/18.
 */
public interface PhotoService {

    Image uploadExperienceImage(@ModelAttribute PhotoUpload photoUpload) throws ResourceNotFoundException;

    Image uploadEducationImage(@ModelAttribute PhotoUpload photoUpload) throws ResourceNotFoundException, CloudinaryException;

    Image uploadExperienceLink(String url, Long id) throws IOException, ResourceNotFoundException, NoSuchAlgorithmException;

    Image uploadEducationLink(String url, Long id) throws IOException, ResourceNotFoundException, NoSuchAlgorithmException;

    String uploadImage(@ModelAttribute PhotoUpload photoUpload) throws Exception;

    WebResponse deleteImage(Long id, String objectType) throws Exception;

}
