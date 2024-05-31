package com.matrix.sportopia.services;

import com.matrix.sportopia.entities.dto.request.SportRequestDto;
import com.matrix.sportopia.entities.dto.response.SportResponseDto;

import java.util.List;

public interface SportService {

    SportResponseDto getById(Long id);
    List<SportResponseDto> getAll();
    SportResponseDto add(SportRequestDto sportRequest);
    List<SportResponseDto> addList(List<SportRequestDto> sportRequests);
    SportResponseDto update(Long id,SportRequestDto sportRequest);
    void delete(Long id);
    List<SportResponseDto> getAllNoActiveSports(SportRequestDto sportRequestDto);
    void changeStatus(Long id);
}
