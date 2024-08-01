package com.matrix.sportopia.services.impl;

import com.matrix.sportopia.entities.Authority;
import com.matrix.sportopia.entities.PasswordResetToken;
import com.matrix.sportopia.entities.User;
import com.matrix.sportopia.enums.UserStatus;
import com.matrix.sportopia.exceptions.handle.AlreadyExistException;
import com.matrix.sportopia.exceptions.handle.IncorrectPasswordException;
import com.matrix.sportopia.mapper.UserMapper;
import com.matrix.sportopia.mapper.mappingUtil.UploadPathUtility;
import com.matrix.sportopia.models.dto.request.Email;
import com.matrix.sportopia.models.dto.request.LoginReq;
import com.matrix.sportopia.models.dto.request.RecoveryPassword;
import com.matrix.sportopia.models.dto.request.UserRequestDto;
import com.matrix.sportopia.models.dto.response.ExceptionDTO;
import com.matrix.sportopia.models.dto.response.LoginRes;
import com.matrix.sportopia.models.dto.response.UserResponseDto;
import com.matrix.sportopia.repositories.AuthorityRepository;
import com.matrix.sportopia.repositories.PasswordResetTokenRepository;
import com.matrix.sportopia.repositories.UserRepository;
import com.matrix.sportopia.services.EmailSenderService;
import com.matrix.sportopia.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    @Value("${app.files}")
    private String rootDir;

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;
    private final EmailSenderService emailSenderService;
    private final UserService userService;
    private final PasswordResetTokenRepository tokenRepository;

    private final UserMapper userMapper;

    public ResponseEntity<?> login(LoginReq loginReq) {
        log.info("-> authenticate method started by: {}", loginReq.getUsername());
        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getUsername(),
                            loginReq.getPassword()));
            log.info("-> authentication details: {}", authentication);
            String username = authentication.getName();
            User user = new User(username, "");
            String token = jwtUtil.createToken(user);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
            LoginRes loginRes = new LoginRes(username, token);
            log.info("-> user: {} logged in", user.getUsername());
            return ResponseEntity.status(HttpStatus.OK).headers(headers).body(loginRes);
        }catch (Exception e) {
            ExceptionDTO exceptionDTO = new ExceptionDTO(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            log.error("-> error due to {} ", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionDTO);
        }

    }

    public UserResponseDto register(UserRequestDto userRequestDto) {
        if (userRequestDto == null) {
            log.warn("-> cannot add null user");
            throw new IllegalArgumentException("User cannot be null");
        }
        log.info("-> started the insert operation!");
        User addedUser;
        UserResponseDto userResponseDto;
        try {
            User user = userMapper.toEntity(userRequestDto);
            user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
            user.setStatus(UserStatus.ACTIVE);

            Authority defaultValue = authorityRepository.findByName("USER");
            Set<Authority> authorities = Set.of(defaultValue);
            user.setAuthorities(authorities);

            if (userRequestDto.getPhoto()!=null && !userRequestDto.getPhoto().isEmpty()){
                String userName = user.getName();
                String userSurname = user.getSurname();
                Long userId = user.getId();
                String photoPath = UploadPathUtility.uploadPath(userRequestDto.getPhoto(),rootDir, userId, userName,userSurname);
                user.setPhotoPath(photoPath);
            }
            addedUser = userRepository.save(user);
            userResponseDto = userMapper.toResponse(addedUser);
            log.info("-> successfully: " + addedUser);

            sendToMail(addedUser);

        } catch (Exception e) {
            log.error("error ", e);
            throw new AlreadyExistException("Failed");
        }
        return userResponseDto;
    }

    public ResponseEntity<String> requestPasswordReset(String email){
        log.info("-> requestPasswordReset method started by: {}", email);
        User user = userService.findByEmail(email);
        if (user == null){
            log.info("-> user is null for requestPasswordReset method");
            return ResponseEntity.badRequest().body("User not found with this email");
        }

        Long userId = user.getId();
        Optional<PasswordResetToken> passwordResetToken = tokenRepository.findByUserId(userId);
        passwordResetToken.ifPresent(tokenRepository::delete);
        String newToken = generateRandomToken();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE,5);
        Date expiryDate = calendar.getTime();

        Email receiverEmail = new Email();
        receiverEmail.setReceiver(email);
        receiverEmail.setText(newToken);
        receiverEmail.setSubject("Sportopia - recovery password");
        try{
            emailSenderService.sendEmail(receiverEmail);
            createToken(user, newToken, expiryDate);
        }catch (Exception e){
            log.error("Error due to: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Email couldn't sent. Try again.");
        }
        log.info("token sent with email for recovery password to {}", email);
        return ResponseEntity.ok("Ok. Verify token was sent to your email");
    }

    public ResponseEntity<String> resetPassword(RecoveryPassword recoveryPassword) {
        log.info("-> resetPassword method started by token: {}", recoveryPassword.getToken());
        if(recoveryPassword.getNewPassword().equals(recoveryPassword.getRetryPassword())){
            PasswordResetToken passwordResetToken = tokenRepository.findByToken(recoveryPassword.getToken());
            if (!isTokenValid(passwordResetToken)) {
                log.info("-> token is not valid");
                return ResponseEntity.badRequest().body("Ops! Something went wrong!");
            }
            User user = passwordResetToken.getUser();
            userService.changePassword(user, passwordEncoder.encode(recoveryPassword.getNewPassword()));
            log.info("-> password changed for userId: {}", user.getId());
            deleteToken(passwordResetToken);
            log.info("-> password successfully reset by token: {}", recoveryPassword.getToken());
            return ResponseEntity.ok("Password reset successfully!");
        }else
            log.error("-> passwords entered do not match");
        throw new IncorrectPasswordException("Password update failed");
    }

    private void sendToMail(User addedUser) {
        Email email = new Email();
        email.setReceiver(addedUser.getEmail());
        email.setSubject("Welcome to Sportopia");
        email.setText("Dear " + addedUser.getName() + " Thank you for registering at Sportopia");
        emailSenderService.sendEmail(email);
    }

    private String generateRandomToken() {
        SecureRandom random = new SecureRandom();
        int TOKEN_LENGTH = 32;
        byte[] bytes = new byte[TOKEN_LENGTH / 2];
        random.nextBytes(bytes);
        return new BigInteger(1, bytes).toString(16);
    }

    private void createToken(User user, String token, Date expiryDate) {
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setUser(user);
        passwordResetToken.setToken(token);
        passwordResetToken.setExpiryDate(expiryDate);
        tokenRepository.save(passwordResetToken);
        log.info("Token created for forgot password function");
    }

    private boolean isTokenValid(PasswordResetToken token) {
        return token != null && !token.getExpiryDate().before(new Date());
    }


    private void deleteToken(PasswordResetToken token) {
        tokenRepository.delete(token);
    }
}
