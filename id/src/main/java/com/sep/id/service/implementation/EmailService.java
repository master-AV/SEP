package com.sep.id.service.implementation;

import com.sep.id.exception.MailCannotBeSentException;
import jakarta.mail.MessagingException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStoreException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateParsingException;

import static com.sep.id.utils.Constants.*;

@Service
public class EmailService {

    private final Environment env;

    public EmailService(final Environment env) {
        this.env = env;
    }

    @Async
    public void sendVerificationMail(int verificationCode, String verificationUrl)
            throws MailException, IOException, MailCannotBeSentException {
        String pathToHTMLFile = TEMPLATE_FILE_PATH + "sendVerificationMailTemplate.html";
        String emailTemplate_PartOne = setEmailTemplate(pathToHTMLFile, "_verificationUrl_", verificationUrl);
        String emailTemplate = splitAndConcatenate(emailTemplate_PartOne, "_verificationCode_", Integer.toString(verificationCode));
        ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Mail.xml");
        HTMLEmailService mm = (HTMLEmailService) context.getBean("htmlMail");
        mm.sendMail(EMAIL, EMAIL, SUBJECT_VERIFY_USER, emailTemplate);
    }

    @Async
    public void sendPinCodeEmail(String pinCode)
            throws MailException, IOException, MailCannotBeSentException {
        String pathToHTMLFile = TEMPLATE_FILE_PATH + "sendPinEmailTemplate.html";
        String emailTemplate = setEmailTemplate(pathToHTMLFile, "_pinCode_", pinCode);
        ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Mail.xml");
        HTMLEmailService mm = (HTMLEmailService) context.getBean("htmlMail");
        mm.sendMail(EMAIL, EMAIL, SUBJECT_PIN, emailTemplate);
    }

    private String readHtmlFile(String filePath) throws IOException {

        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    private String setEmailTemplate(String pathToHTMLFile, String regexForSplit, String parameter) throws IOException {
        String readHtmlString = readHtmlFile(pathToHTMLFile);

        return splitAndConcatenate(readHtmlString, regexForSplit, parameter);
    }


    private String splitAndConcatenate(String stringForSplit, String regexForSplit, String parameterForConcatenate) {
        String[] splittedString = stringForSplit.split(regexForSplit);

        return splittedString[0] + parameterForConcatenate + splittedString[1];
    }

}
