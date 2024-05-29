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
    }
}
