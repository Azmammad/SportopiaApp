package com.matrix.sportopia.services.impl;

import com.matrix.sportopia.entities.Authority;
import com.matrix.sportopia.entities.User;
import com.matrix.sportopia.exceptions.handle.AlreadyExistException;
import com.matrix.sportopia.mapper.UserMapper;
import com.matrix.sportopia.mapper.mappingUtil.UploadPathUtility;
import com.matrix.sportopia.models.dto.request.Email;
import com.matrix.sportopia.models.dto.request.LoginReq;
import com.matrix.sportopia.models.dto.request.UserRequestDto;
import com.matrix.sportopia.models.dto.response.ExceptionDTO;
import com.matrix.sportopia.models.dto.response.LoginRes;
import com.matrix.sportopia.models.dto.response.UserResponseDto;
import com.matrix.sportopia.repositories.AuthorityRepository;
import com.matrix.sportopia.repositories.UserRepository;
import com.matrix.sportopia.services.EmailSenderService;
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

    public UserResponseDto register(UserRequestDto userRequestDto,  MultipartFile photo) {
        if (userRequestDto == null) {
            log.warn("-> cannot add null user");
            throw new IllegalArgumentException("User cannot be null");
        }
        log.info("-> started the insert operation!");
        User addedUser;
        UserResponseDto userResponseDto;
        try {
            userRequestDto.setPhoto(null);
            User user = userMapper.toEntity(userRequestDto);

            Authority defaultValue = authorityRepository.findByName("USER");
            Set<Authority> authorities = Set.of(defaultValue);
            user.setAuthorities(authorities);

//            addedUser = userRepository.save(user);
            if (photo!=null && !photo.isEmpty()){
                String userName = user.getName();
                String userSurname = user.getSurname();
                Long userId = user.getId();
                String photoPath = UploadPathUtility.uploadPath(photo,rootDir, userId, userName,userSurname);
                user.setPhotoPath(photoPath);
                user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
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

    private void sendToMail(User addedUser) {
        Email email = new Email();
        email.setReceiver(addedUser.getEmail());
        email.setSubject("Welcome to Sportopia");
        email.setText("Dear " + addedUser.getName() + " Thank you for registering at Sportopia");
        emailSenderService.sendEmail(email);
    }
}
