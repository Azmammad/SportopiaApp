package com.matrix.sportopia.mapper;

import com.matrix.sportopia.entities.Reservation;
import com.matrix.sportopia.models.dto.request.ReservationRequestDto;
import com.matrix.sportopia.models.dto.response.ReservationResponseDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-01T15:00:29+0400",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.7.jar, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class ReservationMapperImpl extends ReservationMapper {

    @Override
    public ReservationResponseDto toResponse(Reservation reservation) {
        if ( reservation == null ) {
            return null;
        }

        ReservationResponseDto reservationResponseDto = new ReservationResponseDto();

        reservationResponseDto.setStartTime( reservation.getStartTime() );
        reservationResponseDto.setStatus( reservation.getStatus() );

        return reservationResponseDto;
    }

    @Override
    public Reservation toEntity(ReservationRequestDto reservationRequestDto) {
        if ( reservationRequestDto == null ) {
            return null;
        }

        Reservation reservation = new Reservation();

        reservation.setStartTime( reservationRequestDto.getStartTime() );
        reservation.setEndTime( reservationRequestDto.getEndTime() );

        return reservation;
    }

    @Override
    public void updateEntityFromDto(ReservationRequestDto requestDto, Reservation entity) {
        if ( requestDto == null ) {
            return;
        }

        entity.setStartTime( requestDto.getStartTime() );
        entity.setEndTime( requestDto.getEndTime() );
    }
}
