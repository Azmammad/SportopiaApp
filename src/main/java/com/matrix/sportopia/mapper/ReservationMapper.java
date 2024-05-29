package com.matrix.sportopia.mapper;

import com.matrix.sportopia.entities.Reservation;
import com.matrix.sportopia.entities.dto.request.ReservationRequestDto;
import com.matrix.sportopia.entities.dto.response.ReservationResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ReservationMapper {
    public abstract ReservationResponseDto toResponce(Reservation reservation);
    public abstract Reservation toEntity(ReservationRequestDto reservationRequestDto);

}
