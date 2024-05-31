package com.matrix.sportopia.entities.dto.request;

import lombok.Data;

@Data
public class UpdatePasswordReqDto {
    private String currentPassword;
    private String newPassword;
    private String againNewPassword;
}
