package com.matrix.sportopia.models.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RecoveryPasswordDto {
    @NotNull(message = "token can't be null")
    private String token;

    @NotNull(message = "new password can not be null")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$",
            message = "Password must contain at least 1 uppercase letter, 1 lowercase letter, and 1 digit, with a minimum length of 8 characters")
    private String newPassword;

    @NotNull(message = "new password can not be null")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$",
            message = "Password must contain at least 1 uppercase letter, 1 lowercase letter, and 1 digit, with a minimum length of 8 characters")
    private String retryPassword;
}
