package com.matrix.sportopia.mapper;

import com.matrix.sportopia.entities.Sport;
import com.matrix.sportopia.models.dto.request.SportRequestDto;
import com.matrix.sportopia.models.dto.response.SportResponseDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-26T11:59:01+0400",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.7.jar, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class SportMapperImpl extends SportMapper {

    @Override
    public SportResponseDto toResponse(Sport sport) {
        if ( sport == null ) {
            return null;
        }

        SportResponseDto sportResponseDto = new SportResponseDto();

        sportResponseDto.setName( sport.getName() );
        sportResponseDto.setStatus( sport.getStatus() );

        return sportResponseDto;
    }

    @Override
    public Sport toEntity(SportRequestDto sportRequestDto) {
        if ( sportRequestDto == null ) {
            return null;
        }

        Sport sport = new Sport();

        sport.setName( sportRequestDto.getName() );
        sport.setStatus( sportRequestDto.getStatus() );

        return sport;
    }

    @Override
    public void updateEntityFromRequest(SportRequestDto sportRequestDto, Sport sport) {
        if ( sportRequestDto == null ) {
            return;
        }

        sport.setName( sportRequestDto.getName() );
        sport.setStatus( sportRequestDto.getStatus() );
    }
}
