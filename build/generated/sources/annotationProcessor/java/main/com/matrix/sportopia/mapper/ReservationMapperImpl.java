package com.matrix.sportopia.mapper;

import com.matrix.sportopia.entities.Reservation;
import com.matrix.sportopia.entities.dto.request.ReservationRequestDto;
import com.matrix.sportopia.entities.dto.response.ReservationResponseDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-30T10:25:45+0400",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.7.jar, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class ReservationMapperImpl extends ReservationMapper {

    @Override
    public ReservationResponseDto toResponce(Reservation reservation) {
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
        reservation.setStatus( reservationRequestDto.getStatus() );

        return reservation;
    }
}
