package edu.ilyav.api.config;

import com.google.common.base.Predicate;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

@Profile("local")
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket postsApi() {
        return new Docket(DocumentationType.SPRING_WEB).groupName("public-api")
                .apiInfo(apiInfo()).select().paths(postPaths()).build();
    }

    private Predicate<String> postPaths() {
        return or(regex("/user.*"), regex("/rest/public.*"), regex("/rest/private.*"));
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "CVServices API",
                "CVServices API reference for developers.",
                "1.0",
                "http://cvservices.herokuapp.com",
                new Contact("Ilya Valdman", "http://cvservices.herokuapp.com", "valdman.ilya@gmail.com"),
                "License of API", "API license URL", new ArrayList<>());
    }

    @Bean
    SecurityConfiguration security() {
        return new SecurityConfiguration(null, null, null, null, "Bearer " +
                Jwts.builder().setSubject("guest").claim("roles", "user").setIssuedAt(new Date())
                        .setExpiration(new DateTime().plusMinutes(5000).toDate())
                        .signWith(SignatureAlgorithm.HS256, "secretkey").compact(),
                ApiKeyVehicle.HEADER, "Authorization", ",");
    }

}
