package edu.ilyav.api.service.impl;

import com.cloudinary.Cloudinary;
import edu.ilyav.api.cotrollers.HomeController;
import edu.ilyav.api.cotrollers.ProfileController;
import edu.ilyav.api.models.BaseModule;
import edu.ilyav.api.models.Image;
import edu.ilyav.api.models.ProfileContent;
import edu.ilyav.api.service.ProfileContentService;
import edu.ilyav.api.service.exceptions.CloudinaryException;
import edu.ilyav.api.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Optional;

/**
 * Created by ilyav on 13/07/18.
 */
public class BaseServiceImpl {

    private ProfileContentService profileContentService;

    protected void checkBaseObjExist(BaseModule object, ListIterator it, String type) {
        while (it.hasNext()) {
            BaseModule obj = (BaseModule) it.next();
            if (object.getId().equals(obj.getId())) {
                it.remove();
                System.out.println(type + " Object: " + obj.getId() + " was removed.");
            }
        }
    }

    public static void updateHomeProfileObjects() {
        HomeController.isChanged = Boolean.TRUE;
        ProfileController.isChanged = Boolean.TRUE;
    }

    protected Map removeImageFromCloudinary(Map result, Cloudinary cloudinary, List<Image> images) throws CloudinaryException {
        if (!images.isEmpty()) {
            for (Image image : images)
                try {
                    result = cloudinary.uploader().destroy(image.getPublicId(), null);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new CloudinaryException("Cloudinary delete image: " + e.getMessage());
                }
        }
        return result;
    }

    protected Optional<ProfileContent> getProfileContent(Long id) throws ResourceNotFoundException {
        Optional<ProfileContent> profileContent = Optional.ofNullable(profileContentService.findById(id));
        if (!profileContent.isPresent()) {
            throw new ResourceNotFoundException("ProfileContent id: " + id.toString() + " not found");
        }
        return profileContent;
    }

    public ProfileContentService getProfileContentService() {
        return profileContentService;
    }

    @Autowired
    public void setProfileContentService(ProfileContentService profileContentService) {
        this.profileContentService = profileContentService;
    }
}
