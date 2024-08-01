package com.matrix.sportopia.mapper;

import com.matrix.sportopia.entities.Reservation;
import com.matrix.sportopia.models.dto.request.ReservationRequestDto;
import com.matrix.sportopia.models.dto.response.ReservationResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class ReservationMapper {
    public abstract ReservationResponseDto toResponse(Reservation reservation);
    public abstract Reservation toEntity(ReservationRequestDto reservationRequestDto);

    @Mapping(target = "id", ignore = true)
    public abstract void updateEntityFromDto(ReservationRequestDto requestDto, @MappingTarget Reservation entity);
}
