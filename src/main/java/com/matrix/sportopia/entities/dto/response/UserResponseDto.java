package com.matrix.sportopia.entities.dto.response;

import com.matrix.sportopia.enums.UserStatus;
import lombok.Data;

@Data
public class UserResponseDto {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String bankAccount;
    private UserStatus status;
    private String photoPath;
}
