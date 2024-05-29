package com.matrix.sportopia.controllers;

import com.matrix.sportopia.entities.User;
import com.matrix.sportopia.entities.dto.request.UpdatePasswordReqDto;
import com.matrix.sportopia.entities.dto.request.UserRequestDto;
import com.matrix.sportopia.entities.dto.response.UserResponseDto;
import com.matrix.sportopia.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public UserResponseDto getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @GetMapping("/list")
    public List<UserResponseDto> getAll() {
        return userService.getAll();
    }

    @GetMapping("/noActive-list")
    public List<UserResponseDto> getNoActiveUsers() {
        return userService.getAllNoActiveUsers();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> add(@ModelAttribute UserRequestDto userRequest) {
        MultipartFile photo = userRequest.getPhoto();
        userService.add(userRequest, photo);
        return ResponseEntity.ok(photo.getOriginalFilename() + " " + photo.getSize() + " " + userRequest.getName());
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserResponseDto> update(@PathVariable Long id,
                                                  @ModelAttribute UserRequestDto requestDto) {
        MultipartFile photo = requestDto.getPhoto();
        requestDto.setPhoto(photo);
        UserResponseDto updatedUser = userService.update(id, requestDto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @PutMapping("/change-status/{id}")
    public void changeStatus(@PathVariable Long id) {
        userService.changeStatus(id);
    }

    @PutMapping("/password-update")
    public ResponseEntity<UserResponseDto> updatePassword(@RequestBody UpdatePasswordReqDto updatePasswordReqDto) {
        UserResponseDto updateUser = userService.updatePassword(updatePasswordReqDto);
        return ResponseEntity.ok(updateUser);
    }


}
