package edu.ilyav.api.cotrollers;

import edu.ilyav.api.models.Profile;
import edu.ilyav.api.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

//	@Autowired
//	private UserService userService;
	
	@RequestMapping(value="login", method= RequestMethod.POST)
	public String login(@RequestBody Map<String, String> json) throws ServletException {
		if(json.get("username") == null || json.get("password") == null) {
			throw new ServletException("Please fill in username and password");
		}
		
		String userName = json.get("username");
		String password = json.get("password");
		
//		Profile user = userService.findByUserName(userName);
		Profile user = new Profile();
		user.setUserName("ilyav");
		user.setPassword("Ivald79");

		if(user == null) {
			throw new ServletException("User name not found.");
		}
		
		String pwd = user.getPassword();
		
		if (!password.equals(pwd)) {
			throw new ServletException("Invalid login. Please check your username and password");
		}
		
		return Jwts.builder().setSubject(userName).claim("roles", "user").setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256, "secretkey").compact();
		
	}
	
//	@RequestMapping(value="/register", method = RequestMethod.POST)
//	public Profile registerUser(@RequestBody Profile user) {
//		return userService.save(user);
//	}
}
