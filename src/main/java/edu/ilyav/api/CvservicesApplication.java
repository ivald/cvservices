package edu.ilyav.api;

import edu.ilyav.api.config.JwtFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CvservicesApplication {

	@Bean
	public FilterRegistrationBean jwtFilter() {
		final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(new JwtFilter());
		registrationBean.addUrlPatterns("/rest/private/*");
		registrationBean.addUrlPatterns("/user/private/*");
		return registrationBean;
	}

	public static void main(String[] args) {
		SpringApplication.run(CvservicesApplication.class, args);
	}
}
