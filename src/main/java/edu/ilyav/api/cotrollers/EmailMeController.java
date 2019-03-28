package edu.ilyav.api.cotrollers;

import edu.ilyav.api.models.EmailMe;
import edu.ilyav.api.service.EmailMeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/public")
public class EmailMeController {

	@Autowired
	private EmailMeService emailMeService;

	@RequestMapping(value="/emailMe", method = RequestMethod.POST)
	public EmailMe addAMessage(@RequestBody EmailMe email) {
		EmailMe me = new EmailMe();
		try {
			emailMeService.saveOrUpdate(email);
			me.setMessage("Your message has been sent.");
		} catch (Exception e) {
			me.setError("Your message has not been sent.");
		}

		return me;
	}
}
