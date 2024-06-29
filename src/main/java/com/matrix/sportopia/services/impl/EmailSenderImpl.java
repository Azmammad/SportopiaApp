package com.matrix.sportopia.services.impl;

import com.matrix.sportopia.models.dto.request.Email;
import com.matrix.sportopia.services.EmailSenderService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Data
public class EmailSenderImpl implements EmailSenderService {
    private final JavaMailSender javaMailSender;

    @Override
    public void sendEmail(Email email) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email.getReceiver());
        String myEmail = "ezmemmedcebrayilov17@gmail.com";
        msg.setFrom(myEmail);
        msg.setSubject(email.getSubject());
        msg.setText(email.getText());
        javaMailSender.send(msg);
        log.info("-> successfully send email to {}", email.getReceiver());
    }
}
