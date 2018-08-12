package edu.ilyav.api.cotrollers;

import edu.ilyav.api.models.EmailMe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/public")
public class EmailMeController {

	@Autowired
	public JavaMailSender emailSender;

	@RequestMapping(value="/emailMe", method = RequestMethod.POST)
	public EmailMe forgotPassword(@RequestBody EmailMe email) {
		EmailMe me = new EmailMe();
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email.getEmail());
		message.setSubject(email.getName());
		message.setText(email.getMessage());
		try {
			emailSender.send(message);
			me.setMessage("Your message has been sent.");
		} catch (Exception e) {
			me.setError("Your message has not been sent.");
		}


		return me;
	}



}
