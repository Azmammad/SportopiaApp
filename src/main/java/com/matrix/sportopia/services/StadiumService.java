package com.matrix.sportopia.services;

import com.matrix.sportopia.entities.dto.request.StadiumRequestDto;
import com.matrix.sportopia.entities.dto.response.StadiumResponseDto;

import java.util.List;

public interface StadiumService {
    StadiumResponseDto getById(Long id);
    List<StadiumResponseDto> getAll();
    StadiumResponseDto add(StadiumRequestDto stadiumRequestDto);
    StadiumResponseDto update(StadiumRequestDto stadiumRequestDto);
    void delete(Long id);
}
