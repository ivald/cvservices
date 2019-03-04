package edu.ilyav.api.cotrollers;

import edu.ilyav.api.models.Education;
import edu.ilyav.api.models.Experience;
import edu.ilyav.api.models.Profile;
import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/public/download")
public class PdfController {

    private static final Short MAX_LINE_ON_THE_FIRST_PAGE = 80;
    private static final Short MAX_LINE_ON_THE_PAGE = 101;
    private static final String EMPTY_STRING = "";

    //Creating PDF document object
    private PDDocument document;

    private Boolean isFirstPage;
    private Boolean isNextPage;
    private Profile profile;
    private PDPageContentStream contentStream;
    private short numLine = 0;
    private Short pageNumber = 0;

    @Autowired
    private HomeController homeController;

    @RequestMapping(value = "/pdf/{userName}", method = RequestMethod.GET)
    public void generatePdf(HttpServletResponse response,
                            @PathVariable("userName") String userName) throws Exception {

        //Creating PDF document object
        document = new PDDocument();
        isFirstPage = Boolean.TRUE;
        isNextPage = Boolean.FALSE;
        numLine = 0;
        pageNumber = 0;

        profile = homeController.getProfile(userName);

        printText(EMPTY_STRING, Boolean.TRUE);
        printText(EMPTY_STRING, Boolean.TRUE);
        printTitle(Color.DARK_GRAY, "                                                                                     " +
                "SUMMARY                                                                          ");
        printText(EMPTY_STRING, Boolean.TRUE);
        printText(EMPTY_STRING, Boolean.TRUE);

        //Adding text in the form of string
        if (profile.getProfileContent().getSummary().getDescription() != null) {
            String[] descriptionList = profile.getProfileContent().getSummary().getDescription().split("\n");
            for (String input : descriptionList) {
                List<String> wrappedLines = wrap(input, 125);
                printWrappedLines(wrappedLines);
                printText(EMPTY_STRING, Boolean.TRUE);
            }
        }
        printText(EMPTY_STRING, Boolean.TRUE);
        printTitle(Color.DARK_GRAY, "                                                                                  " +
                "EXPERIENCE                                                                        ");
        printText(EMPTY_STRING, Boolean.TRUE);
        printText(EMPTY_STRING, Boolean.TRUE);

        if (!profile.getProfileContent().getExperienceList().isEmpty()) {
            boolean isExpFirst = Boolean.TRUE;
            for (Experience exp : profile.getProfileContent().getExperienceList()) {
                if (!isExpFirst) {
                    printText(EMPTY_STRING, Boolean.TRUE);
                }
                isExpFirst = Boolean.FALSE;
                if (!exp.getCurrentlyWorkHere()) {
                    printTitle(Color.BLUE, exp.getCompany() + " ,  " + exp.getLocation() + "                                                                                                           " + exp.getStartDate() + " - " + exp.getEndDate());
                } else {
                    printTitle(Color.BLUE, exp.getCompany() + " ,  " + exp.getLocation() + "                                                                                                                " + exp.getStartDate() + " - Present");
                }
                printText(EMPTY_STRING, Boolean.TRUE);
                printTitle(Color.BLUE, exp.getTitle());
                printText(EMPTY_STRING, Boolean.TRUE);
                printText(EMPTY_STRING, Boolean.TRUE);
                if (exp.getDescription() != null) {
                    printDescription(exp.getDescription());
                }
                printText(EMPTY_STRING, Boolean.TRUE);
            }
        }

        printText(EMPTY_STRING, Boolean.TRUE);
        printTitle(Color.DARK_GRAY, "                                                                                  " +
                "EDUCATION                                                                        ");
        printText(EMPTY_STRING, Boolean.TRUE);
        printText(EMPTY_STRING, Boolean.TRUE);

        if (!profile.getProfileContent().getEducationList().isEmpty()) {
            boolean isEduFirst = Boolean.TRUE;
            for (Education edu : profile.getProfileContent().getEducationList()) {
                if (!isEduFirst) {
                    printText(EMPTY_STRING, Boolean.TRUE);
                }
                isEduFirst = Boolean.FALSE;
                Optional<String> loc = Optional.ofNullable(edu.getLocation());
                int spaces = 161;
                if(edu.getFromYear().equals(2004L) )
                    spaces = 148;
                else if(edu.getFromYear().equals(2017L) )
                    spaces = 164;

                String nameSpaces = "";
                if (loc.isPresent()) {
                    nameSpaces = " ,   " + edu.getLocation();
                }

                String str1 = edu.getSchoolName() + nameSpaces;
                String str2 = edu.getFromYear() + " - " + edu.getToYearOrExpected();
                StringBuilder str3 = new StringBuilder();
                int count = 0;
                count += str1.length() + str2.length();
                for (int i = 0; i < (spaces - count); i++)
                    str3.append(" ");
                printTitle(Color.BLUE, str1 + str3.toString() + str2);

                printText(EMPTY_STRING, Boolean.TRUE);
                printTitle(Color.BLUE, edu.getDegreeName() + " , " + edu.getFieldOfStudy());
                printText(EMPTY_STRING, Boolean.TRUE);
                printText(EMPTY_STRING, Boolean.TRUE);
                if (edu.getDescription() != null) {
                    printDescription(edu.getDescription());
                }
            }
        }

        pageNumber++;

        closeContent(Boolean.TRUE);

        //Ending the content stream
        contentStream.endText();

        //Setting the non stroking color
        contentStream.setNonStrokingColor(Color.LIGHT_GRAY);

        //Drawing a rectangle
        contentStream.addRect(25, 30, 565, 0.5f);

        //Drawing a rectangle
        contentStream.fill();

        //Drawing a rectangle
        contentStream.setNonStrokingColor(Color.WHITE); //gray background
        contentStream.addRect(25, 30, 565, 50);

        //Drawing a text
        contentStream.setNonStrokingColor(Color.GRAY);
        contentStream.beginText();
        contentStream.moveTextPositionByAmount(285, 10);
        contentStream.drawString("Page " + pageNumber.toString());
        contentStream.endText();

        contentStream.close();

        //Saving the document
        final File file = File.createTempFile("profile", ".pdf");
        document.save(file);

        System.out.println("PDF created");

        //Closing the document
        document.close();

        //Here we have mentioned it to show as attachment
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));

        response.setContentLength((int) file.length());

        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }

    private void printDescription(String description) throws Exception {
        String[] descriptionList = description.replaceAll("\t", " ").split("\n");
        for (String input : descriptionList) {
            List<String> wrappedLines = wrap(input, 120);
            printWrappedLines(wrappedLines);
            printText(EMPTY_STRING, Boolean.TRUE);
        }
    }

    private void printWrappedLines(List<String> wrappedLines) throws Exception {
        int cnt = 0;
        for (String line : wrappedLines) {
            cnt++;
            printText(line.trim(), Boolean.FALSE);
            if (wrappedLines.size() > 1 && cnt != wrappedLines.size()) {
                printText(EMPTY_STRING, Boolean.TRUE);
            }
        }
    }

    private void printTitle(Color color, String title) throws Exception {
        numLine++;

        if (isFirstPage) {
            generateNewPage(Boolean.FALSE);
        }

        contentStream.setNonStrokingColor(color);
        contentStream.showText(title);
        contentStream.setNonStrokingColor(Color.BLACK);
    }

    private void closeContent(Boolean isCloseContent) throws Exception {
        printText(EMPTY_STRING, Boolean.FALSE, isCloseContent);
    }

    private void printText(String input, Boolean isNewLine) throws Exception {
        printText(input, isNewLine, Boolean.FALSE);
    }

    private void printText(String input, Boolean isNewLine, Boolean isCloseContent) throws Exception {

        numLine++;

        if (isFirstPage || (!isNextPage && MAX_LINE_ON_THE_FIRST_PAGE <= numLine) || (isNextPage && MAX_LINE_ON_THE_PAGE <= numLine)) {
            generateNewPage(isCloseContent);
        }

        if (isCloseContent) {
            return;
        }

        if (isNewLine) {
            contentStream.newLine();
        } else {
            contentStream.showText(input);
        }
    }

    private void generateNewPage(Boolean isCloseContent) throws Exception {

        if (isFirstPage) {
            //Creating a blank page
            PDPage page = new PDPage();

            //Adding the blank page to the document
            document.addPage(page);

            contentStream = new PDPageContentStream(document, page);

            //Drawing the image in the PDF document
            //contentStream.drawImage(pdImage, 25, 625);

            contentStream.setNonStrokingColor(Color.BLACK);

            //Begin the Content stream
            contentStream.beginText();

            //Setting the font to the Content stream
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);

            short leftStartPosition = 30;
            short rightStartPosition = 400;

            contentStream.moveTextPositionByAmount(leftStartPosition, 730);
            contentStream.drawString(profile.getFirstName() + ' ' + profile.getLastName());
            contentStream.endText();

            //Begin the Content stream
            contentStream.beginText();

            //Setting the font to the Content stream
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);

            contentStream.moveTextPositionByAmount(leftStartPosition, 710);
            contentStream.drawString(profile.getOccupation());
            contentStream.endText();

            //Begin the Content stream
            contentStream.beginText();

            //Setting the font to the Content stream
            contentStream.setFont(PDType1Font.HELVETICA, 12);

            contentStream.moveTextPositionByAmount(rightStartPosition, 730);
            contentStream.drawString(profile.getPrimaryEmail());
            contentStream.endText();

            //Begin the Content stream
            contentStream.beginText();

            //Setting the font to the Content stream
            contentStream.setFont(PDType1Font.HELVETICA, 12);

            contentStream.moveTextPositionByAmount(rightStartPosition, 710);
            contentStream.drawString(profile.getLinkedInUrl());
            contentStream.endText();

            //Begin the Content stream
            contentStream.beginText();

            //Setting the font to the Content stream
            contentStream.setFont(PDType1Font.HELVETICA, 12);

            contentStream.moveTextPositionByAmount(rightStartPosition, 690);
            contentStream.drawString(this.profile.getMobile());
            contentStream.endText();

            //Begin the Content stream
            contentStream.beginText();

            //Setting the font to the Content stream
            contentStream.setFont(PDType1Font.HELVETICA, 12);

            contentStream.moveTextPositionByAmount(rightStartPosition, 670);
            contentStream.drawString(this.profile.getGithub());
            contentStream.endText();

            //Begin the Content stream
            contentStream.beginText();

            //Setting the font to the Content stream
            contentStream.setFont(PDType1Font.HELVETICA, 12);

            contentStream.moveTextPositionByAmount(rightStartPosition, 650);
            contentStream.drawString("www.ivald.net");
            contentStream.endText();

            //Begin the Content stream
            contentStream.beginText();

            //Setting the font to the Content stream
            contentStream.setFont(PDType1Font.HELVETICA, 10);

            //Setting the leading
            contentStream.setLeading(12.5f);

            //Setting the position for the line
            contentStream.newLineAtOffset(25, 620);

            isFirstPage = Boolean.FALSE;

        } else {

            pageNumber++;

            isNextPage = Boolean.TRUE;

            //Ending the content stream
            contentStream.endText();

            //Setting the non stroking color
            contentStream.setNonStrokingColor(Color.LIGHT_GRAY);

            //Drawing a rectangle
            contentStream.addRect(25, 30, 565, 0.5f);

            //Drawing a rectangle
            contentStream.fill();

            //Drawing a rectangle
            contentStream.setNonStrokingColor(Color.WHITE);
            contentStream.addRect(25, 30, 565, 50);

            //Drawing a text
            contentStream.setNonStrokingColor(Color.GRAY);
            contentStream.beginText();
            contentStream.moveTextPositionByAmount(285, 15);
            contentStream.drawString("Page " + pageNumber.toString());
            contentStream.endText();

            //Closing the content stream
            contentStream.close();

            if (isCloseContent) return;

            numLine = 0;

            //Creating a blank page
            PDPage page = new PDPage();

            //Adding the blank page to the document
            document.addPage(page);

            contentStream = new PDPageContentStream(document, page);

            //Begin the Content stream
            contentStream.beginText();

            //Setting the font to the Content stream
            contentStream.setFont(PDType1Font.HELVETICA, 10);

            //Setting the leading
            contentStream.setLeading(12.5f);

            //Setting the position for the line
            contentStream.newLineAtOffset(25, 770);
        }

    }

    private static List<String> wrap(String input, int maxLength) {
        String[] words = input.split(" ");
        List<String> lines = new ArrayList<>();

        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            if (sb.length() == 0) {
                // Note: Will not work if a *single* word already exceeds maxLength
                sb.append(word);
            } else if (sb.length() + word.length() < maxLength) {
                // Use < maxLength as we add +1 space.
                sb.append(" " + word);
            } else {
                // Line is full
                lines.add(sb.toString());
                // Restart
                sb = new StringBuilder(word);
            }
        }
        // Add the last line
        if (sb.length() > 0) {
            lines.add(sb.toString());
        }

        return lines;
    }
}
