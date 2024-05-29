package com.matrix.sportopia.entities.dto.request;

import lombok.Data;

@Data
public class Email {
    private String receiver;
    private String subject;
    private String text;
    private String fileName;
    private String attachmentPath;
}