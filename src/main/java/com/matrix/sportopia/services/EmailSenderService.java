package com.matrix.sportopia.services;


import com.matrix.sportopia.models.dto.request.Email;

public interface EmailSenderService {
        void sendEmail(Email email);
}
