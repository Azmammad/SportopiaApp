package com.matrix.sportopia.services;

import com.matrix.sportopia.entities.dto.request.UpdatePasswordReqDto;
import com.matrix.sportopia.entities.dto.request.UserRequestDto;
import com.matrix.sportopia.entities.dto.response.UserResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    UserResponseDto getById(Long id);
    List<UserResponseDto> getAll();
    UserResponseDto add(UserRequestDto userRequestDto,MultipartFile photo);
    UserResponseDto update(Long id,UserRequestDto userRequestDto);
    void delete(Long id);
    UserResponseDto updatePassword(UpdatePasswordReqDto updatePasswordReqDto);
    List<UserResponseDto> getAllNoActiveUsers();
    void changeStatus(Long id);
}
