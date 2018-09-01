package edu.ilyav.api.cotrollers;

import edu.ilyav.api.models.Education;
import edu.ilyav.api.models.Experience;
import edu.ilyav.api.models.Profile;
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

        printText("----------------------------------------------------------------------   SUMMARY   -----------------------------------------------------------------------", Boolean.FALSE);
        printText(EMPTY_STRING, Boolean.TRUE);
        printText(EMPTY_STRING, Boolean.TRUE);

        //Adding text in the form of string
        if (profile.getProfileContent().getSummary().getDescription() != null) {
            String[] descriptionList = profile.getProfileContent().getSummary().getDescription().split("\n");
            for (String input : descriptionList) {
                List<String> wrappedLines = wrap(input, 125);
                int cnt = 0;
                for (String line : wrappedLines) {
                    cnt++;
                    printText(line.trim(), Boolean.FALSE);
                    if (wrappedLines.size() > 1 && cnt != wrappedLines.size()) {
                        printText(EMPTY_STRING, Boolean.TRUE);
                    }
                }
                printText(EMPTY_STRING, Boolean.TRUE);
            }
        }
        printText(EMPTY_STRING, Boolean.TRUE);
        printText("---------------------------------------------------------------------   EXPERIENCE   ---------------------------------------------------------------------", Boolean.FALSE);
        printText(EMPTY_STRING, Boolean.TRUE);
        printText(EMPTY_STRING, Boolean.TRUE);

        if (!profile.getProfileContent().getExperienceList().isEmpty()) {
            boolean isExpFirst = Boolean.TRUE;
            for (Experience exp : profile.getProfileContent().getExperienceList()) {
                if(!isExpFirst) {
                    printText(EMPTY_STRING, Boolean.TRUE);
                }
                isExpFirst = Boolean.FALSE;
                printText(exp.getCompany(), Boolean.FALSE);
                printText(EMPTY_STRING, Boolean.TRUE);
                printText(exp.getTitle(), Boolean.FALSE);
                printText(EMPTY_STRING, Boolean.TRUE);
                if(!exp.getCurrentlyWorkHere()) {
                    printText(exp.getStartDate() + " - " + exp.getEndDate(), Boolean.FALSE);
                } else {
                    printText(exp.getStartDate() + " - Present", Boolean.FALSE);
                }
                printText(EMPTY_STRING, Boolean.TRUE);
                printText(exp.getLocation(), Boolean.FALSE);
                printText(EMPTY_STRING, Boolean.TRUE);
                printText("---------------------------------------------------------------", Boolean.FALSE);
                printText(EMPTY_STRING, Boolean.TRUE);
                printText(EMPTY_STRING, Boolean.TRUE);
                if (exp.getDescription() != null) {
                    String[] descriptionList = exp.getDescription().replaceAll("\t", " ").split("\n");
                    for (String input : descriptionList) {
                        List<String> wrappedLines = wrap(input, 120);
                        int cnt = 0;
                        for (String line : wrappedLines) {
                            cnt++;
                            printText(line.trim(), Boolean.FALSE);
                            if (wrappedLines.size() > 1 && cnt != wrappedLines.size()) {
                                printText(EMPTY_STRING, Boolean.TRUE);
                            }
                        }
                        printText(EMPTY_STRING, Boolean.TRUE);
                    }
                }
            }
        }

        printText("---------------------------------------------------------------------   EDUCATION   ---------------------------------------------------------------------", Boolean.FALSE);
        printText(EMPTY_STRING, Boolean.TRUE);
        printText(EMPTY_STRING, Boolean.TRUE);

        if (!profile.getProfileContent().getEducationList().isEmpty()) {
            boolean isEduFirst = Boolean.TRUE;
            for (Education edu : profile.getProfileContent().getEducationList()) {
                if(!isEduFirst) {
                    printText(EMPTY_STRING, Boolean.TRUE);
                }
                isEduFirst = Boolean.FALSE;
                printText(edu.getSchoolName(), Boolean.FALSE);
                printText(EMPTY_STRING, Boolean.TRUE);
                printText(edu.getDegreeName() + " , " + edu.getFieldOfStudy(), Boolean.FALSE);
                printText(EMPTY_STRING, Boolean.TRUE);
                if(edu.getFromYear() != edu.getToYearOrExpected()) {
                    printText(edu.getFromYear() + " - " + edu.getToYearOrExpected(), Boolean.FALSE);
                } else {
                    printText(edu.getFromYear().toString(), Boolean.FALSE);
                }
                printText(EMPTY_STRING, Boolean.TRUE);
                Optional<String> loc = Optional.ofNullable(edu.getLocation());
                if(loc.isPresent()) {
                    printText(edu.getLocation(), Boolean.FALSE);
                    printText(EMPTY_STRING, Boolean.TRUE);
                }
                printText("---------------------------------------------------------------", Boolean.FALSE);
                printText(EMPTY_STRING, Boolean.TRUE);
                printText(EMPTY_STRING, Boolean.TRUE);
                if (edu.getDescription() != null) {
                    String[] descriptionList = edu.getDescription().replaceAll("\t", " ").split("\n");
                    for (String input : descriptionList) {
                        List<String> wrappedLines = wrap(input, 120);
                        int cnt = 0;
                        for (String line : wrappedLines) {
                            cnt++;
                            printText(line.trim(), Boolean.FALSE);
                            if (wrappedLines.size() > 1 && cnt != wrappedLines.size()) {
                                printText(EMPTY_STRING, Boolean.TRUE);
                            }
                        }
                        printText(EMPTY_STRING, Boolean.TRUE);
                    }
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
        contentStream.moveTextPositionByAmount(300, 10);
        contentStream.drawString(pageNumber.toString());
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

    private void closeContent(Boolean isCloseContent) throws Exception {
        printText(EMPTY_STRING, Boolean.FALSE, isCloseContent);
    }

    private void printText(String input, Boolean isNewLine) throws Exception {
        printText(input, isNewLine, Boolean.FALSE);
    }

    private void printText(String input, Boolean isNewLine, Boolean isCloseContent) throws Exception {

        numLine++;

        if(isFirstPage || (!isNextPage && MAX_LINE_ON_THE_FIRST_PAGE  <= numLine) || (isNextPage && MAX_LINE_ON_THE_PAGE  <= numLine)) {
            generateNewPage(isCloseContent);
        }

        if(isCloseContent) {
            return;
        }

        if(isNewLine) {
            contentStream.newLine();
        } else {
            contentStream.showText(input);
        }
    }

    private void generateNewPage(Boolean isCloseContent) throws Exception {

        if(isFirstPage) {
            //Creating a blank page
            PDPage page = new PDPage();

            //Adding the blank page to the document
            document.addPage(page);

            File imgFile = new File("downloaded.jpg");

            OutputStream os = new FileOutputStream(imgFile);

            byte[] byteObjects = new byte[profile.getImageBytes().length];
            int i = 0;
            for (byte b : profile.getImageBytes()) {
                byteObjects[i++] = b;
            }

            os.write(byteObjects);

            //Creating PDImageXObject object
            PDImageXObject pdImage = PDImageXObject.createFromFileByExtension(imgFile, document);

            os.close();

            contentStream = new PDPageContentStream(document, page);

            //Drawing the image in the PDF document
            contentStream.drawImage(pdImage, 25, 625);

            contentStream.setNonStrokingColor(Color.BLACK);

            //Begin the Content stream
            contentStream.beginText();

            //Setting the font to the Content stream
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);

            contentStream.moveTextPositionByAmount(200, 730);
            contentStream.drawString(profile.getFirstName() + ' ' + profile.getLastName());
            contentStream.endText();

            //Begin the Content stream
            contentStream.beginText();

            //Setting the font to the Content stream
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);

            contentStream.moveTextPositionByAmount(200, 710);
            contentStream.drawString(profile.getOccupation());
            contentStream.endText();

            //Begin the Content stream
            contentStream.beginText();

            //Setting the font to the Content stream
            contentStream.setFont(PDType1Font.HELVETICA, 12);

            contentStream.moveTextPositionByAmount(200, 680);
            contentStream.drawString(profile.getPrimaryEmail());
            contentStream.endText();

            //Begin the Content stream
            contentStream.beginText();

            //Setting the font to the Content stream
            contentStream.setFont(PDType1Font.HELVETICA, 12);

            contentStream.moveTextPositionByAmount(200, 660);
            contentStream.drawString(profile.getLinkedInUrl());
            contentStream.endText();

//            //Begin the Content stream
//            contentStream.beginText();
//
//            //Setting the font to the Content stream
//            contentStream.setFont(PDType1Font.HELVETICA, 12);
//
//            contentStream.moveTextPositionByAmount(200, 640);
//            contentStream.drawString("Mobile: XXX-XXX-XXXX");
//            contentStream.endText();

            //Begin the Content stream
            contentStream.beginText();

            //Setting the font to the Content stream
            contentStream.setFont(PDType1Font.HELVETICA, 10);

            //Setting the leading
            contentStream.setLeading(12.5f);

            //Setting the position for the line
            contentStream.newLineAtOffset(25, 600);

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
            contentStream.moveTextPositionByAmount(300, 15);
            contentStream.drawString(pageNumber.toString());
            contentStream.endText();

            //Closing the content stream
            contentStream.close();

            if(isCloseContent) return;

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
