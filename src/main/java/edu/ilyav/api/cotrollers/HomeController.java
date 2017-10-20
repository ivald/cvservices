package edu.ilyav.api.cotrollers;

import edu.ilyav.api.models.Education;
import edu.ilyav.api.models.Language;
import edu.ilyav.api.models.Profile;
import edu.ilyav.api.models.Summary;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilyav on 17/08/17.
 */
@RestController
@RequestMapping("/rest")
public class HomeController {

    @RequestMapping("/public")
    public Profile home() {

        Profile profileObj = new Profile();
        profileObj.setFirstName("Ilya");
        profileObj.setLastName("Valdman");
        profileObj.setOccupation("Sr. Software Engineer");
        profileObj.setPrimaryEmail("valdman.ilya@gmail.com");
        profileObj.setLinkedInUrl("ca.linkedin.com/in/valdmanilya");

        List<Education> educationList = new ArrayList<>();
        Education education = new Education();
        education.setSchoolName("SCE - Shamoon College of Engineering, Israel");
        education.setDegreeName("Bachelor of Technology (B.Tech.)");
        education.setFromYear(2004L);
        education.setToYearOrExpected(2008L);
        education.setIsProfessionalCourse(Boolean.FALSE);
        educationList.add(education);

        education = new Education();
        education.setSchoolName("TCB - Technological College of Beer-Sheva, Israel");
        education.setDegreeName("Practical Engineer");
        education.setFromYear(1998L);
        education.setToYearOrExpected(2000L);
        education.setIsProfessionalCourse(Boolean.FALSE);
        educationList.add(education);

        education = new Education();
        education.setSchoolName("SCALED AGILE");
        education.setDegreeName("SAFe for Teams Course (4.5)");
        education.setFromYear(2017L);
        education.setToYearOrExpected(2017L);
        education.setIsProfessionalCourse(Boolean.TRUE);
        educationList.add(education);

        education = new Education();
        education.setSchoolName("John Bryce, Israel");
        education.setDegreeName("Course Asp.Net Using Visual C# 2010");
        education.setFromYear(2012L);
        education.setToYearOrExpected(2012L);
        education.setIsProfessionalCourse(Boolean.TRUE);
        educationList.add(education);

        education = new Education();
        education.setSchoolName("John Bryce, Israel");
        education.setDegreeName("Re-qualification to JAVA and J2EE");
        education.setFromYear(2011L);
        education.setToYearOrExpected(2012L);
        education.setIsProfessionalCourse(Boolean.TRUE);
        educationList.add(education);

        profileObj.setEducationList(educationList);

        List<Language> languageList = new ArrayList<>();
        Language language = new Language();
        language.setLanguageName("Russian");
        language.setLanguageDescription("Native");
        languageList.add(language);

        language = new Language();
        language.setLanguageName("English");
        language.setLanguageDescription("Professional");
        languageList.add(language);

        language = new Language();
        language.setLanguageName("Hebrew");
        language.setLanguageDescription("Fluently");
        languageList.add(language);

        profileObj.setLanguageList(languageList);

        Summary summary = new Summary();
        summary.setFirstDescription("I am Java and JEE Developer with over 7 years solid experience of Information Technology. Developed a complex information system using Java Enterprise Edition. Strong analytical, problem solving, troubleshooting and debugging skills.");
        summary.setSecondDescription("I am detailed oriented person, able to learn new developing technologies quickly and efficiently. In addition to this I am constantly seeking to improve my skills and fully aware of the latest developing technologies. I have also honed good interpersonal communication skills by collaborating with teams as well as direct and indirect managers. On a personal level I have an ability to stay organized and on top of my work at all times. ");
        List<String> attributeList = new ArrayList<>();
        attributeList.add("Programming language: Java");
        attributeList.add("JEE services: EJB 3, JDBC, JSP, Servlets, JNDI, RMI, JPA, Hibernate, JSF, JAXB");
        attributeList.add("Client side technologies: JS, jQuery, Ajax, HTML, CSS, Primefaces, Richfaces, Myfaces");
        attributeList.add("Markup languages: XML/XSD/XSLT");
        attributeList.add("Web Services: SOAP Web Services, REST");
        attributeList.add("Reporting: Jasper iReport, iText");
        attributeList.add("Application servers: Weblogic, JBoss, GlassFish, Apache Tomcat");
        attributeList.add("Query languages: SQL, PL/SQL");
        attributeList.add("Database: DB2, Oracle, MS-SQL and MySql");
        attributeList.add("Version Control: Subversion, GitHub, GitLab");
        attributeList.add("Development tools/environments: IntelliJ IDEA, Eclipse, SQLDeveloper, J/PL/SQL Developer");
        attributeList.add("Operating systems: Windows, Linux");

        summary.setAttributesList(attributeList);

        profileObj.setSummary(summary);
        return profileObj;
    }
}