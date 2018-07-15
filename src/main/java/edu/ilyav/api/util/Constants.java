package edu.ilyav.api.util;

import edu.ilyav.api.cotrollers.HomeController;
import edu.ilyav.api.cotrollers.ProfileController;

/**
 * Created by ilyav on 13/07/18.
 */
public class Constants {

    public final static String EDUCATION = "Education";
    public final static String EXPERIENCE = "Experience";

    public static void updateHomeProfileObjects() {
        HomeController.isChanged = Boolean.TRUE;
        ProfileController.isChanged = Boolean.TRUE;
    }

}
