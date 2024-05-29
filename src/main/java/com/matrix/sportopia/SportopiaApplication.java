package com.matrix.sportopia;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class SportopiaApplication implements CommandLineRunner {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    public static void main(String[] args) {
        SpringApplication.run(SportopiaApplication.class, args);
    }

    @Override
    public void run(String... args) {
        sendEmailWithHtml();
    }

    public void sendEmail() {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("cbrylv2001@inbox.ru");
        msg.setFrom("ezmemmedcebrayilov17@gmail.com");
        msg.setSubject("Spring email");

        msg.setText("to test sending mail");
        javaMailSender.send(msg);

        log.info("-> email successfully send");
    }

    public void sendEmailWithFile() throws MessagingException {
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);

        helper.setTo("miraliyevr@gmail.com");
        helper.setFrom("ezmemmedcebrayilov17@gmail.com");
        helper.setSubject("Spring email");
        helper.setText("to test sending mail with attachment");

        helper.addAttachment("For Əzi"
                , new File("src/main/java/com/matrix/sportopia/text.txt"));
        javaMailSender.send(msg);
        log.info("-> email successfully send with file");
    }

    public void sendEmailWithHtml() {
        Context context = new Context();
        context.setVariable("message", "It is test html template");
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        try {
            helper.setTo("school.master@inbox.ru");
            helper.setFrom("ezmemmedcebrayilov17@gmail.com");
            helper.setSubject("Spring email");
            String htmlContent = templateEngine.process("email-template", context);
            helper.setText(htmlContent, true);
            javaMailSender.send(mimeMessage);
            log.info("-> email successfully send with template");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
