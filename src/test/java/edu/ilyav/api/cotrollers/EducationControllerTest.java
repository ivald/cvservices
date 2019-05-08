package edu.ilyav.api.cotrollers;

import edu.ilyav.api.models.Education;
import edu.ilyav.api.models.Login;
import edu.ilyav.api.models.Role;
import edu.ilyav.api.models.UserInfo;
import edu.ilyav.api.service.EducationService;
import edu.ilyav.api.service.ProfileService;
import edu.ilyav.api.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by ivald on 2018-09-04.
 */

@RunWith(SpringRunner.class)
@WebMvcTest(EducationController.class)
@PropertySource(ignoreResourceNotFound = true, value = "classpath:application.properties")
public class EducationControllerTest {

    @Value("${secret.key}")
    protected String SECRET_KEY;

    private final String USER_NAME = "ilyav";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private ProfileService profileService;

    @MockBean
    private EducationService educationService;

    private Optional<UserInfo> user;

    @Before
    public void setUp() throws Exception {
        this.user = Optional.of(new UserInfo());

        DateTime currentTime = new DateTime();

        user.get().setToken(Jwts.builder().setSubject(USER_NAME).setIssuedAt(new Date())
                .setExpiration(currentTime.plusMinutes(30).toDate())
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes("UTF-8")).compact());

        final Claims claims = Jwts.parser().setSigningKey(SECRET_KEY.getBytes("UTF-8")).parseClaimsJws(user.get().getToken()).getBody();

        Login login = new Login();
        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setRoleName("ADMIN");
        roles.add(role);
        login.setRoles(roles);
        user.get().setLogin(login);

        when(userService.findByUserName(claims.getSubject())).thenReturn(this.user.get());
    }

    private String eduArray = "[\n" +
            "    {\n" +
            "        \"id\": 135,\n" +
            "        \"schoolName\": \"SCE - Shamoon College of Engineering, Israel\",\n" +
            "        \"degreeName\": \"Bachelor of Technology (B.Tech.)\",\n" +
            "        \"fieldOfStudy\": \"Computer Software Engineering\",\n" +
            "        \"grade\": null,\n" +
            "        \"activitiesAndSocieties\": null,\n" +
            "        \"fromYear\": 2004,\n" +
            "        \"toYearOrExpected\": 2008,\n" +
            "        \"description\": \"Between the dates: 09/2005 - 06/2008 I worked at Sami Shamoon College in the position Computer Technician.\\n\\nResponsibilities:\\n• Computer maintenance\\n• Installation operating systems\\n• Different types of software installation for studying needs of the College\\n• Networks maintenance\\n\\nFinal project - An online system for the managing and processing of bibliographical information for research purposes.\\n•\\tDevelopment tools: C#, ASP.NET, MS SQL Server 2005, JS, AJAX, XML, CSS, HTML.\\n•\\tDevelopment environment: MS Visual Studio 2005, SQL Server Management Studio\",\n" +
            "        \"imageName\": null,\n" +
            "        \"link\": \"https://en.sce.ac.il/sce/about\",\n" +
            "        \"colorTag\": \"green\",\n" +
            "        \"location\": \"Beer Sheva, Israel\",\n" +
            "        \"isProfessionalCourse\": false,\n" +
            "        \"profileId\": 2,\n" +
            "        \"profileContentId\": 11,\n" +
            "        \"imageList\": [\n" +
            "            {\n" +
            "                \"id\": 186,\n" +
            "                \"imageUrl\": \"https://res.cloudinary.com/ilyavimages/image/upload/v1524858789/uzoisjlqon0wqyvzreq6.jpg\",\n" +
            "                \"publicId\": \"uzoisjlqon0wqyvzreq6\",\n" +
            "                \"description\": \"SCE - SAMI SHMOON COLLEGE OF ENGINEERING (B.Tech)\",\n" +
            "                \"sourceUrl\": null,\n" +
            "                \"title\": null,\n" +
            "                \"experienceId\": null,\n" +
            "                \"educationId\": null\n" +
            "            }\n" +
            "        ]\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 137,\n" +
            "        \"schoolName\": \"SCALED AGILE\",\n" +
            "        \"degreeName\": \"SAFe for Teams Course (4.5)\",\n" +
            "        \"fieldOfStudy\": \"Scaled Agile Framework\",\n" +
            "        \"grade\": null,\n" +
            "        \"activitiesAndSocieties\": null,\n" +
            "        \"fromYear\": 2017,\n" +
            "        \"toYearOrExpected\": 2017,\n" +
            "        \"description\": \"SAFe is an online freely revealed knowledge base of proven, integrated patterns for implementing Lean-Agile development. It provides comprehensive guidance for work at the Portfolio, Large Solution, Program, and Team Levels.\\nSAFe 4.5 this new version maximizes the speed of product or service delivery from initial idea to release, and from customer feedback to enhancements\\n                                        \",\n" +
            "        \"imageName\": null,\n" +
            "        \"link\": \"http://www.scaledagileframework.com/about/\",\n" +
            "        \"colorTag\": \"grey\",\n" +
            "        \"location\": null,\n" +
            "        \"isProfessionalCourse\": true,\n" +
            "        \"profileId\": 2,\n" +
            "        \"profileContentId\": 11,\n" +
            "        \"imageList\": [\n" +
            "            {\n" +
            "                \"id\": 247,\n" +
            "                \"imageUrl\": \"https://res.cloudinary.com/ilyavimages/image/upload/v1527878886/gjhepg2hacbamdkw1n4i.png\",\n" +
            "                \"publicId\": \"gjhepg2hacbamdkw1n4i\",\n" +
            "                \"description\": \"SAFe\",\n" +
            "                \"sourceUrl\": null,\n" +
            "                \"title\": null,\n" +
            "                \"experienceId\": null,\n" +
            "                \"educationId\": 137\n" +
            "            }\n" +
            "        ]\n" +
            "    }]";

    public void testAdd() throws Exception {

    }

    public void testUpdate() throws Exception {

    }

    @Test
    public void testDelete() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .delete("/rest/private/education/delete/222").header("Authorization", "Bearer " + this.user.get().getToken())
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk());

        verify(educationService, times(1)).delete(anyLong());
    }

    @Test
    public void testGetAll() throws Exception {
        Education education = new Education();
        education.setId(135L);
        education.setSchoolName("SCE - Shamoon College of Engineering, Israel");

        Education education1 = new Education();
        education1.setId(136L);
        education1.setSchoolName("SCALED AGILE");

        when(educationService.findAll()).thenReturn(Arrays.asList(education, education1));

        RequestBuilder request = MockMvcRequestBuilders
                .get("/rest/private/education/all").header("Authorization", "Bearer " + this.user.get().getToken())
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("[{id:135,schoolName: \"SCE - Shamoon College of Engineering, Israel\"}, {id:136,schoolName: \"SCALED AGILE\"}]"))
                .andReturn();

    }
}