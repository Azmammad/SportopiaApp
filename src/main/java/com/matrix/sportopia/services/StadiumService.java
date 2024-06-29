package com.matrix.sportopia.services;

import com.matrix.sportopia.models.dto.request.StadiumRequestDto;
import com.matrix.sportopia.models.dto.response.StadiumResponseDto;

import java.util.List;

public interface StadiumService {
    StadiumResponseDto getById(Long id);

    List<StadiumResponseDto> getAll();

    StadiumResponseDto add(StadiumRequestDto stadiumRequestDto);

    StadiumResponseDto update(Long id,StadiumRequestDto stadiumRequestDto);

    void delete(Long id);

    List<StadiumResponseDto> getStadiumsBySportId(Long sportId);
    void changeStatus(Long id);

}
