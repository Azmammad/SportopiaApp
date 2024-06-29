package com.matrix.sportopia.mapper;

import com.matrix.sportopia.entities.Sport;
import com.matrix.sportopia.models.dto.request.SportRequestDto;
import com.matrix.sportopia.models.dto.response.SportResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class SportMapper {
    public abstract SportResponseDto toResponse(Sport sport);
    public abstract Sport toEntity(SportRequestDto sportRequestDto);
    @Mapping(target = "id",ignore = true)
    public abstract void updateEntityFromRequest(SportRequestDto sportRequestDto, @MappingTarget Sport sport);
}

