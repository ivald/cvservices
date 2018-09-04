package edu.ilyav.api.cotrollers;

import edu.ilyav.api.models.UserInfo;
import edu.ilyav.api.service.UserService;
import edu.ilyav.api.service.exceptions.ResourceNotFoundException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

	private final String EMPTY = "";

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public UserInfo login(@RequestBody UserInfo userInfo) throws ServletException, ResourceNotFoundException {

		if (userInfo.getUserName() == null || userInfo.getPassword() == null) {
			throw new ServletException("Please fill in username and password");
		}

		UserInfo user = userService.findByUserName(userInfo.getUserName());

		if (user == null) {
			throw new ServletException("User name not found.");
		}

		if (!BCrypt.checkpw(userInfo.getPassword(), user.getPassword())) {
			throw new ServletException("Invalid login. Please check your username and password");
		}

		DateTime currentTime = new DateTime();

		user.setToken(Jwts.builder().setSubject(userInfo.getUserName()).claim("roles", "user").setIssuedAt(new Date())
				.setExpiration(currentTime.plusMinutes(5000).toDate())
				.signWith(SignatureAlgorithm.HS256, "secretkey").compact());

		user.setPassword(EMPTY);

		return user;
	}

	@RequestMapping(value="/register", method = RequestMethod.POST)
	public UserInfo registerUser(@RequestBody UserInfo user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userService.saveOrUpdate(user);
	}

	@RequestMapping(value="/all", method = RequestMethod.GET)
	public List<UserInfo> getAllUsers() {
		return userService.findAllUsers();
	}
}
