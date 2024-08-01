package com.matrix.sportopia.models.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RecoveryPassword {
    @NotNull(message = "token can not be null")
    private String token;

    @NotNull(message = "new password can not be null")
    @Size(min = 3, max = 30)
    @Pattern(regexp = "[A-Za-z0-9]+")
    private String newPassword;

    @NotNull(message = "retry password can not be null")
    @Size(min = 3, max = 30)
    @Pattern(regexp = "[A-Za-z0-9]+")
    private String retryPassword;
}