package com.matrix.sportopia.mapper;

import com.matrix.sportopia.entities.Stadium;
import com.matrix.sportopia.entities.dto.request.StadiumRequestDto;
import com.matrix.sportopia.entities.dto.response.StadiumResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class StadiumMapper {
    public abstract StadiumResponseDto toResponse(Stadium stadium);
    public abstract Stadium toEntity(StadiumRequestDto stadiumRequest);
}
