package com.matrix.sportopia.services;


import com.matrix.sportopia.entities.dto.request.Email;

public interface EmailSenderService {
        void sendEmail(Email email);
}
