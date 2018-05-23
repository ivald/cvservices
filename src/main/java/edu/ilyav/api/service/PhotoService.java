package edu.ilyav.api.service;

import edu.ilyav.api.models.Image;
import edu.ilyav.api.models.PhotoUpload;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.IOException;

/**
 * Created by ilyav on 19/05/18.
 */
public interface PhotoService {

    String uploadExperienceImage(@ModelAttribute PhotoUpload photoUpload);

    String uploadEducationImage(@ModelAttribute PhotoUpload photoUpload);

    Image uploadExperienceLink(String url, Long id) throws IOException;

    Image uploadEducationLink(String url, Long id) throws IOException;

}
