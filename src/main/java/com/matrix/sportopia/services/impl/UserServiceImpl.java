package com.matrix.sportopia.services.impl;

import com.matrix.sportopia.entities.Authority;
import com.matrix.sportopia.entities.User;
import com.matrix.sportopia.exceptions.handle.*;
import com.matrix.sportopia.models.dto.request.Email;
import com.matrix.sportopia.models.dto.request.UpdatePasswordReqDto;
import com.matrix.sportopia.models.dto.request.UserRequestDto;
import com.matrix.sportopia.models.dto.response.UserResponseDto;
import com.matrix.sportopia.enums.UserStatus;
import com.matrix.sportopia.mapper.UserMapper;
import com.matrix.sportopia.mapper.mappingUtil.UploadPathUtility;
import com.matrix.sportopia.repositories.AuthorityRepository;
import com.matrix.sportopia.repositories.UserRepository;
import com.matrix.sportopia.services.EmailSenderService;
import com.matrix.sportopia.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.matrix.sportopia.enums.UserStatus.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    @Value("${app.files}")
    private String rootDir;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final EmailSenderService emailSenderService;
    private final AuthorityRepository authorityRepository;

    @Override
    public UserResponseDto getById(Long id) {
        //throw new TestException(User.class);
        log.info("-> user-service get by id operation with id = {}", id);
        UserResponseDto userResponseDto = userRepository.findById(id).map(userMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException(User.class));
        if (userResponseDto.getStatus() == DELETED) {
            throw new EntityNotFoundException("Active user not found");
        }
        log.info("-> successful! user-service get by id operation with id = {}", id);
        return userResponseDto;
    }

    @Override
    public List<UserResponseDto> getAll() {
        log.info("-> started getAll method for user");
        List<User> activeUsers = userRepository.findAllActiveUsers();
        if (activeUsers.isEmpty()) {
            throw new EntityNotFoundException("Users not found");
        }
        List<UserResponseDto> responseDtoList = activeUsers.
                stream().map(userMapper::toResponse).collect(Collectors.toList());
        log.info("-> successfully");
        return responseDtoList;
    }

    @Override
    public List<UserResponseDto> getAllNoActiveUsers() {
        log.info("-> started deleted users list operation");
        List<User> inActiveUsers = userRepository.findAllNoActiveUsers();
        if (inActiveUsers.isEmpty()) {
            throw new EntityNotFoundException("Users not found");
        }
        List<UserResponseDto> responseDtoList = inActiveUsers.
                stream().map(userMapper::toResponse).collect(Collectors.toList());
        log.info("-> successfully");
        return responseDtoList;
    }

   /* @Override
    @Transactional
    public UserResponseDto add(UserRequestDto userRequestDto, MultipartFile photo) {
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
            user.getAuthorities().add(defaultValue);

            addedUser = userRepository.save(user);
            if (photo!=null && !photo.isEmpty()){
                String userName = user.getName();
                String userSurname = user.getSurname();
                Long userId = user.getId();
                String photoPath = UploadPathUtility.uploadPath(photo,rootDir, userId, userName,userSurname);
                user.setPhotoPath(photoPath);
            }
            userRepository.save(addedUser);
            userResponseDto = userMapper.toResponse(addedUser);
            log.info("-> successfully: " + addedUser);

            sendToMail(addedUser);

        } catch (Exception e) {
            log.error("error ", e);
            throw new AlreadyExistException("Failed");
        }
        return userResponseDto;
    }*/


    @Override
    @Transactional
    public UserResponseDto update(Long id, UserRequestDto userRequestDto) {
        if (userRequestDto == null || id == null) {
            log.warn("-> cannot update user with null values");
            throw new IllegalArgumentException("User and ID cannot be null");
        }
        log.info("-> started the update operation");
        UserResponseDto userResponseDto;
        User updatedUser;
        try {
            User existingUser = userRepository.findById(id).orElseThrow(() ->
                    new EntityNotFoundException("User with id = " + id + " not found"));
            userMapper.updateEntityFromRequest(userRequestDto, existingUser);

            updatePhoto(userRequestDto,existingUser);

            updatedUser = userRepository.save(existingUser);
            userResponseDto = userMapper.toResponse(updatedUser);
            log.info("-> successfully updated user: " + updatedUser);
        } catch (Exception e) {
            log.error("-> update operation failed", e);
            throw new UpdateFailedException("Failed to update user", e);
        }
        return userResponseDto;
    }

    @Override
    public void delete(Long id) {
        log.info("-> delete operation started");
        User user = userRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (user.getStatus() == ACTIVE || user.getStatus() == INACTIVE) {
            user.setStatus(UserStatus.DELETED);
            userRepository.save(user);
            log.info("-> user with id = {} as DELETED", id);
        } else {
            log.error("-> user with id = {} is already deleted", id);
            throw new IllegalStateException("User is already deleted");
        }

    }

    @Override
    public void changeStatus(Long id) {
        log.info("-> change status started");
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        if (user.getStatus() == ACTIVE) {
            log.info("-> user status changed is Inactive");
            user.setStatus(INACTIVE);
        } else if (user.getStatus() == INACTIVE) {
            log.info("-> user status changed is Active");
            user.setStatus(ACTIVE);
        } else {
            log.error("-> the status of a deleted user cannot be changed ");
            throw new IllegalStateException("User is already deleted");
        }
        userRepository.save(user);
    }


    @Override
    public UserResponseDto updatePassword(UpdatePasswordReqDto updatePasswordReqDto) {
        if (updatePasswordReqDto == null) {
            log.warn("-> cannot update password with null request");
            throw new IllegalArgumentException("Request cannot be null");
        }
        String currentPassword = updatePasswordReqDto.getCurrentPassword();
        String newPassword = updatePasswordReqDto.getNewPassword();
        String againNewPassword = updatePasswordReqDto.getAgainNewPassword();

        if (!newPassword.equals(againNewPassword)) {
            log.error("-> new passwords do not match");
            throw new IllegalArgumentException("New passwords do not match");
        }
        String email = getCurrentUserEmail();

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getPassword().equals(currentPassword)) {
                user.setPassword(newPassword);
                User updatedUser = userRepository.save(user);
                return userMapper.toResponse(updatedUser);
            } else {
                log.error("-> current password does not match for user with email {}", email);
                throw new IllegalArgumentException("Current password is incorrect");
            }
        } else {
            log.error("-> user with email {} not found", email);
            throw new EntityNotFoundException("User not found");
        }
    }

    @Override
    public byte[] getPhoto(String photoPath){
        try {
            Path path = Paths.get(photoPath);
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read photo from path: " + photoPath, e);
        }
    }
    /*public ResponseEntity<String> requestForResetPassword(String email){
        log.info("requestForResetPassword method started by: {}", email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new EntityNotFoundException("User not found with this email!"));

        Long userId = user.getId();
        Optional<PasswordResetToken> passwordResetToken = tokenRepository.findByUserId(userId);
        passwordResetToken.ifPresent(tokenRepository::delete);
    }*/

    /*@Override
    public void resetPassword(String token,String newPassword){
        PasswordResetToken resetToken = tokenRepository.findByToken(token).orElse
    }*/



    private void sendToMail(User addedUser) {
        Email email = new Email();
        email.setReceiver(addedUser.getEmail());
        email.setSubject("Welcome to Sportopia");
        email.setText("Dear " + addedUser.getName() + " Thank you for registering at Sportopia");
        emailSenderService.sendEmail(email);
    }

    private String getCurrentUserEmail() {
        //return SecurityContextHolder.getContext().getAuthentication().getName();
        return null;
    }

    private void updatePhoto(UserRequestDto userRequestDto, User existingUser) throws IOException {
        MultipartFile photo = userRequestDto.getPhoto();
        if (photo != null && !photo.isEmpty()) {
            deleteExistingPhoto(existingUser);
            String userDirName = String.format("%d_%s_%s", existingUser.getId(), existingUser.getName(), existingUser.getSurname());
            Path userDir = Paths.get(rootDir, userDirName);
            if (!Files.exists(userDir)) {
                Files.createDirectories(userDir);
            }
            String photoPath = UploadPathUtility.uploadPath(photo, rootDir, existingUser.getId(), existingUser.getName(), existingUser.getSurname());
            existingUser.setPhotoPath(photoPath);
        }
    }

    private void deleteExistingPhoto(User existingUser) {
        if (existingUser.getPhotoPath() != null) {
            Path existingPhotoPath = Paths.get(existingUser.getPhotoPath());
            try {
                Files.deleteIfExists(existingPhotoPath);
            } catch (IOException e) {
                log.warn("Failed to delete existing photo: " + existingPhotoPath, e);
            }
        }
    }
}
