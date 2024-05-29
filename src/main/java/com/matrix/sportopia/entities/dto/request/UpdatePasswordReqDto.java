package com.matrix.sportopia.entities.dto.request;

import lombok.Data;

@Data
public class UpdatePasswordReqDto {
    private String email;
    private String currentPassword;
    private String newPassword;
}
