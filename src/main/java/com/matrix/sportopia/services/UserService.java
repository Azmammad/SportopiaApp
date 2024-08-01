package com.matrix.sportopia.services;

import com.matrix.sportopia.entities.User;
import com.matrix.sportopia.models.dto.request.ChangePasswordDto;
import com.matrix.sportopia.models.dto.request.UserRequestDto;
import com.matrix.sportopia.models.dto.response.UserResponseDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface UserService {
    UserResponseDto getById(Long id);
    List<UserResponseDto> getAll();
    UserResponseDto update(Long id,UserRequestDto userRequestDto);
    void delete(Long id);
    void changePassword(User user, String newPassword);
    List<UserResponseDto> getAllNoActiveUsers();
    void changeStatus(Long id);
    byte[] getPhoto(String photoPath);
    User findByEmail(String email);
    void changePassword(HttpServletRequest request, ChangePasswordDto changePasswordDto);
}
