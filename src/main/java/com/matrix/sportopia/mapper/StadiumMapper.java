package com.matrix.sportopia.mapper;

import com.matrix.sportopia.entities.Stadium;
import com.matrix.sportopia.models.dto.request.StadiumRequestDto;
import com.matrix.sportopia.models.dto.response.StadiumResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class StadiumMapper {
    public abstract StadiumResponseDto toResponse(Stadium stadium);
    public abstract Stadium toEntity(StadiumRequestDto stadiumRequest);

    @Mapping(target = "id", ignore = true)
    public abstract void updateEntityFromDto(StadiumRequestDto dto, @MappingTarget Stadium entity);
}
