package com.matrix.sportopia.controllers;


import com.matrix.sportopia.models.dto.request.LoginReq;
import com.matrix.sportopia.models.dto.request.RecoveryPassword;
import com.matrix.sportopia.models.dto.request.UserRequestDto;
import com.matrix.sportopia.models.dto.response.UserResponseDto;
import com.matrix.sportopia.services.impl.AuthService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @ResponseBody
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginReq loginReq){
        return authService.login(loginReq);
    }


    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto register(@ModelAttribute UserRequestDto userRequest) {
        return authService.register(userRequest);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> requestPasswordReset(@RequestParam @NotBlank @Email String email){
        return authService.requestPasswordReset(email);
    }

    @PatchMapping("/recovery-password")
    public ResponseEntity<String> resetPassword(@RequestBody @Valid RecoveryPassword recoveryPassword) {
        return authService.resetPassword(recoveryPassword);
    }
}
