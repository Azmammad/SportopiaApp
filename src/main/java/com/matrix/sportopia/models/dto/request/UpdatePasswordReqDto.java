package com.matrix.sportopia.models.dto.request;

import lombok.Data;

@Data
public class UpdatePasswordReqDto {
    private String currentPassword;
    private String newPassword;
    private String againNewPassword;
}
