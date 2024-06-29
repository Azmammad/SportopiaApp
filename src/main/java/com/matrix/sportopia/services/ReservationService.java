package com.matrix.sportopia.services;

import com.matrix.sportopia.models.dto.request.ReservationRequestDto;
import com.matrix.sportopia.models.dto.response.ReservationResponseDto;

import java.util.List;

public interface ReservationService {

    ReservationResponseDto getById(Long id);
    List<ReservationResponseDto> getAll();
    ReservationResponseDto add(ReservationRequestDto reservationRequestDto);
    ReservationResponseDto update(ReservationRequestDto reservationRequestDto);
    void delete(Long id);

}
