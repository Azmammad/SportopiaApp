package com.matrix.sportopia.models.dto.request;

import com.matrix.sportopia.enums.UserStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class UserRequestDto {
    private String name;
    private String surname;
    private String username;
    private String email;
    private String phoneNumber;

    @NotNull
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$",
            message = "Password must contain at least 1 uppercase letter, 1 lowercase letter, and 1 digit, with a minimum length of 8 characters")
    private String password;

    @Size(min = 16, max = 16)
    private String bankAccount;
    private MultipartFile photo;
}