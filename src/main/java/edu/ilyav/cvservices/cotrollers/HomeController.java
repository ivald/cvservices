package edu.ilyav.cvservices.cotrollers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ilyav on 17/08/17.
 */
@RestController
public class HomeController {

    @RequestMapping("/")
    public String home() {
        return "Hello, Ilyav!";
    }
}