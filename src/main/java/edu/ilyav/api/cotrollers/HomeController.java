package edu.ilyav.api.cotrollers;

import edu.ilyav.api.models.Profile;
import edu.ilyav.api.models.ProfileContent;
import edu.ilyav.api.service.exceptions.ResourceNotFoundException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ilyav on 17/08/17.
 */
@RestController
@RequestMapping("/rest/public")
public class HomeController extends BaseController {

    @RequestMapping(value = "/run", method = RequestMethod.GET)
    public Boolean isRunning() {
        return Boolean.TRUE;
    }

    @RequestMapping(value = "/home/{id}", method = RequestMethod.GET)
    public ProfileContent home(@PathVariable Long id) throws ResourceNotFoundException {
        return getProfile(id).getProfileContent();
    }

    @RequestMapping(value = "/main/home/{userName}", method = RequestMethod.GET)
    public Profile home(@PathVariable String userName) throws ResourceNotFoundException {
        return getProfile(userName);
    }

}
