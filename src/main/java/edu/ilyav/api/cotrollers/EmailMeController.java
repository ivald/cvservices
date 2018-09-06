package edu.ilyav.api.cotrollers;

import edu.ilyav.api.models.EmailMe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/public")
@PropertySource(ignoreResourceNotFound = true, value = "classpath:application.properties")
public class EmailMeController {

	@Value("${spring.mail.username}")
	private String username;

	@Value("${spring.mail.cc}")
	private String cc;

	@Autowired
	public JavaMailSender emailSender;

	@RequestMapping(value="/emailMe", method = RequestMethod.POST)
	public EmailMe sendMeAMessage(@RequestBody EmailMe email) {
		EmailMe me = new EmailMe();
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(this.username);
		message.setCc(this.cc);
		message.setSubject(email.getName() + " <" + email.getEmail() + '>');
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
