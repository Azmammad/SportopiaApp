package com.matrix.sportopia.mapper;

import com.matrix.sportopia.entities.Stadium;
import com.matrix.sportopia.models.dto.request.StadiumRequestDto;
import com.matrix.sportopia.models.dto.response.StadiumResponseDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-26T11:59:01+0400",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.7.jar, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class StadiumMapperImpl extends StadiumMapper {

    @Override
    public StadiumResponseDto toResponse(Stadium stadium) {
        if ( stadium == null ) {
            return null;
        }

        StadiumResponseDto stadiumResponseDto = new StadiumResponseDto();

        stadiumResponseDto.setName( stadium.getName() );
        stadiumResponseDto.setAddress( stadium.getAddress() );
        stadiumResponseDto.setPrice( stadium.getPrice() );
        stadiumResponseDto.setStatus( stadium.getStatus() );

        return stadiumResponseDto;
    }

    @Override
    public Stadium toEntity(StadiumRequestDto stadiumRequest) {
        if ( stadiumRequest == null ) {
            return null;
        }

        Stadium stadium = new Stadium();

        stadium.setName( stadiumRequest.getName() );
        stadium.setCity( stadiumRequest.getCity() );
        stadium.setAddress( stadiumRequest.getAddress() );
        stadium.setPrice( stadiumRequest.getPrice() );
        stadium.setStatus( stadiumRequest.getStatus() );

        return stadium;
    }

    @Override
    public void updateEntityFromDto(StadiumRequestDto dto, Stadium entity) {
        if ( dto == null ) {
            return;
        }

        entity.setName( dto.getName() );
        entity.setCity( dto.getCity() );
        entity.setAddress( dto.getAddress() );
        entity.setPrice( dto.getPrice() );
        entity.setStatus( dto.getStatus() );
    }
}
