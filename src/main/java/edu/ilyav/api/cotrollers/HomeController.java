package edu.ilyav.api.cotrollers;

import edu.ilyav.api.Models.Education;
import edu.ilyav.api.Models.Language;
import edu.ilyav.api.Models.Profile;
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

        return profileObj;
    }
}