package com.matrix.sportopia.models.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LoginReq {

    @NotBlank(message = "Username cannot be empty or null")
    @Size(max = 50)
    @Pattern(regexp = "[A-Za-z0-9_]+$")
    private String username;

    @NotBlank(message = "Username cannot be empty or null")
    @Size(min = 3)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d_.!@#$%^&*]+$")
    private String password;
}
