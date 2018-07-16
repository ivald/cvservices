package edu.ilyav.api.service.impl;

import edu.ilyav.api.cotrollers.HomeController;
import edu.ilyav.api.cotrollers.ProfileController;
import edu.ilyav.api.models.BaseModule;

import java.util.ListIterator;

/**
 * Created by ilyav on 13/07/18.
 */
public class BaseServiceImpl {

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

}
