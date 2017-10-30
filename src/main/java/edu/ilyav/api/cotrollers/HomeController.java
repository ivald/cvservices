package edu.ilyav.api.cotrollers;

import edu.ilyav.api.models.*;
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

    private Profile profile;
    private ProfileContent profileContent;

    public HomeController() {
        init();
    }

    @RequestMapping("/public/sideBar")
    public Profile sideBar() {
        return profile;
    }

    @RequestMapping("/public/home")
    public ProfileContent home() {
        return profileContent;
    }

    private void init() {
        profile = new Profile();
        profile.setFirstName("Ilya");
        profile.setLastName("Valdman");
        profile.setOccupation("Sr. Software Engineer");
        profile.setPrimaryEmail("valdman.ilya@gmail.com");
        profile.setLinkedInUrl("ca.linkedin.com/in/valdmanilya");

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

        profile.setEducationList(educationList);

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

        profile.setLanguageList(languageList);

        profileContent = new ProfileContent();
        Summary summary = new Summary();
        summary.setFirstDescription("I am Java and JEE Developer with over 7 years solid experience of Information Technology. Developed a complex information system using Java Enterprise Edition. Strong analytical, problem solving, troubleshooting and debugging skills.");
        summary.setSecondDescription("I am detailed oriented person, able to learn new developing technologies quickly and efficiently. In addition to this I am constantly seeking to improve my skills and fully aware of the latest developing technologies. \n I have also honed good interpersonal communication skills by collaborating with teams as well as direct and indirect managers. On a personal level I have an ability to stay organized and on top of my work at all times. ");
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
        profileContent.setSummary(summary);

        List<Experience> experienceList = new ArrayList<>();
        Experience experience = new Experience();
        experience.setTitle("Sr. Java Developer");
        experience.setCompany("ADESA");
        experience.setFromYear(2015L);
        experience.setFromMonth("Dec");
        experience.setCurrentlyWorkHere(Boolean.TRUE);
        experience.setLink("https://www.adesa.ca/about-adesa");
        experience.setColorTag("teal");

        experienceList.add(experience);

        experience = new Experience();
        experience.setTitle("Java Web Developer");
        experience.setCompany("Oltis Systems");
        experience.setFromYear(2015L);
        experience.setFromMonth("May");
        experience.setToYearOrExpected(2015L);
        experience.setToMonth("Oct");
        experience.setDescription("Oltis specializes in Project Management, Business Analysis, Enterprise Architecture and Software Development services. \n" +
                "\n" +
                "I took part in developing a system for Doctors Manitoba (Canadian Medical Association). This project was to build a User Interface for a client custom application. User interface is built using JSF (Primefaces Framework) with web services used as data source. This custom application consists of multiple modules - Security, Administration, Processing and main User data management module consisting of HR and User Benefits type components. \n" +
                "\n" +
                "During my work I as part of the team has developed UI and Java Beans for all modules, including development of custom reusable composite components. The work involved processing data send and received via Web Services, building back end code to be reused by both Web Services and UI side beans, developing internationalization features, defining and building reusable modules. Work also involved reviewing code developed by other developers and assisting other DEV where needed. \n" +
                "\n" +
                "Responsibilities: • Developed the enterprise project software solution based on provided business requirements. \n" +
                "• Developed client side by Java Server Faces 2 using Primefaces Framework 5. \n" +
                "• Developed a set of reusable JSF components. \n" +
                "• Performed technical analysis and impact assessment for change requests. \n" +
                "• Maintained and rearchitecting and refactoring web based client server Java application. \n" +
                "• Performed debugging, troubleshooting and resolving software issues found during internal testing. \n" +
                "• Worked with GitLab tool for tasks development and bug tracking throughout our entire software development. \n" +
                "\n" +
                "Technologies:\tJAVA/JEE, JSF 2, Web Services (REST), XML, XSD, JAXB, HTML 5, CSS 3, Java Script, jQuery, Ajax, Sql, Git, MySQL, Apache Tomcat 8. \n" +
                "\n" +
                "Software development process: \n" +
                "• Used application servers Apache Tomcat/JBoss \n" +
                "• Used during developing Git version control tool \n" +
                "• Used development environment IntelliJ IDEA, Eclipse \n" +
                "• Used OS Linux ");
        experience.setCurrentlyWorkHere(Boolean.FALSE);
        experience.setLink("http://www.oltis.ca/about-us.html");
        experience.setColorTag("grey");
        experienceList.add(experience);

        experience = new Experience();
        experience.setTitle("Java and JEE Developer");
        experience.setCompany("Sapiens (IDIT)");
        experience.setFromYear(2013L);
        experience.setFromMonth("Jul");
        experience.setToYearOrExpected(2015L);
        experience.setToMonth("Mar");
        experience.setDescription("Sapiens P&C (IDIT) is a component based software solution, addressing the specific needs of general insurance carriers for traditional insurance, direct insurance, bancassurance and brokers markets. \n" +
                "I collaborated with Euler Hermes, Partner Re and MLOZ insurance companies in developing an end to end implementation of customer projects, including UI, SQL data model and business logic implementation. During these projects, I had been involved in customizing and development code of different modules such as Contact, Policy, Claim, Billing, Accounting, Batch Jobs. \n" +
                "\n" +
                "End to end implementation of customer projects \n" +
                "• Created UI, Data model and business logic implementation \n" +
                "• Developed server side by using EJB3, JPA, Hibernate, JDBC, Servlets, JSP \n" +
                "• Developed client side by using JS, Ajax, jQuery \n" +
                "• Prepared and developed web services with external systems by using REST, SOAP,XSLT \n" +
                "• Prepared variety of SQL queries for different data bases like Oracle, MS SQL, DB2 \n" +
                "• Handled migration data from different external systems (legacy) to native objects format of app \n" +
                "\n" +
                "Document design, functional, and technical specifications \n" +
                "• Prepared and designed documents based on SRS documents issued by business analysts, ensuring that all of the initial requirements were met \n" +
                "• Prepared unit tests and worked closely with QA department on test cases refinement to provide quality products and fine-tune the details \n" +
                "\n" +
                "Debugging and troubleshooting \n" +
                "• Performed debugging, troubleshooting and resolving software issues found during internal testing, allocating time for changes to be made into existing solution \n" +
                "• Used JIRA tool for tasks development and bug tracking throughout our entire software development \n" +
                "\n" +
                "Software development process \n" +
                "• Performed application deployment to different application servers such as Weblogic/JBoss \n" +
                "• Used Tortoise SVN version control tool and MAVEN to manage a project’s build \n" +
                "• Used development environment: IntelliJ IDEA, SQL Developer, SQL Editors, SoapUI ");
        experience.setCurrentlyWorkHere(Boolean.FALSE);
        experience.setLink("http://www.sapiens.com/about-sapiens/corporate-profile/");
        experience.setColorTag("blue");
        experienceList.add(experience);

        experience = new Experience();
        experience.setTitle("Java Developer");
        experience.setCompany("Discount Bank");
        experience.setFromYear(2012L);
        experience.setFromMonth("Apr");
        experience.setToYearOrExpected(2013L);
        experience.setToMonth("Jul");
        experience.setDescription("Israel Discount Bank is the third largest bank and a leading financial group in Israel. Discount Bank provides a full spectrum of corporate and retail financial products and services to its clients. \n" +
                "\n" +
                "I participated in development of an overdraft system for big companies. It is N-tier distributed components system, developed in Java and J2EE technologies. The application uses the information required to process from a main system which runs in IBM mainframe. At the end of the process all data is synchronized with main systems by batch jobs. \n" +
                "\n" +
                "Implementation of customer projects: \n" +
                "• Created UI, Data model and business logic implementation \n" +
                "• Developed server side by using Servlets, JSF 1.2, JDBC, Toplink \n" +
                "• Developed client side by using JSP, JS, Ajax, jQuery, Richfaces, Myfaces \n" +
                "• Created reports and forms automatically by using Jasper iReport, iText \n" +
                "• Prepared variety of SQL queries and stored procedures for Oracle db \n" +
                "\n" +
                "Debugging and troubleshooting: \n" +
                "• Prepared unit tests and performed debugging, troubleshooting and resolving software issues found during internal testing \n" +
                "\n" +
                "Software development process: \n" +
                "• Performed application deployment to application server OC4J \n" +
                "• Used development environment such as JDeveloper 10.x, PL/SQL Developer");
        experience.setCurrentlyWorkHere(Boolean.FALSE);
        experience.setLink("https://www.discountbank.co.il/DB/en");
        experience.setColorTag("green");
        experienceList.add(experience);

        experience = new Experience();
        experience.setTitle("Software Developer");
        experience.setCompany("Sapiens");
        experience.setFromYear(2008L);
        experience.setFromMonth("Jun");
        experience.setToYearOrExpected(2012L);
        experience.setToMonth("Mar");
        experience.setDescription("Sapiens Life and Pensions Solution. The Sapiens solution (eMerge) was developed as a rule-based platform for all pension products. eMerge tools enable rapid prototyping and iterative development of rich Internet applications. \n" +
                "\n" +
                "I worked in Life and Pension division in collaboration with Menora Mivtachim. Worked with a complex information system that was developed using Sapiens internal tools. \n" +
                "\n" +
                "Implementation of customer project: \n" +
                "\n" +
                "• Developed a complex information system \n" +
                "• Developed server side by using eMerge, Java \n" +
                "• Developed client side by using an internal web technology and JS \n" +
                "• Developed complex and robust SQL queries for DB2 db \n" +
                "• Maintained and developed interfaces with external systems (web services) \n" +
                "\n" +
                "Debugging and troubleshooting: \n" +
                "• Prepared unit tests preparation and close work with QA department on test cases refinement \n" +
                "• Performed debugging, troubleshooting and resolving software issues found during internal testing \n" +
                "\n" +
                "Software development process: \n" +
                "• Provided technical guidance for client needs, to allow them quick understanding of the different modules of the project \n" +
                "• Used development environment such as Eclipse, eMerge Development Workbench, SQL Developer. ");
        experience.setCurrentlyWorkHere(Boolean.FALSE);
        experience.setLink("http://www.sapiens.com/about-sapiens/corporate-profile/");
        experience.setColorTag("blue");
        experienceList.add(experience);

        profileContent.setExperienceList(experienceList);
    }

}